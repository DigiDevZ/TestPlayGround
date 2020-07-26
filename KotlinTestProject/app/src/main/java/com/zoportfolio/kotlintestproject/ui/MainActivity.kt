package com.zoportfolio.kotlintestproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.zoportfolio.kotlintestproject.R
import com.zoportfolio.kotlintestproject.data.retrofitTest.apiService.PaperAPIService
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
        val assetDataService =
            PaperAPIService()

        //Using coroutines, by accessing it through GlobalScope.launch
        // and using the IO thread for background operations. (Main thread is for UI operations.)
        GlobalScope.launch(Dispatchers.IO) {
            //Call getTradingAsset and await for a response, after the response is filled, assetDataResponse will be filled and fully instantiated.
            //GlobalScope allows for the code in this block to be pushed off until ready.
            val assetDataResponse = assetDataService.getTradingAsset("SPY").await()
            Log.i(TAG, "onCreate: ${assetDataResponse.assetClass}")

//            val toast = Toast.makeText(this@MainActivity, assetDataResponse.assetClass, Toast.LENGTH_LONG)
//            toast.show()
        }

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

        //TODO: Document everything I added yesterday and then move onto part 2 of the MVVM Youtube guide.

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