package com.mangesh.air.indexx.ui.aqi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mangesh.air.indexx.R
import com.mangesh.air.indexx.data.enitity.AirIndex
import com.mangesh.air.indexx.databinding.ActivityMainBinding
import com.mangesh.air.indexx.ui.realtimegraph.RealTimeActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var airIndexAdapter: AirIndexAdapter

    private lateinit var airIndexViewModel: AirIndexViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        airIndexViewModel = ViewModelProvider(this)[AirIndexViewModel::class.java]
        initView()
    }

    private fun initView() {
        airIndexAdapter = AirIndexAdapter(onClickItem)
        binding.rvAirIndex.apply {
            adapter = airIndexAdapter
            itemAnimator = null
        }
        setObserver()
    }

    private fun setObserver() {
       airIndexViewModel.getAirIndexList().observe(this,{
           if (it!=null) airIndexAdapter.submitList(it)
       })
    }

    private val onClickItem: (airIndex: AirIndex) -> Unit = {
         startActivity(Intent(this, RealTimeActivity::class.java).apply {
             putExtra("city_name",it.cityName)
         })
    }
}