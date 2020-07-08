package com.zoportfolio.kotlintestproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: For this test project I want to simple do the following,
        // Grab on stocks data from the alpaca api using retrofit
        // display that data in a spark view from the robinhood library, and a robinhood ticker,
        // use dependency injection to make the code more suitable and readable,
        // and finally, use the MVVM Architecture as a baseline.
    }
}