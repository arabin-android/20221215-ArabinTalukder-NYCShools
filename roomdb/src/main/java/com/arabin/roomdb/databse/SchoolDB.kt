package com.arabin.roomdb.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arabin.roomdb.dao.ResultInfoDao
import com.arabin.roomdb.dao.SchoolInfoDao
import com.arabin.roomdb.entity.SchoolDetails
import com.arabin.roomdb.entity.SchoolResult

/**
 * @author Arabin
 * @since 12/16/2022
 * RoomDb to store data
 * only 2 table required for this project
 * */
@Database(entities = [SchoolDetails::class, SchoolResult::class], version = 1, exportSchema = false)
abstract class SchoolDB: RoomDatabase() {
    abstract fun resultInfoDao(): ResultInfoDao
    abstract fun schoolInfoDao(): SchoolInfoDao

}