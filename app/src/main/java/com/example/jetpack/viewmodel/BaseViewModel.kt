package com.example.jetpack.viewmodel

//AndroidViewModel same VieModel but need Application context parameter (app not down , safe class)
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main // first do job after return Main Thread

    override fun onCleared() { // for leak memory on RAM
        super.onCleared()
        job.cancel()
    }

}