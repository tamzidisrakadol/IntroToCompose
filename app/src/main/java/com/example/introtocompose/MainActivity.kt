package com.example.introtocompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.introtocompose.ui.theme.IntroToComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           DisposableExample()
        }
    }
}


//text in compose
@Composable
fun sayCheeze(name:String="Cheezzy "){
    Text(text = "hello $name",
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.ExtraBold,
        color= Color.Red,
        textAlign = TextAlign.Start
    )
}


//image in compose
@Composable
private fun previewImage(){
    Image(painter = painterResource(id = R.drawable.mobil),
        contentDescription = "Description Image",
//        colorFilter = ColorFilter.tint(Color.Green)
//        contentScale = ContentScale.Crop
    )
}


//button with click in compose
@Composable
private fun btnfunc(){

    Button(onClick = {},
        colors = ButtonDefaults.buttonColors(
//            containerColor = Color.White,
        )) {
        Text(text = "Click Btn")
        Image(painter = painterResource(id = R.drawable.add),
            contentDescription = "Dummy")
    }

}


//edittext in compose
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextPrev(){
    //state will reserve the value everytime after changes
    val state = remember {
        mutableStateOf("")
    }
    TextField(value = state.value,
        onValueChange = {
            state.value=it
        },
        label = { Text(text = "Enter Message")},
        placeholder = { Text(text = "The Name")},

    )
}


//row and col in compose
@Composable
private fun rowAndColPrev(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = "Tamzid")
        Text(text = "Israk")
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(text = "Adol")
            Text(text = "Android")
        }
    }
}



//list in compose
@Preview(showBackground = true, widthDp = 300, heightDp = 500)
@Composable
private fun listView(){
    Row(Modifier.padding(8.dp)) {
        Image(painter = painterResource(id = R.drawable.user),
            contentDescription = "",
            Modifier.size(40.dp)
        )
        Column {
            Text(
                text = "Adol",
                fontWeight = FontWeight.Bold
            )
            Text(text = "Android Developer",
                fontWeight = FontWeight.ExtraLight,
                fontSize = 15.sp)
        }
    }
}


//modifier in compose
@Preview(showBackground = true, widthDp = 300, heightDp = 500)
@Composable
private fun modifyFun(){
    Text(text = "Modifier",
        color = Color.White,
        modifier = Modifier
            .clickable { }
            .background(Color.Magenta)
            .size(200.dp)
            .border(4.dp, Color.Red)
            .clip(CircleShape)
            .background(Color.Yellow)
        )
}


//circular image in jetpack
@Composable
private fun circularImage(){
    Image(painter = painterResource(id = R.drawable.mobil),
        contentDescription = "mobile",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Black, CircleShape))
}

//side effects in compose
//if we need any function to execute only one time we can use launchedEffect(_)

@Composable
private fun Counter(){
    var counter = remember{ mutableStateOf(0) }
    var key = counter.value%3==0  // it will change the value of key
    LaunchedEffect(key1 = key,){  //it will run for only 1 time unless the value of is key is changed
        Log.d("count","${counter.value}")
    }
    Button(onClick = { counter.value++ }) {
        Text(text = "increment")
    }
}



//remember coroutineScope
@Composable
private fun LaunchedEffectComposable(){
    var count= remember {
        mutableStateOf(0)
    }

    var scope = rememberCoroutineScope()  // it can only use in launchedEffect or event like button

    LaunchedEffect(key1 = Unit){
        scope.launch {
            Log.d("count","Started")
            try {
                for (i in 1..10){
                    count.value++
                    delay(1000)
                }
            }catch (e:Exception){
                Log.d("count", e.toString())
            }
        }
    }
    

    var text = "counter is running ${count.value}"
    if (count.value==10){
        text="Counter stopped"
    }

    Text(text = text)
}

//RememberUpdateState
@Composable
private fun App(){
    var count= remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit){
        delay(2000)
        count.value=10
    }
    Counter(value = count.value)
}

@Composable
private fun Counter(value:Int){
    val state = rememberUpdatedState(newValue = value) //it will remember the value of updated state
    LaunchedEffect(key1 = Unit){
        delay(5000)
        Log.d("count",state.value.toString())
    }

    Text(text = value.toString())
}


//DISPOSABLE EFFECT
@Composable
private fun DisposableExample(){
    var state= remember {
        mutableStateOf(false)
    }

    DisposableEffect(key1 = state.value,){
        Log.d("count","Disposable effect started")
        onDispose {
            Log.d("count","Cleaning up items")
        }
    }

    Button(onClick = {
        state.value=!state.value
    }) {
        Text(text = "changeState")
    }
}



