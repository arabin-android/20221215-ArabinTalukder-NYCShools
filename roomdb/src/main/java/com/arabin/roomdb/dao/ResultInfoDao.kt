package com.arabin.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arabin.roomdb.entity.SchoolResult


/**
 * @author Arabin
 * @since 12/16/2022
 * */

/**
 * ResultInfoDao responsible to perform
 * operation on [resultDetails] table
 * please see the @see SchoolResult Entity
 * for details
 * */
@Dao
interface ResultInfoDao {

    /**Inserts all items to [resultDetails] table*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertAll(student: List<SchoolResult>): List<Long>

    /**Returns all rows from [resultDetails] table*/
    @Query("select * From resultDetails ORDER BY id ASC")
    fun  getAllResult() : List<SchoolResult>

    /**Returns a list of matched keyword*/
    @Query("SELECT * FROM resultDetails WHERE dbn LIKE :inputString")
    fun findResult(inputString: String): List<SchoolResult>

}