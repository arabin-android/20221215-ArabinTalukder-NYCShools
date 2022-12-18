package com.arabin.retrofit.api.schoolapi

import com.arabin.retrofit.apiservice.SchoolApiService
import com.arabin.roomdb.entity.SchoolDetails
import com.arabin.roomdb.entity.SchoolResult
import javax.inject.Inject

/**
 * @author Arabin
 * @since 12/16/2022
 * Implements [SchoolApiHelperInterface]
 * Dependency injected constructor injection
 * calls api service to get result
 * @see [SchoolApiService]
 * */
class SchoolApiHelperImpl @Inject constructor(private val apiSvc: SchoolApiService):
    SchoolApiHelperInterface {

    /**A suspend function to get school details response must run under coroutine*/
    override suspend fun getSchoolDetails(): List<SchoolDetails> = apiSvc.getSchoolDetails()

    /**A suspend function to get result response must run under coroutine*/
    override suspend fun getSchoolResults(): List<SchoolResult> = apiSvc.getSchoolResults()


}