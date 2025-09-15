package com.azabost.quest.users.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.azabost.quest.theme.QuestTheme
import com.azabost.quest.users.repository.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersActivity : ComponentActivity() {

    private val viewModel: UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        observeToasts()

        setContent {
            QuestTheme {
                val users = viewModel.users.collectAsState()

                UsersScreen(
                    users = users.value,
                    createUser = viewModel::createUser,
                )
            }
        }
    }

    private fun observeToasts() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.toasts.collect { message ->
                    Toast.makeText(this@UsersActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun UsersScreen(
    users: List<User>,
    modifier: Modifier = Modifier,
    createUser: (firstName: String, lastName: String) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { UserForm(createUser) },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                users.forEach { user ->
                    item {
                        User(
                            user = user,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserForm(createUser: (String, String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color
        )
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                createUser(firstName, lastName)
                firstName = ""
                lastName = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create User")
        }
    }
}

@Composable
fun User(
    user: User,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = user.firstName)
        Text(text = user.lastName)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Success State")
@Composable
fun PostsSuccessPreview() {
    QuestTheme {
        val previewUsers = listOf(
            User(1, "Sylvester", "Stallone"),
            User(2, "John", "Rambo"),
        )

        UsersScreen(
            users = previewUsers,
            createUser = { _, _ -> },
        )
    }
}