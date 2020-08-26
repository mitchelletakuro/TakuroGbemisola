package com.mitchelletakuro.gbemisolatakurofilter.ui.carowners.viewmodel

import android.app.Application
import android.content.Context
import android.os.Environment
import androidx.lifecycle.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.mitchelletakuro.gbemisolatakurofilter.model.CarOwnerItem
import com.mitchelletakuro.gbemisolatakurofilter.model.FilterItem
import com.mitchelletakuro.gbemisolatakurofilter.utils.filterCarOwners
import com.mitchelletakuro.gbemisolatakurofilter.utils.readDataFromRaw
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class CarOwnersViewModel(application: Application, private val Filter:FilterItem) :
AndroidViewModel(application) {

    private var job: Job? = null
    private var carOwners = ArrayList<CarOwnerItem>()

    private val _carOwnersList = MutableLiveData<List<CarOwnerItem>>()
    val carOwnersList: LiveData<List<CarOwnerItem>>
        get() = _carOwnersList

    private var _showSnackbarEvent = MutableLiveData<Boolean?>()
    val showSnackBarEvent: LiveData<Boolean?>
        get() = _showSnackbarEvent


    init {
        job = viewModelScope.launch {
            getCarOwners()
        }
    }

    suspend fun getCarOwners() {
        withContext(Dispatchers.IO) {
            try {
                carOwners = readDataFromRaw()
                _carOwnersList.postValue(filterCarOwners(Filter, carOwners))
            } catch (e: IOException) {
                _carOwnersList.postValue(listOf())
                _showSnackbarEvent.postValue(true)
            }
        }
    }

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }



    @Suppress("UNCHECKED_CAST")
    class CarOwnerViewModelFactory(private val application: Application, private val filter: FilterItem) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            (CarOwnersViewModel(application, filter) as T)
    }


}