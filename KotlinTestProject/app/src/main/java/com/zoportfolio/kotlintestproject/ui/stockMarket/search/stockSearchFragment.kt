package com.zoportfolio.kotlintestproject.ui.stockMarket.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoportfolio.kotlintestproject.R

class stockSearchFragment : Fragment() {

    companion object {
        fun newInstance() =
            stockSearchFragment()
    }

    private lateinit var viewModel: StockSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stock_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StockSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}