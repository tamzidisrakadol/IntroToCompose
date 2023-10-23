package com.example.introtocompose

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.introtocompose.model.Quote
import com.google.gson.Gson
import java.nio.charset.Charset

object DataManager {

    var data = emptyArray<Quote>()
    val isDataLoaded = mutableStateOf(false)
    val currentPage = mutableStateOf(ShowPages.LISTING)
    var currentQuote:Quote? = null

    fun loadAssetFromFile(context: Context){
        val inputStream = context.assets.open("quoteList.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        data = gson.fromJson(json,Array<Quote>::class.java)
        isDataLoaded.value = true
    }

    fun switchPages(quote: Quote?){
        if (currentPage.value == ShowPages.LISTING){
            currentQuote = quote
            currentPage.value = ShowPages.DETAILS
        }else{
            currentPage.value = ShowPages.LISTING
        }
    }
}