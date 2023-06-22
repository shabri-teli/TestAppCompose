package com.example.testappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testappcompose.ui.theme.TestAppComposeTheme
import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

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
//                    MessageCard(Message("Aarya", "Never give up"))
                    //Conversation(messages = SampleData.conversationSample)
                    ConversationVertical(messages = SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val title: String)


@Composable
fun Conversation(messages: List<Message>){
    LazyColumn{
        items(messages) {   message ->
            MessageCard(msg = message)
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier
            .width(8.dp)
            .height(8.dp))
        Modifier.padding(all = 8.dp)

        /**Col 1: Image column*/
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_face_profile),
                contentDescription = "This is content",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.8.dp, MaterialTheme.colors.secondary,
                        CircleShape),
                alignment = Alignment.Center
            )
        }

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        /**Col 2: Text Column*/
        Column {
            //colors & typography
            Text(
                text = msg.author,
                color = colorResource(id = R.color.black),
                style = MaterialTheme.typography.subtitle1
            )

            // Add a vertical space between the author and message texts
//            Spacer(modifier = Modifier.height(2.dp))

            Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp, modifier = Modifier.padding(8.dp)) {
                Text(text = msg.title,
                    color = colorResource(id = R.color.gray),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(4.dp)
                )
            }
            /**right margin*/
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
    Spacer(modifier = Modifier
        .width(4.dp)
        .height(8.dp))


}

@Composable
fun TopBar(){
    Surface(color = colorResource(id = R.color.gray), elevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()) {
            Column {
                Text(modifier = Modifier.padding(4.dp),text = "My Name", color = colorResource(id = R.color.white))
            }
        }
    }
}

@Composable
fun ConversationVertical(messages: List<Message>){
    TopBar()
    Column() {
        LazyRow{
            items(messages) {   message ->
                MessageCard(msg = message)
            }
        }

        LazyColumn{
            items(messages) {   message ->
                MessageCard(msg = message)
            }
        }
    }


}

//////////////////////////////////////////////////////////

@Preview(showBackground = true)
@Composable
fun PreviewMessageCard() {
    MessageCard(msg = Message("Name", "this is the content."))
}

@Preview(showBackground = true)
@Composable
fun PreviewTopView() {
    TopBar()
}


@Preview
@Composable
fun PreviewConversation() {
    TestAppComposeTheme() {
        //Conversation(SampleData.conversationSample)
        ConversationVertical(SampleData.conversationSample)
    }
}
