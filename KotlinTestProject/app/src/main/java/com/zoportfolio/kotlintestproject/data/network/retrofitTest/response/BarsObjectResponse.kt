package com.zoportfolio.kotlintestproject.data.network.retrofitTest.response


import com.google.gson.annotations.SerializedName

data class BarsObjectResponse(
    val c: Double,
    val h: Double,
    val l: Double,
    val o: Double,
    val t: Int,
    val v: Int
)

//@SerializedName("t")
//var barTime: Int? = null//Unix epoch in seconds
//@SerializedName("o")
//var barOpenPrice: Double? = null//Open price of the bar
//@SerializedName("h")
//var barHighPrice: Double? = null//Highest price of the bar
//@SerializedName("l")
//var barLowPrice: Double? = null//Lowest price of the bar
//@SerializedName("c")
//var barClosePrice: Double? = null//Close price of the bar
//@SerializedName("v")
//var barVolume: Double? = null//Volume of the bar