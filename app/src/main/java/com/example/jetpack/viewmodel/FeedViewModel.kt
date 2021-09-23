package com.example.jetpack.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpack.models.Country
import com.example.jetpack.network.services.CountryDatabase
import com.example.jetpack.networking.adapters.services.CountryAPIService
import com.example.jetpack.utils.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L //10 minutes

    val countries = MutableLiveData<List<Country>>()
    val countriesError = MutableLiveData<Boolean>()
    val countriesLoading = MutableLiveData<Boolean>()


    fun refreshFromAPIWhenSwipe(){
        getDataFromAPI()
    }

    fun refreshData() {
        val updateTime = customPreferences.getTime()

        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }
    }

    private fun getDataFromSQLite() {
        countriesLoading.value = true
        launch {
            val countriesList = CountryDatabase(getApplication()).countryDao().getAllCountries()

            showCountries(countriesList)
            Toast.makeText(getApplication(), "Countries from SQLite", Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI() {
        countriesLoading.value = true
        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {

                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Countries from API", Toast.LENGTH_LONG)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        countriesError.value = true
                        countriesLoading.value = false
                    }
                })
        )
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countriesError.value = false
        countriesLoading.value = false
    }

    private fun storeInSQLite(list: List<Country>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()

            val listLong =
                dao.insertAll(*list.toTypedArray()) // toTypedArray do single item in list items (individual)
            var i = 0
            while (i < list.size) { //that while return Long to uuid
                list[i].uuid = listLong[i].toInt()
                i += 1
            }
            showCountries(list)
        }
        customPreferences.saveTime(System.nanoTime())
    }

}