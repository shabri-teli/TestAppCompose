package com.example.testappcompose

import android.content.Context
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.example.testappcompose.ui.theme.TestAppComposeTheme
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.testappcompose.ui.ui.CreateButton
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM

import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView


class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Handle the splash screen trasition
        Thread.sleep(5000)
        installSplashScreen()
        setContent {
            TestAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Login(getVideoUri())
                }
            }
        }
    }

    private fun getVideoUri(): Uri {
        val rawId = resources.getIdentifier("info", "raw", packageName)
        val videoUri = "android.resource://$packageName/$rawId"
        return Uri.parse(videoUri)
    }
}

@Composable
private fun Context.buildExoPlayer(uri: Uri) =
    ExoPlayer.Builder(this).build().apply {
        setMediaItem(MediaItem.fromUri(uri))
        repeatMode = Player.REPEAT_MODE_ALL
        playWhenReady = true
        prepare()
    }

private fun Context.buildPlayerView(exoPlayer: Player?) =
    StyledPlayerView(this).apply {
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        useController = false
        resizeMode = RESIZE_MODE_ZOOM
    }

@Composable
fun Login(videoUri: Uri) {
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val exoUri = context.buildExoPlayer(videoUri)
    val exoPlayer = remember { exoUri }

    DisposableEffect(
        AndroidView(
            factory = { it.buildPlayerView(exoPlayer) },
            modifier = Modifier.fillMaxSize()
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    ProvideWindowInsets {
        Surface(
            color = colorResource(id = R.color.nc_green).copy(alpha = 0.6f),
            elevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                Modifier
                    .navigationBarsWithImePadding()
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                inflateScreenView()

                Divider(
                    color = Color.White.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 48.dp)
                )
                //tabs
                Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.weight(2f, true)) {
                    inflateTabs()
                }

            }
        }
    }
}

@Preview
@Composable
fun inflateScreenView() {
    Column() {
        // top brand logo
        Column(
            modifier = Modifier
                .weight(2f, true)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_child_care_24),
                null,
                Modifier.size(80.dp),
                tint = Color.White
            )
        }

        //login screen (form details)
        Column(modifier = Modifier.weight(5f, true)) {
            inflateLoginView()
        }
    }
}


@Preview
@Composable
fun inflateLoginView() {
    //heading
    Text(
        text = "Log in or sign up", color = colorResource(id = R.color.white), style =
        androidx.compose.material.MaterialTheme.typography.h4
    )

    Spacer(modifier = Modifier.height(8.dp))

    ContentColorComponent(contentColor = Color.White) {
        outLinedTextView("PHONE", "Enter your 10 digit phone number", Icons.Default.Phone)
    }

    lineBreakerWithText("OR")

    ContentColorComponent(contentColor = Color.White) {
        outLinedTextView("EMAIL", "Enter your email", Icons.Default.Email)
    }
    CreateBroadButton(label = "CONTINUE") {}

    Column(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateMediumButton(label = "CONTINUE WITH APPLE") {}
        CreateMediumButton(label = "CONTINUE WITH GOOGLE") {}
    }
}

@Preview
@Composable
fun inflateTabs() {
    loadImage("Rental")
    loadImage("Bookings")
    loadImage("Account")
    loadImage("VIP Club")
    loadImage("More")
}

@Composable
fun loadImage(tabName: String ?= ""){
    Box {
        Image(
            imageVector = Icons.Default.Email,
            contentDescription = "This is content",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(80.dp)
                .clip(RectangleShape)
                /*.border(
                    1.8.dp, androidx.compose.material.MaterialTheme.colors.secondary,
                    CircleShape
                )*/
        )

        Text( text = tabName ?: "NoName", color = colorResource(id = R.color.white), textAlign = TextAlign.Center)
    }
}

@Composable
fun ContentColorComponent(
    contentColor: Color = LocalContentColor.current,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        content = content
    )
}

@Composable
fun outLinedTextView(textLabel: String, placeholderText: String, iconImg: ImageVector) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = text,
        leadingIcon = {
            Icon(
//                imageVector = Icons.Default.Email,
                imageVector = iconImg,
                contentDescription = null,
                tint = Color.White
            )
        },
        trailingIcon = { if(text != null )
            trailingIconView(false)
        else trailingIconView(true)},
        /*trailingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Color.White
            )
        },*/
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = { Text(text = textLabel, color = colorResource(id = R.color.white)) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.white),
            unfocusedBorderColor = colorResource(id = R.color.white),
            unfocusedLabelColor = colorResource(id = R.color.white)
        ),
        placeholder = {
            Text(
                text = placeholderText,
                color = colorResource(id = R.color.white)
            )
        },
        onValueChange = {
            text = it
        },
        singleLine = true,
    )
}

@Composable
fun trailingIconView(isBlank: Boolean ?= false){
    var text by remember { mutableStateOf("") }
    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                text = ""
            },
        ) {
            Icon(
                if(isBlank!!)
                Icons.Default.Edit else Icons.Default.Close,
                contentDescription = "",
                tint = Color.Black
            )
        }

    }
}
@Preview
@Composable
fun lineBreakerWithText(textValue: String? = "OR") {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(4f, false)
        ) {
            Divider(
                modifier = Modifier
                    .height(height = 2.dp),
                color = colorResource(id = R.color.light_line),
                thickness = 1.dp,
            )
        }

        /**text */
        Row(
            modifier = Modifier
                .padding(16.dp)
                .weight(2f, false)
        ) {
            androidx.compose.material3.Text(
                text = textValue ?: "",
                color = colorResource(id = R.color.light_gray)
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(4f, false)
        ) {
            Divider(
                modifier = Modifier
                    .height(height = 2.dp),
                color = colorResource(id = R.color.light_line),
                thickness = 1.dp,
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}


@Preview
@Composable
fun previewEditText() {
    Column() {
        ContentColorComponent(contentColor = Color.White) {
            outLinedTextView("Email", "Enter your email", Icons.Default.Phone)
            /*OutlinedTextField(
                value = text,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = "Email address", color = colorResource(id = R.color.white)) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.white),
                    unfocusedBorderColor = colorResource(id = R.color.white),
                    unfocusedLabelColor = colorResource(id = R.color.white)
                ),
                placeholder = {
                    Text(
                        text = "Your email",
                        color = colorResource(id = R.color.white)
                    )
                },
                onValueChange = {
                    text = it
                },
                singleLine = true,
            )*/
        }
    }
}

@Preview
@Composable
fun CardViewEditText(setTextValue: String? = "") {
    var name by remember {
        mutableStateOf(setTextValue)
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember {
        FocusRequester()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .shadow(
                ambientColor = Color.Blue,
                spotColor = Color.Cyan,
                elevation = if (isFocused) 15.dp else 0.dp,
                clip = true,
                shape = CircleShape
            ),
        shape = CircleShape
    ) {
        OutlinedTextField(
            value = name ?: "",
            onValueChange = { name = it },
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(listOf(Color.Cyan, Color.Blue)),
                    shape = CircleShape
                )
                .padding(16.dp)
                .background(Color.White)
                .focusRequester(focusRequester),
        )
    }
}


/////////////Globals

@Composable
fun CreateBroadButton(label: String, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(8.dp))

    androidx.compose.material3.Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.white)),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .heightIn(min = 80.dp, 100.dp)
            .padding(16.dp)
    ) {
        androidx.compose.material3.Text(
            text = "$label",
            color = colorResource(id = R.color.nc_green),
            fontSize = 18.sp,
            style = androidx.compose.material.MaterialTheme.typography.button,
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CreateMediumButton(label: String, onClick: () -> Unit) {
    androidx.compose.material3.Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.white)),
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "heart icon",
            modifier = Modifier.size(ButtonDefaults.IconSize),
            tint = Color.Gray
        )
        androidx.compose.material3.Text(
            text = "$label",
            color = colorResource(id = R.color.nc_green),
            fontSize = 18.sp,
            style = androidx.compose.material.MaterialTheme.typography.button,
        )
    }
}






