package com.arabin.a20221215_arabintalukder_nycshools.respository

import com.arabin.retrofit.api.schoolapi.SchoolApiHelperInterface
import com.arabin.roomdb.dao.ResultInfoDao
import com.arabin.roomdb.dao.SchoolInfoDao
import com.arabin.roomdb.entity.SchoolDetails
import com.arabin.roomdb.entity.SchoolResult
import javax.inject.Inject


/**
 * @author Arabin
 * @since 12/16/2022
 * Main repo this should
 * communicate with viewModel
 * MVVM, hilt injected
 * check dependency graph to see the tree
 * */
class MainRepo @Inject constructor(
    private val schoolApiHelperImpl: SchoolApiHelperInterface,
    private val schoolInfoDao: SchoolInfoDao,
    private val resultInfoDao: ResultInfoDao
    ){

    /**API call's to get data from server*/
    suspend fun getSchoolDetails() = schoolApiHelperImpl.getSchoolDetails()
    suspend fun getSchoolResults() = schoolApiHelperImpl.getSchoolResults()

    /**Insertion call to save data in local db*/
    suspend fun insertSchoolInfo(items: List<SchoolDetails>): List<Long> = schoolInfoDao.insertAll(items)
    suspend fun insertResultInfo(items: List<SchoolResult>) : List<Long> = resultInfoDao.insertAll(items)

    /**Returns all items from respected tables*/
    fun fetchAllSchools() = schoolInfoDao.getAllSchools()
    fun fetchAllResults() = resultInfoDao.getAllResult()

    /**Search all queried item*/
    fun findSchools(inputString: String) = schoolInfoDao.findSchoolName(inputString)
    fun findResult(inputString: String) = resultInfoDao.findResult(inputString)
}