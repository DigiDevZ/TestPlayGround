package com.zoportfolio.kotlintestproject.data.repository

import androidx.lifecycle.LiveData
import com.zoportfolio.kotlintestproject.data.db.entity.AssetEntry

interface StockRepository {

    suspend fun getAssetData(symbol: String): LiveData<AssetEntry>

}