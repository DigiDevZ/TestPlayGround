package com.zoportfolio.kotlintestproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.zoportfolio.kotlintestproject.retrofitTest.PaperAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity.TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: For this test project I want to simple do the following,
        // Grab on stocks data from the alpaca api using retrofit
        // display that data in a spark view from the robinhood library, and a robinhood ticker,
        // use dependency injection to make the code more suitable and readable,
        // and finally, use the MVVM Architecture as a baseline.


        /**
         * COROUTINES AND RETROFIT SECTION
         */


        //FIRST ATTEMPT - *


//        val retrofit = Retrofit.Builder()
//            .baseUrl(APIContracts.API_BASEPOINT_TRADING)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

//        Log.i(TAG, "onCreate: retrofit built, commencing service")

//        val service = retrofit.create(AssetDataService::class.java)
//        val call = service.getTradingAsset("AMD", APIContracts.API_KEY, APIContracts.API_KEY_ID)
//        call.enqueue(object : Callback<AssetDataResponse> {
//            override fun onResponse(
//                call: Call<AssetDataResponse>,
//                response: Response<AssetDataResponse>
//            ) {
//                if(response.code() == 200) {
//                    val assetDataResponse = response.body()
//
//                    val stringBuilder = "Asset Symbol: " +
//                            assetDataResponse?.assetSymbol!!.toString()
//
//                }
//            }
//            override fun onFailure(call: Call<AssetDataResponse>, t: Throwable) {
//                Log.i(tag, "onFailure: " + t.localizedMessage)
//            }
//        })


        //SECOND ATTEMPT - **
        //Create the service object.
        val assetDataService = PaperAPIService()

        //Using coroutines, by accessing it through GlobalScope.launch
        // and using the IO thread for background operations. (Main thread is for UI operations.)
        //TODO: NOTE using the Main thread for testing purposes, but will switch to IO later.
        GlobalScope.launch(Dispatchers.Main) {
            //Call getTradingAsset and await for a response, after the response is filled, assetDataResponse will be filled and fully instantiated.
            //GlobalScope allows for the code in this block to be pushed off until ready.
            val assetDataResponse = assetDataService.getTradingAsset("SPY").await()
            Log.i(TAG, "onCreate: ${assetDataResponse.assetClass}")

            val toast = Toast.makeText(this@MainActivity, assetDataResponse.assetClass, Toast.LENGTH_LONG)
            toast.show()
        }



    }


}