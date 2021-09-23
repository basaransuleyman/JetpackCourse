package com.example.jetpack.networking.adapters.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack.R
import com.example.jetpack.databinding.CountryItemsBinding
import com.example.jetpack.models.Country
import com.example.jetpack.network.adapters.CountryClickListener
import com.example.jetpack.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.country_items.view.*

class CountryAdapter(val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {

    class CountryViewHolder(var view: CountryItemsBinding) : RecyclerView.ViewHolder(view.root)

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.country = countryList[position]
        holder.view.listener = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CountryItemsBinding>(
            inflater,
            R.layout.country_items,
            parent,
            false
        )
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryWhenRefresh(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()//refresh to adapter
    }

    override fun onCountryClicked(view: View) {
        super.onCountryClicked(view)
        val uuid = view.tvCountryUuid.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedToCountries(uuid)
        Navigation.findNavController(view).navigate(action)
    }

}