import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import kotlin.coroutines.resumeWithException
import kotlin.math.sqrt

/**
 * This class contains various functions with common coroutine mistakes.
 * Your goal is to identify and fix them.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CoroutinesBadPractices {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun fetchAndForget() {
        scope.launch {
            // Simulate a network call
            delay(2000)
            println("Data fetched from network!")
        }
    }

    fun performHeavyCalculation() {
        scope.launch(Dispatchers.Default) {
            var result = 0.0
            for (i in 1..100_000_000) {
                result += sqrt(i.toDouble())
            }
            println("Heavy calculation finished with result: $result")
        }
    }

    fun updateUiFromMain() {
        scope.launch {
            println("Doing some work on the main thread...")
            delay(500)
            println("UI has been updated!")
        }
    }

    fun getCombinedData() {
        scope.launch {
            println("Fetching data...")
            try {
                val deferred1 = async { listOf(1, 2, 3, 4, 5) }
                val deferred2 = async<List<Int>> {
                    throw RuntimeException("Error fetching data")
                }

                val result1 = runCatching { deferred1.await() }
                    .getOrElse { e ->
                        coroutineContext.ensureActive()
                        println("Error1: ${e.message}")
                        listOf(-1)
                    }

                val result2 = runCatching { deferred2.await() }
                    .getOrElse { e ->
                        coroutineContext.ensureActive()
                        println("Error2: ${e.message}")
                        listOf(-1)
                    }

                result1 + result2
            } catch (e: Exception) {
                println("Error: ${e.message}")
                listOf(-1)
            }
        }
    }

    fun getSequentialData() {
        scope.launch {
            println("Fetching data...")
            val result1 = try {
                delay(500)
                listOf(1, 2, 3, 4, 5)
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                println("Error1: ${e.message}")
                listOf(-1)
            }
            val result2 = try {
                delay(500)
                listOf(6, 7, 8, 9, 10)
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                println("Error2: ${e.message}")
                listOf(-1)
            }

            println("Result: ${result1 + result2}")
        }
    }

    fun getSequentialDataWithRunCatching() {
        scope.launch {
            println("Fetching data...")
            val result1 = runCatching {
                delay(500)
                listOf(1, 2, 3, 4, 5)
            }.getOrElse { e ->
                coroutineContext.ensureActive()
                println("Error1: ${e.message}")
                listOf(-1)
            }
            val result2 = runCatching {
                delay(500)
                listOf(6, 7, 8, 9, 10)
            }.getOrElse { e ->
                coroutineContext.ensureActive()
                println("Error2: ${e.message}")
                listOf(-1)
            }

            println("Result: ${result1 + result2}")
        }
    }

    fun complexTaskWithManualJob() {
        scope.launch(Dispatchers.IO) {
            println("Parent task started.")

            // Start a sub-task for logging
            launch {
                repeat(5) {
                    delay(300)
                    println("Logging...")
                }
            }
            delay(1000)
            println("Parent task finished.")
        }
    }

    private val api = LegacyApi()
    fun startLegacyApiChain() {

        scope.launch {
            println("Starting legacy API chain...")

            try {
                val user = fetchUser("123")
                val profile = fetchUserProfile(user)
                val contacts = fetchUserContacts(user)
                println("UI update $user $profile $contacts")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    private suspend fun fetchUser(id: String): LegacyApi.User {
        return suspendCancellableCoroutine { cont ->
            api.fetchUser(id, object : ResultCallback<LegacyApi.User> {
                override fun onSuccess(result: LegacyApi.User) {
                    cont.resume(result) {}
                }

                override fun onFailure(error: Throwable) {
                    cont.resumeWithException(error)
                }
            })
        }
    }

    private suspend fun fetchUserProfile(user: LegacyApi.User): LegacyApi.Profile {
        return suspendCancellableCoroutine { cont ->
            api.fetchUserProfile(user, object : ResultCallback<LegacyApi.Profile> {
                override fun onSuccess(result: LegacyApi.Profile) {
                    cont.resume(result) {}
                }

                override fun onFailure(error: Throwable) {
                    cont.resumeWithException(error)
                }
            })
        }
    }

    private suspend fun fetchUserContacts(user: LegacyApi.User): List<LegacyApi.Contact> {
        return suspendCancellableCoroutine { cont ->
            api.fetchUserContacts(user, object : ResultCallback<List<LegacyApi.Contact>> {
                override fun onSuccess(result: List<LegacyApi.Contact>) {
                    cont.resume(result) {}
                }

                override fun onFailure(error: Throwable) {
                    cont.resumeWithException(error)
                }
            })
        }
    }

}

// --- Helper classes for legacy API simulation ---

interface ResultCallback<T> {
    fun onSuccess(result: T)
    fun onFailure(error: Throwable)
}

class LegacyApi {
    data class User(val id: String, val name: String)
    data class Profile(val userName: String, val favoriteColor: String)
    data class Contact(val name: String, val phoneNumber: String)

    fun fetchUser(id: String, callback: ResultCallback<User>) {
        try {
            Thread.sleep(1000)
            if (id == "123") {
                callback.onSuccess(User(id = "123", name = "Alex"))
            } else {
                callback.onFailure(IllegalArgumentException("User not found"))
            }
        } catch (e: InterruptedException) {
            callback.onFailure(e)
        }
    }

    fun fetchUserProfile(user: User, callback: ResultCallback<Profile>) {
        try {
            Thread.sleep(1000)
            if (user.name.contains("Alex")) {
                callback.onSuccess(Profile(userName = user.name, favoriteColor = "Blue"))
            } else {
                callback.onFailure(IOException("Profile service unavailable"))
            }
        } catch (e: InterruptedException) {
            callback.onFailure(e)
        }
    }

    fun fetchUserContacts(user: User, callback: ResultCallback<List<Contact>>) {
        try {
            Thread.sleep(1000)
            if (user.name.contains("Alex")) {
                callback.onSuccess(
                    listOf(
                        Contact(name = "John", phoneNumber = "123456789"),
                        Contact(name = "Jane", phoneNumber = "098765432")
                    )
                )
            } else {
                callback.onFailure(IOException("Profile service unavailable"))
            }
        } catch (e: InterruptedException) {
            callback.onFailure(e)
        }
    }
}
