package com.zoportfolio.kotlintestproject.data.network.retrofitTest.response


import com.google.gson.annotations.SerializedName

data class BarsResponse(
    //TODO: This serialized name will always be dynamic so I need to figure out a way for it to be handle dynamically according to MVVM.
    @SerializedName("SPY")
    val barsObjectResponse: List<BarsObjectResponse>
)