package com.example.introtocompose

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.introtocompose.Components.PickImageFromGallery
import com.example.introtocompose.app.PostOffice
import com.example.introtocompose.screens.QuoteDetail
import com.example.introtocompose.screens.QuoteListItem
import com.example.introtocompose.screens.QuoteListScreen
import com.example.introtocompose.screens.TipCalculatorScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        CoroutineScope(Dispatchers.IO).launch {
//            DataManager.loadAssetFromFile(applicationContext)
//        }

        setContent {
            PickImageFromGallery()
        }
    }
}


//Quote ui
enum class ShowPages{
    LISTING,
    DETAILS
}


@Preview
@Composable
fun AppQuoteScreen(){
    if(DataManager.isDataLoaded.value){
        if (DataManager.currentPage.value ==ShowPages.LISTING){
            QuoteListScreen(data = DataManager.data) {
                DataManager.switchPages(it)
            }
        }else{
            DataManager.currentQuote?.let {
                QuoteDetail(quote = it)
            }
        }
        
    }else{
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(1f)
        ){
            Text(text = "Loading",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }

}


@Composable
fun TextComponent(textvalue: String, shadowColor: Color) {
    val shadowOffset = Offset(x = -40f, y = 4f)
    Text(
        text = textvalue,
        modifier = Modifier
            .width(80.dp)
            .height(80.dp)
            .background(Color.LightGray)
            .padding(horizontal = 18.dp, vertical = 28.dp),

        fontSize = 20.sp,
        fontStyle = FontStyle.Normal,
        color = Color.Black,
        style = TextStyle(
            fontSize = 24.sp,
            fontStyle = FontStyle.Normal,
            shadow = Shadow(shadowColor, shadowOffset, 2f)
        )
    )
}


@Composable
fun EvenNumber() {
    for (number in 1..10) {
        if (number % 2 ==0) {
            val color = Color(
                red = Random.nextInt(256),
                green = Random.nextInt(256),
                blue = Random.nextInt(256)
            )
            TextComponent(textvalue = number.toString(), shadowColor = color)
        }
    }
}


@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
       EvenNumber()
        Row {
            EvenNumber()
        }
    }
}

// ** add those file in onCreate to see the result **//
//val painter = painterResource(id = R.drawable.car)
//val description = "Luxurious Card"
//val title = "F and F"
//
//Box(
//modifier = Modifier
//.fillMaxWidth(0.5f)
//.padding(16.dp)
//) {
//    CardViewExample(painter = painter, contentDescription = description, title = title)
//}

@Composable
private fun CardViewExample(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
    ) {
        Box(modifier = Modifier.height(200.dp)) {

            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )

            //to add a extra color of layer in the end
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Blue
                            ),
                            startY = 400f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = title,
                    style = TextStyle(color = Color.White),
                    fontSize = 16.sp,
                )
            }
        }
    }
}


//open time picker dialog
@Composable
private fun showTimePicker(context: Context) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)


    val time = remember {
        mutableStateOf("")
    }

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            time.value = "$hour : $minute"
        }, hour, minute, false
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Selected Date: ${time.value}")
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = {
            timePickerDialog.show()
        }) {
            Text(text = "Select Time")
        }
    }
}

//open date picker dialog
@Composable
private fun showDatePicker(context: Context) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    val date = remember {
        mutableStateOf("")
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth / $month / $year"
        }, year, month, dayOfMonth
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Selected Date: ${date.value}")
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = {
            datePickerDialog.show()
        }) {
            Text(text = "Select Date")
        }
    }
}


//text in compose
@Composable
fun sayCheeze(name: String = "Cheezzy ") {
    Text(
        text = "hello $name",
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.ExtraBold,
        color = Color.Red,
        textAlign = TextAlign.Start
    )
}


//image in compose
@Composable
private fun previewImage() {
    Image(
        painter = painterResource(id = R.drawable.mobil),
        contentDescription = "Description Image",
//        colorFilter = ColorFilter.tint(Color.Green)
//        contentScale = ContentScale.Crop
    )
}


//button with click in compose
@Composable
private fun btnfunc() {

    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
//            containerColor = Color.White,
        )
    ) {
        Text(text = "Click Btn")
        Image(
            painter = painterResource(id = R.drawable.add),
            contentDescription = "Dummy"
        )
    }

}


//edittext in compose
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextPrev() {
    //state will reserve the value everytime after changes
    val state = remember {
        mutableStateOf("")
    }
    TextField(
        value = state.value,
        onValueChange = {
            state.value = it
        },
        label = { Text(text = "Enter Message") },
        placeholder = { Text(text = "The Name") },

        )
}


//row and col in compose
@Composable
private fun rowAndColPrev() {
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

@Composable
private fun listView() {
    Row(Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "",
            Modifier.size(40.dp)
        )
        Column {
            Text(
                text = "Adol",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Android Developer",
                fontWeight = FontWeight.ExtraLight,
                fontSize = 15.sp
            )
        }
    }
}


//modifier in compose

@Composable
private fun modifyFun() {
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
private fun circularImage() {
    Image(
        painter = painterResource(id = R.drawable.mobil),
        contentDescription = "mobile",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Black, CircleShape)
    )
}

//side effects in compose
//if we need any function to execute only one time we can use launchedEffect(_)

@Composable
private fun Counter() {
    var counter = remember { mutableStateOf(0) }
    var key = counter.value % 3 == 0  // it will change the value of key
    LaunchedEffect(key1 = key) {  //it will run for only 1 time unless the value of is key is changed
        Log.d("count", "${counter.value}")
    }
    Button(onClick = { counter.value++ }) {
        Text(text = "increment")
    }
}


//remember coroutineScope
@Composable
private fun LaunchedEffectComposable() {
    var count = remember {
        mutableStateOf(0)
    }

    var scope = rememberCoroutineScope()  // it can only use in launchedEffect or event like button

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            Log.d("count", "Started")
            try {
                for (i in 1..10) {
                    count.value++
                    delay(1000)
                }
            } catch (e: Exception) {
                Log.d("count", e.toString())
            }
        }
    }


    var text = "counter is running ${count.value}"
    if (count.value == 10) {
        text = "Counter stopped"
    }

    Text(text = text)
}

//RememberUpdateState
@Composable
private fun App() {
    var count = remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit) {
        delay(2000)
        count.value = 10
    }
    Counter(value = count.value)
}

@Composable
private fun Counter(value: Int) {
    val state = rememberUpdatedState(newValue = value) //it will remember the value of updated state
    LaunchedEffect(key1 = Unit) {
        delay(5000)
        Log.d("count", state.value.toString())
    }

    Text(text = value.toString())
}


//DISPOSABLE EFFECT
@Composable
private fun DisposableExample() {
    var state = remember {
        mutableStateOf(false)
    }

    DisposableEffect(key1 = state.value) {
        Log.d("count", "Disposable effect started")
        onDispose {
            Log.d("count", "Cleaning up items")
        }
    }

    Button(onClick = {
        state.value = !state.value
    }) {
        Text(text = "changeState")
    }
}

@Composable
private fun KeyboardComposable() {
    val view = LocalView.current
    DisposableEffect(key1 = Unit) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val insets = ViewCompat.getRootWindowInsets(view)
            val isKeyboardVisible = insets?.isVisible(WindowInsetsCompat.Type.ime())
            Log.d("count", isKeyboardVisible.toString())
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}



