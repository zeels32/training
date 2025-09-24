import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.math.sqrt

/**
 * This class contains various functions with common coroutine mistakes.
 * Your goal is to identify and fix them.
 */
class CoroutinesBadPractices {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun fetchAndForget() {
        GlobalScope.launch {
            // Simulate a network call
            delay(2000)
            println("Data fetched from network!")
        }
    }

    fun performHeavyCalculation() {
        scope.launch(Dispatchers.IO) {
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
            withContext(Dispatchers.Main) {
                println("UI has been updated!")
            }
        }
    }

    fun getCombinedData() {
        scope.launch {
            println("Fetching data...")
            try {
                val result1 = async { listOf(1, 2, 3, 4, 5) }
                val result2 = async<List<Int>> {
                    throw RuntimeException("Error fetching data")
                }
                result1.await() + result2.await()
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
                println("Error1: ${e.message}")
                listOf(-1)
            }
            val result2 = try {
                delay(500)
                listOf(6, 7, 8, 9, 10)
            } catch (e: Exception) {
                println("Error2: ${e.message}")
                listOf(-1)
            }

            println("Result: ${result1 + result2}")
        }
    }

    fun complexTaskWithManualJob() {
        val scope = CoroutineScope(Dispatchers.IO)
        val parentJob = Job()
        scope.launch(parentJob) {
            println("Parent task started.")

            // Start a sub-task for logging
            val loggingJob = Job()
            scope.launch(loggingJob) {
                repeat(5) {
                    delay(300)
                    println("Logging...")
                }
            }
            delay(1000)
            println("Parent task finished.")
        }
    }

    fun startLegacyApiChain() {
        val api = LegacyApi()

        scope.launch {
            println("Starting legacy API chain...")
            api.fetchUser(
                "123",
                object : ResultCallback<LegacyApi.User> {
                    override fun onSuccess(user: LegacyApi.User) {
                        println("Got user: $user")
                        api.fetchUserProfile(
                            user,
                            object : ResultCallback<LegacyApi.Profile> {
                                override fun onSuccess(profile: LegacyApi.Profile) {
                                    println("Got profile: $profile")
                                    // Imagine more calls... this gets unreadable fast.
                                    api.fetchUserContacts(
                                        user,
                                        object : ResultCallback<List<LegacyApi.Contact>> {
                                            override fun onSuccess(contacts: List<LegacyApi.Contact>) {
                                                println("Got contacts: $contacts")
                                                launch {
                                                    withContext(Dispatchers.Main) {
                                                        println("UI update $user $profile $contacts")
                                                    }
                                                }

                                            }

                                            override fun onFailure(error: Throwable) {
                                                println("Failed to get contacts: ${error.message}")
                                            }
                                        }
                                    )
                                }

                                override fun onFailure(error: Throwable) {
                                    println("Failed to get profile: ${error.message}")
                                }
                            }
                        )
                    }

                    override fun onFailure(error: Throwable) {
                        println("Failed to get user: ${error.message}")
                    }
                }
            )
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
