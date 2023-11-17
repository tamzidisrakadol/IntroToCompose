package com.example.introtocompose.Components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.introtocompose.ui.theme.componentShape
import com.example.introtocompose.ui.theme.grey
import com.example.introtocompose.ui.theme.lightgrey


@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}


@Composable
fun HeaderTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameTextField(value: String, painter: Painter) {

    val textValue = remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShape.small),
        label = {
            Text(text = value)
        },
        value = textValue.value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = grey,
            focusedBorderColor = grey,
            cursorColor = grey,
            containerColor = lightgrey
        ),
        keyboardOptions = KeyboardOptions.Default,
        onValueChange = {
            textValue.value = it
        },
        leadingIcon = {
            Icon(painter = painter, contentDescription = "Quote")
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(value: String, painter: Painter) {

    val textValue = remember {
        mutableStateOf("")
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShape.small),
        label = {
            Text(text = value)
        },
        value = textValue.value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = grey,
            focusedBorderColor = grey,
            cursorColor = grey,
            containerColor = lightgrey
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {
            textValue.value = it
        },
        leadingIcon = {
            Icon(painter = painter, contentDescription = "Quote")
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            var description = if (passwordVisible.value) {
                "Hide Password"
            } else {
                "Show Password"
            }

            IconButton(onClick = {
                passwordVisible.value = !passwordVisible.value
            }) {
                Icon(imageVector = iconImage, contentDescription = "")
            }


        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}


@Composable
fun CheckboxComponent(value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkedState = remember {
            mutableStateOf(false)
        }

        Checkbox(checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
            })
        ClickableTextComponent(value = value)
    }
}


@Composable
fun ClickableTextComponent(value: String) {
    val initialText = "By continuing you accept our "
    val privacyText = "Privacy Policy"
    val andText = " and "
    val termsAndCondition = "Terms and Condition"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(
            style = SpanStyle(
                color = Color(0xFF7D5260)
            )
        ) {
            pushStringAnnotation(tag = privacyText, annotation = privacyText)
            append(privacyText)
        }
        append(andText)
        withStyle(
            style = SpanStyle(
                color = Color(0xFF7D5260)
            )
        ) {
            pushStringAnnotation(tag = termsAndCondition, annotation = termsAndCondition)
            append(termsAndCondition)
        }
    }
    ClickableText(text = annotatedString, onClick = {
        annotatedString.getStringAnnotations(it, it)
            .firstOrNull()?.also {
                Log.d("tag", "$it")
            }
    })
}


@Composable
fun PickImageFromGallery() {
    val context = LocalContext.current
    var imgUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =ActivityResultContracts.GetContent()){
        imgUri = it
    }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Button(
            onClick = { launcher.launch("image/*") }
        ) {
            Text(text = "Pick Images from Gallery")
        }
        Spacer(modifier = Modifier.height(20.dp))

        imgUri?.let {
            bitmap = if (Build.VERSION.SDK_INT<28){
                MediaStore.Images.Media.getBitmap(context.contentResolver,it)
            }else{
                val source = ImageDecoder.createSource(context.contentResolver,it)
                ImageDecoder.decodeBitmap(source)
            }
            Image(bitmap = bitmap?.asImageBitmap()!!, contentDescription ="", modifier = Modifier.size(300.dp))
        }
    }
}


@Composable
fun PickUpImageFromCamera(){
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()){
        bitmap = it
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { launcher.launch() }) {
            Text(text = "Pick Image from Camera")
        }
        Spacer(modifier = Modifier.height(25.dp))
        bitmap?.let{
            Image(bitmap = bitmap?.asImageBitmap()!!, contentDescription ="", modifier = Modifier.size(300.dp))
        }
    }
}