package com.arabin.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arabin.roomdb.entity.SchoolDetails

/**
 * @author Arabin
 * @since 12/16/2022
 * */

/**
 * SchoolInfoDao responsible to perform
 * operation on [schoolDetails] table
 * please see the @see SchoolDetails Entity
 * for details
 * */
@Dao
interface SchoolInfoDao {

    /**Insert items to [schoolDetails] table
     * should run in background thread as it might block UI thread*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SchoolDetails>) :List<Long>

    /*** Returns all rows from [schoolDetails] table*/
    @Query("select * From schoolDetails ORDER BY id ASC")
    fun getAllSchools() : List<SchoolDetails>

    /**Returns searched school
     * @param schoolName or address*/
    @Query("SELECT * FROM schoolDetails WHERE school_name LIKE :inputString OR primary_address_line_1 LIKE :inputString")
    fun findSchoolName(inputString: String): List<SchoolDetails>

}