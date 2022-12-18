package com.arabin.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.arabin.roomdb.dao.ResultInfoDao
import com.arabin.roomdb.dao.SchoolInfoDao
import com.arabin.roomdb.databse.SchoolDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Arabin
 * @since12/16/2022
 * Hilt Module for dependency Injection
 * Provide database and DAO's to perform operation
 * a Singleton as we should have only one instance all time
 * MVVM architecture this should communicate with repo
 * */
@Module
@InstallIn(SingletonComponent::class)
class DataBaseBuilder {

    /**Provides database*/
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): SchoolDB =
        Room.databaseBuilder(context, SchoolDB::class.java, "schoolDb.db").build()

    /**Provides SchoolInfoDao*/
    @Provides
    @Singleton
    fun providesSchoolInfoDao(database: SchoolDB): SchoolInfoDao {
        return database.schoolInfoDao()
    }

    /**Provides ResultInfoDao*/
    @Provides
    @Singleton
    fun providesResultInfoDao(database: SchoolDB): ResultInfoDao {
        return database.resultInfoDao()
    }

}