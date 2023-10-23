package com.example.introtocompose.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.introtocompose.model.Quote

@Composable
fun QuoteListShow(data: Array<Quote>,onClick:(quote:Quote)->Unit){
    LazyColumn(content = {
        items(data){
            QuoteListItem(quote = it,onClick)
        }
    })
}