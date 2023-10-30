package com.example.introtocompose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalComposeUiApi::class)
@Preview(showSystemUi = true)
@Composable
fun TipCalculatorScreen() {
    Surface {
        Column {
            TopHeader()
            Spacer(modifier = Modifier.height(10.dp))
            MainContent()
        }
    }
}


@Composable
fun TopHeader(totalPerPerson: Double = 134.0) {
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
    val totalBillState = remember {
        mutableStateOf("")
    }

    //checking the input field is empty or not
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyBoardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            InputFields(valueState = totalBillState, labelId = "Enter Total Bill" , enabled =true , isSingleLine = true,
                onAction = KeyboardActions {
                if (validState) return@KeyboardActions
                    keyBoardController?.hide()
            })
        }
    }
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
        modifier = modifier.padding(10.dp).fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType=keyboardType, imeAction = imeAction)
    )
}
