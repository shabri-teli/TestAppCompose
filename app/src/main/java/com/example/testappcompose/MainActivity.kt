package com.example.testappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testappcompose.ui.theme.TestAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting("Android")
                    //show messageCard
                    MessageCard(Members("Aarya","Never give up"))
                }
            }
        }
    }
}

data class Members(val name: String, val address: String)

@Composable
fun MessageCard(msg: Members){
    Row{ Modifier.padding(all = 8.dp)
        Image(
            painter = painterResource(R.drawable.ic_child_care_24), contentDescription = "This is content",
            modifier = Modifier.size(40.dp).clip(CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        Column{
            Text(text = msg.name)
            // Add a vertical space between the author and message texts
                Spacer(modifier = Modifier.height(4.dp))
            Text(text = msg.address)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMessageCard(){
    MessageCard(msg = Members("Name","this is the content."))
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestAppComposeTheme {
        Greeting("Android")
    }
}