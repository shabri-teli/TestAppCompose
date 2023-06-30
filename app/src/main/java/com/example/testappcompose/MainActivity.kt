package com.example.testappcompose

import SampleData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testappcompose.ui.theme.TestAppComposeTheme
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.testappcompose.ui.FormScreen

private var isClicked:Boolean ?=false

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.primary
                ) {
                    inflateMainLayout(messages = SampleData.conversationSample, modules = SampleData.ModuleData)
                }
            }
        }
    }
}

data class Message(val author: String, val title: String)
data class Module(val name: String, val img: String)

@Composable
fun MessageCardItem(msg: Message) {
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
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(
                        1.8.dp, MaterialTheme.colors.secondary,
                        CircleShape
                    )
            )
        }

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        /**Col 2: Text Column (Name & nsg)*/
        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }

        /** to update surface color from one to other*/
        val surfaceColor by animateColorAsState(
            targetValue = if(isExpanded) colorResource(id= R.color.teal_200) else colorResource(id = R.color.white)
        )
        Column (modifier = Modifier.clickable { isExpanded = !isExpanded }){
            //colors & typography
            Text(
                text = msg.author,
                color = colorResource(id = R.color.black),
                style = MaterialTheme.typography.subtitle1
            )

            // Add a vertical space between the author and message texts
//            Spacer(modifier = Modifier.height(2.dp))

            Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp, modifier = Modifier.padding(8.dp), color = surfaceColor) {
                Text(text = msg.title,
                    color = colorResource(id = R.color.gray),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(4.dp),

                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
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
fun ModuleItem(modules: Module) {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier
            .width(10.dp)
            .height(10.dp))
        Modifier.padding(all = 8.dp)

        /**Col 1: Image column*/
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(80.dp)
        ) {
            Spacer(modifier = Modifier
                .height(10.dp)
                .width(10.dp))
            Image(
                painter = painterResource(R.drawable.ic_face_profile),
                contentDescription = "This is content",
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(
                        1.8.dp, MaterialTheme.colors.secondary,
                        CircleShape
                    )
                    .clickable {
                        isClicked = !isClicked!!
                        ViewModules(context, modules.name)
                    }
            )

            Spacer(modifier = Modifier
                .height(10.dp)
                .width(10.dp))
            Text(text = modules.name,
                style = MaterialTheme.typography.overline,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    Spacer(modifier = Modifier
        .width(4.dp)
        .height(8.dp))
    /*if(isClicked){
        showImage()
    }*/
}

fun ViewModules(context: Context, moduleName: String) {
    Toast.makeText(context, "$moduleName", Toast.LENGTH_SHORT).show()
    val goToFormScreen = Intent(context, FormScreen::class.java)
    goToFormScreen.putExtra("moduleName",moduleName ?: "")
    context.startActivity(goToFormScreen)
}

@Composable
fun showImage(){
    Column {
        Image(
            painter = painterResource(R.drawable.ic_face_profile),
            contentDescription = "This is content",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(400.dp)
                .clip(CircleShape)
                .border(
                    1.8.dp, MaterialTheme.colors.secondary,
                    CircleShape
                )
        )
    }
}

@Preview
@Composable
fun TopBar(){
    Surface(color = colorResource(id = R.color.gray), elevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()) {
            Column {
                Text(modifier = Modifier.padding(4.dp),text = "My Name", color = colorResource(id = R.color.white))
            }
        }
        /**padding bottom*/
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun inflateMainLayout(messages: List<Message>, modules: List<Module>){
    Column() {
        TopBar()

        /** horizontal scroll view*/
        InflateModules(modules)
        /*LazyRow(Modifier.background(color = Color.Magenta)){
            items(messages) {   message ->
                MessageCard(msg = message)
            }
        }*/

        /**vertical scrollview*/
        Spacer(modifier = Modifier.height(8.dp))
        InflateMessages(messages = messages)
    }
}

@Composable
fun InflateMessages(messages: List<Message>){
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn{
        items(messages) {   message ->
            MessageCardItem(msg = message)
        }
    }
}

@Composable
fun InflateModules(modules: List<Module>){
    LazyRow(modifier = Modifier.background(color = Color.DarkGray)){
        items(modules) { modules ->
            ModuleItem(modules)
        }
    }
}

//////////////////////////////////////////////////////////

@Preview(showBackground = true)
@Composable
fun PreviewMessageCard() {
    MessageCardItem(msg = Message("Name", "this is the content."))
}

@Preview
@Composable
fun PreviewConversation() {
    TestAppComposeTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.secondary
        ) {
            inflateMainLayout(SampleData.conversationSample,SampleData.ModuleData)
        }
    }
}
