package com.arabin.roomdb.localdata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.arabin.roomdb.dao.ResultInfoDao
import com.arabin.roomdb.dao.SchoolInfoDao
import com.arabin.roomdb.databse.SchoolDB
import com.arabin.roomdb.entity.SchoolDetails
import com.arabin.roomdb.entity.SchoolResult
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * @author Arabin
 * @since 12/16/2022
 * A test class
 * all db operation
 * tested here
 * */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RoomDbOperationTests {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainDb: SchoolDB
    private lateinit var schoolInfoDao: SchoolInfoDao
    private lateinit var resultInfoDao: ResultInfoDao

    /**Setup db and insert dummy data for testing*/
    @Before
    fun setUpDb() {
        mainDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SchoolDB::class.java
        ).allowMainThreadQueries().build()
        schoolInfoDao = mainDb.schoolInfoDao()
        resultInfoDao = mainDb.resultInfoDao()
        val mockData = SchoolDetails(
            1,
            "dummyDbn",
            "dummySchoolName",
            "dummyPhoneNumber",
            "dummyEmail",
            "dummyWebsite",
            "dummyLatitude",
            "dummyLongitude",
            "dummyAddress",
            "dummyCity",
            "dummyZip",
            "dummyStateCode"
        )
        val data = mutableListOf<SchoolDetails>()
        data.add(mockData)
        runTest {
            schoolInfoDao.insertAll(data)
        }
    }

    @After
    fun closeDb() {
        mainDb.close()
    }

    /**Test query to get all items from table*/
    @Test
    fun getAllSchoolInfo() = runTest {
        val schoolInfo = schoolInfoDao.getAllSchools()
        assertThat(schoolInfo).isNotEmpty()
    }

    /**Test insertion of result info*/
    @Test
    fun insertResultInfo() = runTest {
        val data = SchoolResult(
            1, "dummyDbn", "dummyTestTakers",
            "dummyReading", "dummyMath",
            "dummyWriting", "dummySchoolName"
        )
        val dataList = mutableListOf<SchoolResult>()
        dataList.add(data)
        resultInfoDao.insertAll(dataList)
        val test = resultInfoDao.getAllResult()
        assertThat(test).contains(data)
    }

    /**Test search query for result*/
    @Test
    fun findResultInfo() = runTest {
        val data = SchoolResult(
            1, "dummyDbn", "dummyTestTakers",
            "dummyReading", "dummyMath",
            "dummyWriting", "dummySchoolName"
        )
        val dataList = mutableListOf<SchoolResult>()
        dataList.add(data)
        resultInfoDao.insertAll(dataList)
        val items = resultInfoDao.findResult("dummyDbn")
        assertThat(items[0].dbn).isEqualTo("dummyDbn")
    }

    /**Test search query for school*/
    @Test
    fun findSchoolInfo() = runTest {
        val items = schoolInfoDao.findSchoolName("dummySchoolName")
        assertThat(items[0].school_name).isEqualTo("dummySchoolName")
    }



}