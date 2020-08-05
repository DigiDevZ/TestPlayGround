package com.zoportfolio.kotlintestproject.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zoportfolio.kotlintestproject.data.db.entity.AssetEntry

@Database(
    entities = [AssetEntry::class],
    version = 1
)
abstract class StockDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao

    companion object {
        @Volatile
        private var instance: StockDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            StockDatabase::class.java, "stock.db")
                .build()
    }
}