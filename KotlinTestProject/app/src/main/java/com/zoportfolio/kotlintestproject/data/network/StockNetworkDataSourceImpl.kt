package com.zoportfolio.kotlintestproject.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zoportfolio.kotlintestproject.data.network.retrofitTest.apiService.PaperAPIService
import com.zoportfolio.kotlintestproject.data.network.retrofitTest.response.AssetResponse
import com.zoportfolio.kotlintestproject.internal.NoConnectivityException

class StockNetworkDataSourceImpl(
    private val paperAPIService: PaperAPIService
) : StockNetworkDataSource {

    private val _downloadedAssetData = MutableLiveData<AssetResponse>()

    override val downloadedAssetData: LiveData<AssetResponse>
        get() = _downloadedAssetData

    override suspend fun fetchAssetData(symbol: String) {
        try {
            val fetchedAssetData = paperAPIService
                .getTradingAsset(symbol)
                .await()
            _downloadedAssetData.postValue(fetchedAssetData)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}