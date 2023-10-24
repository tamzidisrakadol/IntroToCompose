package com.example.introtocompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.introtocompose.Components.HeaderTextComponent
import com.example.introtocompose.Components.NameTextField
import com.example.introtocompose.Components.NormalTextComponent
import com.example.introtocompose.R


@Preview
@Composable
fun SignUpScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NormalTextComponent(value = "Hey there")
            HeaderTextComponent(value = "Create an account")
            Spacer(modifier = Modifier.height(20.dp))

            NameTextField(value = "Name", painter = painterResource(id = R.drawable.person))
            NameTextField(value = "Name", painter = painterResource(id = R.drawable.email))
            NameTextField(value = "Name", painter = painterResource(id = R.drawable.baseline_local_phone_24))
            NameTextField(value = "Name", painter = painterResource(id = R.drawable.pass))

        }

    }

}

