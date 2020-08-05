package com.zoportfolio.kotlintestproject.data.network

import androidx.lifecycle.LiveData
import com.zoportfolio.kotlintestproject.data.network.retrofitTest.response.AssetResponse

interface StockNetworkDataSource {

    val downloadedAssetData: LiveData<AssetResponse>

    suspend fun fetchAssetData(
        symbol: String
    )

}