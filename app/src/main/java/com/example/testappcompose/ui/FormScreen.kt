package com.example.testappcompose.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.testappcompose.MainActivity
import com.example.testappcompose.ui.ui.CreateButton
import com.example.testappcompose.ui.ui.showButton
import com.example.testappcompose.ui.ui.theme.TestAppComposeTheme

private var context: Context ?= null

class FormScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    context = LocalContext.current
                    getIntentData()
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val moduleName = getIntentData()
    Column() {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Text(
            text = "This is  $moduleName!",
            modifier = modifier
        )
        showButton("$moduleName")
        CreateButton("$moduleName") {
            showModulesName(moduleName)
        }
        CreateButton("Back") {
            onClickOfButton()
            val goToFormScreen = Intent(context, MainActivity::class.java)
            goToIntent(goToFormScreen)
        }

    }
}

fun onClickOfButton(){
    Toast.makeText(context, "buttonClicked", Toast.LENGTH_SHORT).show()
}

fun showModulesName(moduleName: String?) {
    Toast.makeText(context, "This is $moduleName", Toast.LENGTH_SHORT).show()
}


fun goToIntent(goToScreenIntent: Intent) {
    goToScreenIntent.putExtra("moduleName","myName" ?: "")
    context?.startActivity(goToScreenIntent)
}

@Composable
fun getIntentData(): String? {
    val context = LocalContext.current
    val intent = (context as FormScreen).intent
    val moduleName = intent.getStringExtra("moduleName")
    return moduleName
}

///////////////////////////
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestAppComposeTheme {
        Greeting("Android")
    }
}