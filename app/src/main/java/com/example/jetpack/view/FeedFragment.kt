package com.example.jetpack.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpack.R
import com.example.jetpack.networking.adapters.adapters.CountryAdapter
import com.example.jetpack.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()
        rcCountyList.layoutManager = LinearLayoutManager(context)
        rcCountyList.adapter = countryAdapter

        srLayout.setOnRefreshListener {
            rcCountyList.visibility = View.GONE
            tvErrorCountry.visibility = View.GONE
            pbCountryLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPIWhenSwipe()
            srLayout.isRefreshing = false
        }
        observeLiveData()
    }

    private fun observeLiveData() {

        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                rcCountyList.visibility = View.VISIBLE
                countryAdapter.updateCountryWhenRefresh(countries)
            }
        })

        viewModel.countriesError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    tvErrorCountry.visibility = View.VISIBLE
                } else {
                    tvErrorCountry.visibility = View.GONE
                }
            }
        })

        viewModel.countriesLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    pbCountryLoading.visibility = View.VISIBLE
                    rcCountyList.visibility = View.GONE
                    tvErrorCountry.visibility = View.GONE
                } else {
                    pbCountryLoading.visibility = View.GONE
                }
            }
        })
    }

}