package com.example.datastoreapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import com.example.datastoreapp.ui.theme.DatastoreAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val darkModeStore = StoreDarkMode(this)
            val darkMode = darkModeStore.getDarkMode.collectAsState(initial = false)
            DatastoreAppTheme(
                darkTheme = darkMode.value

            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Greeting(
                        darkModeStore, darkMode.value
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(darkModeStore: StoreDarkMode, darkMode: Boolean) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreUserEmail(context)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        var email by rememberSaveable { mutableStateOf("") }


        val userEmail = dataStore.getEmail.collectAsState(initial = "")

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            keyboardOptions = KeyboardOptions().copy(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {


            scope.launch {
                dataStore.saveEmail(email)

            }
        }) {
            Text("Guardar E-mail")

        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(userEmail.value)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            scope.launch {
                if (darkMode) {
                    darkModeStore.saveDarkMode(false)
                } else {
                    darkModeStore.saveDarkMode(true)
                }

            }
        }) {
            Text(text = "Mudar estado")
        }


        Switch(checked = darkMode, onCheckedChange = {  isChecked ->

            scope.launch {
                darkModeStore.saveDarkMode(isChecked)
            }
        })

    }
}

