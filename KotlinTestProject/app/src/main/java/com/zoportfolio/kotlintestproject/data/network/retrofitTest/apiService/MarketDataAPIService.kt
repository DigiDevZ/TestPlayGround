package com.zoportfolio.kotlintestproject.data.network.retrofitTest.apiService

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.zoportfolio.kotlintestproject.data.network.retrofitTest.APIContracts
import com.zoportfolio.kotlintestproject.data.network.retrofitTest.response.BarObjectDataResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarketDataAPIService {

    @GET("/v1/bars/{timeframe}")
    fun getTradingAssetBars(@Path("timeframe") timeframe : String,
                            @Query("symbols") symbols: String) : Deferred<List<BarObjectDataResponse>>
    //The first level of the JSON in this request, is the objects of each symbol.
    // SPY object with BarObjectDataResponse


    companion object {
        operator fun invoke(): MarketDataAPIService {

            //Create the requestInterceptor
            val requestInterceptor = Interceptor {chain ->  

                val url = chain
                    .request()
                    .url()
                    .newBuilder()
                    .build()

                val request = chain
                    .request()
                    .newBuilder()
                    .url(url)
                    .addHeader("APCA-API-KEY-ID",
                        APIContracts.API_KEY_ID
                    )
                    .addHeader("APCA-API-SECRET-KEY",
                        APIContracts.API_KEY
                    )
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(APIContracts.API_BASEPOINT_DATA)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarketDataAPIService::class.java)
        }
    }

}