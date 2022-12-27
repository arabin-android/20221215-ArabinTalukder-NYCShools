package com.arabin.a20221215_arabintalukder_nycshools.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arabin.a20221215_arabintalukder_nycshools.respository.MainRepo
import com.arabin.retrofit.restapihelper.RestAPIState
import com.arabin.roomdb.entity.SchoolDetails
import com.arabin.roomdb.entity.SchoolResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.logging.Handler
import javax.inject.Inject


/**
 * @author Arabin
 * @since 12/16/2022
 * Main view model to communicate with Main Repo
 * calls db queries and API's
 * */
@HiltViewModel
class SchoolDataViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {

    /**live school list data to be observed in UI*/
    private val _localSchoolData = MutableLiveData<Event<RestAPIState<List<SchoolDetails>>>>()
    var localSchoolData: LiveData<Event<RestAPIState<List<SchoolDetails>>>> =
        _localSchoolData

    /**results live data based on query*/
    private val _marksDetails = MutableLiveData<Event<RestAPIState<List<SchoolResult>>>>()
    val marksDetails : LiveData<Event<RestAPIState<List<SchoolResult>>>> =
        _marksDetails


    /** 1st check if data already populated in roomDb or not
     * if not fetch api and insert it to roomdb
     * runs in background thread both operations api and room insertion*/
    fun loadSchoolDetails() {
        viewModelScope.launch (Dispatchers.IO){
            _localSchoolData.postValue(Event(RestAPIState.loading(data = null, "Loading")))
            try {
                var data = mainRepo.fetchAllSchools()
              if (data.isNotEmpty()){
                  _localSchoolData.postValue(Event(RestAPIState.success(data)))
              }else{
                  val id = mainRepo.insertSchoolInfo(mainRepo.getSchoolDetails())
                  Log.d("vehicle_lsit_item","Inserted ID ${id.size}")
                  mainRepo.insertResultInfo(mainRepo.getSchoolResults())
                  data = mainRepo.fetchAllSchools()
                  _localSchoolData.postValue(Event(RestAPIState.success(data)))
              }
            } catch (e: Exception) {
                e.printStackTrace()
                _localSchoolData.postValue(Event(RestAPIState.error(data = null, "Error")))
            }
        }
    }

    /** Find searched school based on school name or address runs in background*/
    fun findSearchedSchools(string: String){
        viewModelScope.launch(Dispatchers.IO){
            _localSchoolData.postValue(Event(RestAPIState.loading(data = null, "Loading")))
            try {
                val data = if(string.isBlank()){
                    mainRepo.fetchAllSchools()
                }else{
                    mainRepo.findSchools(string)
                }
                _localSchoolData.postValue(Event(RestAPIState.success(data = data)))
            }catch (e: Exception){
                _localSchoolData.postValue(Event(RestAPIState.error(data = null, "Error")))
            }
        }
    }

    /** Find the result based on dbn code runs in background*/
    fun findResult(inputString: String){
        viewModelScope.launch(Dispatchers.IO){
            _marksDetails.postValue(Event(RestAPIState.loading(data = null, "Loading")))
            try {
                if(inputString.isBlank()){
                    _marksDetails.postValue(Event(RestAPIState.error(data = null, "Error")))
                }else{
                    _marksDetails.postValue(Event(RestAPIState.success(data = mainRepo.findResult(inputString))))
                }
            }catch (e: Exception){
                _marksDetails.postValue(Event(RestAPIState.error(data = null, "Error")))
            }
        }
    }

}