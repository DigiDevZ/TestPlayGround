package com.zoportfolio.kotlintestproject.ui.stockMarket.watchlist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoportfolio.kotlintestproject.R

class stockWatchlistFragment : Fragment() {

    companion object {
        fun newInstance() =
            stockWatchlistFragment()
    }

    private lateinit var viewModel: StockWatchlistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stock_watchlist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StockWatchlistViewModel::class.java)
        // TODO: Use the ViewModel
    }

}