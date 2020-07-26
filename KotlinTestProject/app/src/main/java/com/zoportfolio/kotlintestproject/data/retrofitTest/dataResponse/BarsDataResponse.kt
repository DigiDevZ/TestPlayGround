package com.zoportfolio.kotlintestproject.data.retrofitTest.dataResponse

import com.google.gson.annotations.SerializedName

class BarsDataResponse {

    //TODO: Got lost on issue.
    // The response in this request is an object that has a dynamic key based on the symbol.
    // Need to further research and look this up.

    @SerializedName("data")
    val assetBarsData : List<BarObjectDataResponse>? = null

}