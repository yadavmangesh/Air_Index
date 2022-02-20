package com.mangesh.air.indexx.ui.realtimegraph


import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mangesh.air.indexx.R
import com.mangesh.air.indexx.data.airIndexToValueChart
import com.mangesh.air.indexx.databinding.ActivityRealTimeBinding
import com.jjoe64.graphview.Viewport
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.mangesh.air.indexx.data.getAirQualityColor


class RealTimeActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRealTimeBinding
    private var cityName: String? = null
    private lateinit var viewModel: RealTimeViewModel

    private val series = LineGraphSeries<DataPoint>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_real_time)
        cityName = intent?.getStringExtra("city_name")
        viewModel = ViewModelProvider(this)[RealTimeViewModel::class.java]
        viewModel.cityName = cityName.toString()

        initView()
    }

    private fun initView() {

        binding.tvCity.text = cityName
        binding.anyChartView.addSeries(series)
        val viewport: Viewport = binding.anyChartView.viewport

        viewport.apply {
           isXAxisBoundsManual = false
            setMinY(0.0)
            setMaxY(400.0)
            setMinX(1.0)
            setMaxX(30.0)
            isScrollable = true
        }

        var count = 0
        viewModel.getCityAirIndex(cityName).observe(this, {
            if (it != null) {
                binding.tvCategory.background.setColorFilter(ContextCompat.getColor(this@RealTimeActivity,
                    getAirQualityColor(it.airQuality)),PorterDuff.Mode.SRC_ATOP)
                Log.d("TAG", "getCityAirIndex: ${it.cityName}  AQI ${it.currentAQI}")
                binding.tvCity.text = "Air Quality is ${it.airQuality} in $cityName"
                series.appendData(airIndexToValueChart(++count,it),true,30)
                binding.anyChartView.addSeries(series)
            }
        })
    }
}