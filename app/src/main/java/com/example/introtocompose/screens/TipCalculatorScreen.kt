package com.example.introtocompose.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(showSystemUi = true)
@Composable
fun TipCalculatorScreen() {
    
    Surface {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            MainContent()
        }
    }
}


@Composable
fun TopHeader(totalPerPerson: Double) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(15.dp))),
        color = Color(0XFFE9D7F7)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Total Per Person", style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = "$$total", style = TextStyle(
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.ExtraBold
                )
            )

        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainContent() {
    BillForm() {
        Log.d("bill", "${it.toInt() * 100}")
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    modify: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    val totalBillState = remember {
        mutableStateOf("")
    }

    //checking the input field is empty or not
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliderPositionState.value*100).toInt()

    val splitByState = remember {
        mutableStateOf(1)
    }

    val spiltRange = IntRange(start = 1, endInclusive = 100)
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val totalPerPersonState= remember {
        mutableStateOf(0.0)
    }

    val keyBoardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(15.dp))),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TopHeader(totalPerPersonState.value)
            Spacer(modifier = Modifier.height(10.dp))
            InputFields(valueState = totalBillState,
                labelId = "Enter Total Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValueChange(totalBillState.value.trim())
                    keyBoardController?.hide()
                })
            if (validState) {
                Row(
                    modifier = Modifier.padding(3.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Spilt",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically),
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(120.dp))

                    //negative value
                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                            splitByState.value =
                                if (splitByState.value > 1)
                                    splitByState.value - 1
                                else 1
                        })
                        Text(
                            text = "${splitByState.value}",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(start = 9.dp, end = 8.dp)
                        )

                        //positive value
                        RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                            if (splitByState.value < spiltRange.last){
                                splitByState.value=splitByState.value + 1
                            }
                        })
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = modify.padding(horizontal = 5.dp)) {
                    Text(
                        text = "Tip",
                        modifier = modify.align(alignment = Alignment.CenterVertically),
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = modify.width(180.dp))
                    Text(
                        text = "$ ${tipAmountState.value}", style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                //SLIDER
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$tipPercentage%", modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Slider(
                        value = sliderPositionState.value,
                        onValueChange = {
                            sliderPositionState.value = it
                            tipAmountState.value = calculateTotalTip(totalBill = totalBillState.value.toDouble(), tipPercentage =  tipPercentage)
                            totalPerPersonState.value= calculateTotalPerPerson(totalBillState.value.toDouble(),splitByState.value,tipPercentage)
                        },
                        modifier = modify.padding(start = 16.dp, end = 16.dp),
 //                       steps = 5,
                        onValueChangeFinished = {

                        }
                    )

                }
            }
        }
    }
}

fun calculateTotalTip(totalBill:Double,tipPercentage:Int):Double{
    return if (totalBill > 1 && totalBill.toString().isNotEmpty()){
        (totalBill * tipPercentage)/100
    }else{
        0.0
    }
}

fun calculateTotalPerPerson(totalBill: Double,spiltBy:Int,tipPercentage:Int):Double{
    val bill = calculateTotalTip(totalBill,tipPercentage)+totalBill
    return (bill/spiltBy)
}


//all components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFields(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = { Text(text = labelId) },
        leadingIcon = { Icon(imageVector = Icons.Rounded.AttachMoney, contentDescription = "") },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction

    )
}


val iconButtonSizeModifier = Modifier.size(30.dp)

@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black,
    bg: Color = MaterialTheme.colorScheme.onBackground,
    cardElevation: Dp = 4.dp
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable { onClick.invoke() }
            .then(iconButtonSizeModifier),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = cardElevation
        ),

        ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            tint = tint,
            modifier = Modifier.size(30.dp)
        )
    }
}
