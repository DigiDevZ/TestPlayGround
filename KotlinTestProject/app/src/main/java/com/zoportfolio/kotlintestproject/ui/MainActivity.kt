package com.zoportfolio.kotlintestproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.zoportfolio.kotlintestproject.R
import com.zoportfolio.kotlintestproject.data.network.ConnectivityInterceptor
import com.zoportfolio.kotlintestproject.data.network.ConnectivityInterceptorImpl
import com.zoportfolio.kotlintestproject.data.network.StockNetworkDataSource
import com.zoportfolio.kotlintestproject.data.network.StockNetworkDataSourceImpl
import com.zoportfolio.kotlintestproject.data.network.retrofitTest.apiService.PaperAPIService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity.TAG"

    private lateinit var  navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //TODO: For this test project I want to simple do the following,
        // Grab a stocks data from the alpaca api using retrofit
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
//        val assetDataService =
//            PaperAPIService()

        //Using coroutines, by accessing it through GlobalScope.launch
        // and using the IO thread for background operations. (Main thread is for UI operations.)
//        GlobalScope.launch(Dispatchers.IO) {
            //Call getTradingAsset and await for a response, after the response is filled, assetDataResponse will be filled and fully instantiated.
            //GlobalScope allows for the code in this block to be pushed off until ready.
//            val assetDataResponse = assetDataService.getTradingAsset("SPY").await()
//            Log.i(TAG, "onCreate: $assetDataResponse")

//            val toast = Toast.makeText(this@MainActivity, assetDataResponse.assetClass, Toast.LENGTH_LONG)
//            toast.show()
//        }

        //TODO: Check the BarsDataResponse TODO for more information on the below commented code.!!!
//        val barsDataService =
//            MarketDataAPIService()
//
//        GlobalScope.launch(Dispatchers.IO) {
//            val barsDataResponse = barsDataService.getTradingAssetBars("1D", "SPY").await()
//            for (bar in barsDataResponse) {
//                Log.i(TAG, "In for loop of barsDataResponse: \nbar open time: ${getDateTime(bar.barTime.toString())} \nbar volume: ${bar.barVolume}")
//            }
//        }

        // Summary:
        // Can use JSON to kotlin when creating a response class that will be handling JSON and used in retrofit requests.
        // NEED to make sure to use val/var and nullable/non-nullable when necessary, and set the only create annotations when needed to be true.

        /**
         * NAVIGATION SECTION
         */

        //Navigation Notes:
        // In order to utilize the google navigation architecture their are some dependencies that need to be implemented.
        // android.arch.navigation:navigation-(fragment/ui/etc..)

        // A navigation resource asset is needed as well. Can be seen in the res/navigation/mobile_navigation,
        // where all destinations need to be handled and added to.

        // A menu for navigation is useful as well, as can be seen with the menu resource in this project, that's a bottom navigation style

        // In order to get the right fragments for the job for MVVM, when creating a fragment class, choose the fragment (with viewmodel) option.
        // This will create a view model for the class, and also a layout file as well, and the fragment class. Very cool and easy to use.

        //To get the navigation controller,
        // use Navigation.findNavController(activity, id),
        // and direct it to the nav_host_fragment in the mainactivity.xml
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        //Assign the navController to the bottom_nav view.
        bottom_nav.setupWithNavController(navController)

        //Set the action bar with the navController.
        NavigationUI.setupActionBarWithNavController(this, navController)

        /**
         * ROOM DATABASE SECTION
         */

        //To create a table for a class, give it the @Entity(tableName = "name") Annotation.
        // And to have a value that is custom, not a primitive, use the @Embedded Annotation.
        // The table also needs a primary key, if the table will only have one entry ever, the ID can be a constant,
        // otherwise set the primary key to be auto generating and be an Int.

        //There are two types of query, @Query which is static and @RawQuery which takes in a premade query.
        // To use a dynamic WHERE, in the Dao get function, add a parameter for whatever you want to query by,
        // and then signify that in the query statement with a : (See AssetDao for example)

        // The database class needs to have a few things before it can be utilized correctly.
        // The following: @Database Annotation with entities and version in that annotation.
        // Inherit from RoomDatabase(), an abstract fun that calls the AssetDao or any Dao that it needs to access.
        // A companion object to make the class a singleton in kotlin.

        // IMPORTANT NOTE: Restructured the AssetResponse to be a data class, and put all of the vars in the constructor
        // Data classes in kotlin are useful ways to create and have a class that is used for holding data, AKA an object class.
        // The Data class automatically creates the utility functions like toString() and equal(),
        // in order to use it succesfully the data class must have all of its properties in the arguements/parameters section.
        // See AssetResponse for an example of a data class.

        //There is a difference between entry and response.
        // The entry will be used as the data object that is going to be stored in the database.
        // The response is the data object used to receive the JSON from the Request.
        // IMPORTANT NOTE: In my case I think I could safely use my AssetResponse as my entry class as well.
        // I just need to change the id to be nullable.

        //TODO: Write out all that I added in todays session, focused on part 4 of the tutorial.

        /**
         * NETWORK ABSTRACTION SECTION
         */

        //Basically you need a lot of interfaces to allow for abstraction and dependency injection.

        //Created another interceptor (ConnectivityInterceptor) to check for internet connection,
        // checks for the internet in both the SDK 29 way and previous ways (The previous ways are deperecated and I should be aware of that for the future.)

        //Abstracted a layer of using the PaperAPIService by creating another interface (StockNetworkDataSource)
        // Not sure how this one works so I will need to re-watch the video to reaffirm my understanding.
        // I know that this is supposed to allow for me to manipulate the data I receive in one class,
        // and that it returns and provides LiveData to be observed by the repository.

        //This is how I will be doing network calls from now on, need to describe this process.
        val apiService = PaperAPIService(ConnectivityInterceptorImpl(this))
        val stockNetworkDataSource = StockNetworkDataSourceImpl(apiService)

        stockNetworkDataSource.downloadedAssetData.observe(this, androidx.lifecycle.Observer {
            Log.i(TAG, it.toString())
        })

        GlobalScope.launch(Dispatchers.IO) {
            stockNetworkDataSource.fetchAssetData("AMD")
        }

        /**
         * REPOSITORY AND KODEIN SECTION
         */

        //TODO: Started tutorial 5
        // Go over what I learned from this tutorial video.



    }

    //This override is necessary for the navigation controller to work properly. Otherwise the back arrow wont work when pressed.
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }


   private fun getDateTime(s: String) : String? {
       return try {
           val sdf = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.US)
           val netDate = Date(s.toLong())
           val date = sdf.format(netDate)
           date.toString()
       } catch (e: Exception) {
           e.toString()
       }
    }



}