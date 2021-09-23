package com.example.jetpack.networking.adapters.services

import com.example.jetpack.models.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<Country>>//Same as Observable but it can't pull elements endless

}