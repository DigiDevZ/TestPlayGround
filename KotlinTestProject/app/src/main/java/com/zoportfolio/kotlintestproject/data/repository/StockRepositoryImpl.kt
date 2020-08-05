package com.zoportfolio.kotlintestproject.data.repository

import androidx.lifecycle.LiveData
import com.zoportfolio.kotlintestproject.data.db.AssetDao
import com.zoportfolio.kotlintestproject.data.db.entity.AssetEntry
import com.zoportfolio.kotlintestproject.data.network.StockNetworkDataSource
import com.zoportfolio.kotlintestproject.data.network.retrofitTest.response.AssetResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StockRepositoryImpl(
    private val assetDao: AssetDao,
    private val stockNetworkDataSource: StockNetworkDataSource
) : StockRepository {

    init {
        stockNetworkDataSource.downloadedAssetData.observeForever { newAssetData ->
            persistFetchedAssetData(newAssetData)
        }
    }

    override suspend fun getAssetData(symbol: String): LiveData<AssetEntry> {
        return withContext(Dispatchers.IO) {
            initStockData(symbol)
            return@withContext assetDao.getAssetEntry(symbol)
        }
    }

    private fun persistFetchedAssetData(fetchedAsset: AssetResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            //I should ask if this acceptable to do here, question:
            // Having to create the entry val object since I have the AssetEntry and AssetResponse class, potentially I could just make the AssetEntry and AssetResponse class the same.
            val fetchedAssetEntry = AssetEntry(assetId = fetchedAsset.assetId,
                assetClass = fetchedAsset.assetClass,
                assetExchange = fetchedAsset.assetExchange,
                assetSymbol = fetchedAsset.assetSymbol,
                assetStatus = fetchedAsset.assetStatus,
                assetTradable = fetchedAsset.assetTradable,
                assetMarginable = fetchedAsset.assetMarginable,
                assetShortable = fetchedAsset.assetShortable,
                assetEasyToBorrow = fetchedAsset.assetEasyToBorrow
            )
            assetDao.upsert(fetchedAssetEntry)
        }
    }

    private suspend fun initStockData(symbol: String) {
        fetchAssetData(symbol)
    }

    private suspend fun fetchAssetData(symbol: String) {
        stockNetworkDataSource.fetchAssetData(symbol)
    }
}