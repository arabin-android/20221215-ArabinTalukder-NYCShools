package com.arabin.retrofit.api.schoolapi

import com.arabin.roomdb.entity.SchoolDetails
import com.arabin.roomdb.entity.SchoolResult


/**
 * @author Arabin
 * @since 12/16/2022
 * An interface that holds
 * all api call abstraction
 * @see [SchoolApiHelperImpl]
 * */
interface SchoolApiHelperInterface {
    suspend fun getSchoolDetails(): List<SchoolDetails>
    suspend fun getSchoolResults(): List<SchoolResult>
}