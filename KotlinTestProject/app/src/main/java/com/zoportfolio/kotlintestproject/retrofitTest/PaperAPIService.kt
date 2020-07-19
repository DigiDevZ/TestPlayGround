package com.zoportfolio.kotlintestproject.retrofitTest

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface PaperAPIService {

    //api.alpaca.markets

    //@Path is for adding in more paths that may need to be dynamic
    //@Query is for adding in queries that are structured as key=value

    //If I need to use a dynamic path in the url, then indicate that with = {pathKey}, and following it up with = @Path("pathKey") pathKey: String
    @GET("/v2/assets/{symbol}")
    fun getTradingAsset(@Path("symbol") symbol: String) : Deferred<AssetDataResponse>



    //Companion object is needed for every service, this will be how I add the header, and the deferred response.
    companion object {

        //operator fun invoke() will invoke the AssetDataService automatically.
        operator fun invoke(): PaperAPIService {

            //Need to create a request interceptor to add the header, which is the two api keys.
            val requestInterceptor = Interceptor { chain ->

                //Build the url.
                val url = chain
                    .request()
                    .url()
                    .newBuilder()
                    .build()

                //Build and edit the request by adding the two needed headers, this can change per API.
                val request = chain
                    .request()
                    .newBuilder()
                    .url(url)
                    .addHeader("APCA-API-KEY-ID", APIContracts.API_KEY_ID)
                    .addHeader("APCA-API-SECRET-KEY", APIContracts.API_KEY)
                    .build()

                //Return the request.
                return@Interceptor chain.proceed(request)
            }

            //Add the interceptor by interfacing with the OkHttpClient.
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            //Return this class, after building it with the needed properties
            // the client
            // the base url
            // call adapter factory
            // Gson converter factory
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(APIContracts.API_BASEPOINT_TRADING)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PaperAPIService::class.java)
        }
    }

}