package com.zoportfolio.kotlintestproject.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zoportfolio.kotlintestproject.data.db.entity.AssetEntry
import kotlinx.coroutines.selects.select

@Dao
interface AssetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(assetEntry: AssetEntry)

    @Query("select * from asset_data where assetSymbol = :assetSymbol" )
    fun getAssetEntry(assetSymbol: String) : LiveData<AssetEntry>
}