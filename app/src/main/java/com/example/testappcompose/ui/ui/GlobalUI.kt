package com.example.testappcompose.ui.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.testappcompose.R
import com.example.testappcompose.ui.FormScreen
import com.example.testappcompose.ui.ui.ui.theme.TestAppComposeTheme

class GlobalUI : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val data = getIntentData()
                    showButton(label = data.toString())
                }
            }
        }
    }
}

@Composable
fun showButton(label: String) {
    Button(onClick = { /*TODO*/
    }) {
        Text(
            text = ("This is $label")
        )
    }
}

@Composable
fun getIntentData() {
    val context = LocalContext.current
    val intent = (context as FormScreen).intent
    val moduleName = intent.getStringExtra("moduleName")
}

@Composable
fun CreateButton(label: String, onClick:()-> Unit){
    Button(onClick = onClick,
    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_green))) {
        Text(text = "$label")
    }
}

//////////////////////////////////////

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    TestAppComposeTheme {
        showButton("Android")
    }
}