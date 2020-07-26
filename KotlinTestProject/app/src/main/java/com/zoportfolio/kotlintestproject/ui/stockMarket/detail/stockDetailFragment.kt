package com.zoportfolio.kotlintestproject.ui.stockMarket.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoportfolio.kotlintestproject.R

class stockDetailFragment : Fragment() {

    companion object {
        fun newInstance() = stockDetailFragment()
    }

    private lateinit var viewModel: StockDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stock_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StockDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}