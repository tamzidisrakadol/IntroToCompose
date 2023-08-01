package com.example.introtocompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Preview
@Composable
fun previewItem(){
    LazyColumn(content = {
        items(getCategoryList()){
            blogCategory(title = it.title, subtitle = it.subtitle )
        }
    })
}




@Composable
fun blogCategory(title:String,subtitle:String){
    Card(
        modifier = Modifier.padding(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.user),
                contentDescription = "Image",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .weight(.2f)
            )
            itemDecription(title, subtitle,Modifier.weight(.8f))
        }
    }
}

@Composable
private fun itemDecription(title: String, subtitle: String,modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = subtitle, fontWeight = FontWeight.Light, fontSize = 12.sp,
            style = MaterialTheme.typography.bodySmall
        )

    }
}


data class Category(val title:String,val subtitle:String)

fun getCategoryList():MutableList<Category>{
    val list = mutableListOf<Category>()
    list.add(Category("Java developer","Intermediate"))
    list.add(Category("C developer","Expert"))
    list.add(Category("C++ developer","Intermediate"))
    list.add(Category("C# developer","Expert"))
    list.add(Category("Python developer","Intermediate"))
    list.add(Category("Js developer","Expert"))
    list.add(Category("Node developer","Intermediate"))
    list.add(Category("Android developer","Expert"))
    list.add(Category("Flutter developer","Intermediate"))
    list.add(Category("Ios developer","Expert"))
    list.add(Category("Java developer","Intermediate"))
    list.add(Category("C developer","Expert"))
    list.add(Category("C++ developer","Intermediate"))
    list.add(Category("C# developer","Expert"))
    list.add(Category("Python developer","Intermediate"))
    list.add(Category("Js developer","Expert"))
    list.add(Category("Node developer","Intermediate"))
    list.add(Category("Android developer","Expert"))
    list.add(Category("Flutter developer","Intermediate"))
    list.add(Category("Ios developer","Expert"))

    return list
}