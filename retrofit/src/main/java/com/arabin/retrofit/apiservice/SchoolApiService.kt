package com.arabin.retrofit.apiservice

import com.arabin.retrofit.ApiEndPoints
import com.arabin.roomdb.entity.SchoolDetails
import com.arabin.roomdb.entity.SchoolResult
import retrofit2.http.GET

/**
 * @author Arabin
 * @since 12/16/2022
 * Api service place all api call here
 * @see [ApiEndPoints] holds all endpoints
 * */
interface SchoolApiService: ApiEndPoints {

    /**Get all schools*/
    @GET(ApiEndPoints.GET_SCHOOL_NAMES)
    suspend fun getSchoolDetails() : List<SchoolDetails>

    /**Get all results*/
    @GET(ApiEndPoints.GET_SCHOOL_RESULTS)
    suspend fun getSchoolResults(): List<SchoolResult>

}