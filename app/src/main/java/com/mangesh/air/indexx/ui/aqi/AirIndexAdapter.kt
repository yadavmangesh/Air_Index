package com.mangesh.air.indexx.ui.aqi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mangesh.air.indexx.data.enitity.AirIndex
import com.mangesh.air.indexx.data.getAirQualityColor
import com.mangesh.air.indexx.data.roundTo
import com.mangesh.air.indexx.databinding.ItemAirIndexBinding

class AirIndexAdapter(val onClickItem: (airIndex: AirIndex) -> Unit) :
    ListAdapter<AirIndex, AirIndexAdapter.AirViewHolder>(AirIndexDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirViewHolder {
        return AirViewHolder(
            ItemAirIndexBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AirViewHolder, position: Int) {
        val airIndex = currentList[position]
        holder.bind(airIndex)
    }

    class AirIndexDiff : DiffUtil.ItemCallback<AirIndex>() {
        override fun areItemsTheSame(oldItem: AirIndex, newItem: AirIndex): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AirIndex, newItem: AirIndex): Boolean {
            return oldItem == newItem
        }

    }

    inner class AirViewHolder(val binding: ItemAirIndexBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(airIndex: AirIndex) {
            var aqivalue = airIndex.currentAQI.roundTo(2).toString()
            binding.apply {
                tvCity.text = airIndex.cityName
                tvAqi.text = "${aqivalue.substringBefore(".")}. "
                tvAqiDecimal.text = aqivalue.substringAfter(".")
                tvLastUpdate.text = airIndex.lastMessage
                tvAirColor.apply {
                    text = airIndex.airQuality
                    setTextColor(
                        ContextCompat.getColor(
                            tvAirColor.context,
                            getAirQualityColor(airIndex.airQuality)
                        )
                    )
                }
                cardAqi.setCardBackgroundColor(
                    ContextCompat.getColor(
                        cardAqi.context,
                        getAirQualityColor(airIndex.airQuality)
                    )
                )
            }

            binding.mainCard.setOnClickListener {
                onClickItem.invoke(airIndex)
            }
        }

    }
}