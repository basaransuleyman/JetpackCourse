package com.example.jetpack.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.jetpack.R
import com.example.jetpack.databinding.FragmentCountriesBinding
import com.example.jetpack.utils.downloadFromUrl
import com.example.jetpack.utils.placeholderProgressBar
import com.example.jetpack.viewmodel.CountriesViewModel
import kotlinx.android.synthetic.main.fragment_countries.*

class CountriesFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var viewModel: CountriesViewModel
    private lateinit var dataBinding: FragmentCountriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_countries,
                container,
                false
            )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            countryUuid = CountriesFragmentArgs.fromBundle(it!!).countryuuid
        }

        viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)
        viewModel.getDataFromRoomDatabase(countryUuid)

        observerLiveData()
    }

    private fun observerLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                dataBinding.selectedCountry = country
            }
        })
    }

}