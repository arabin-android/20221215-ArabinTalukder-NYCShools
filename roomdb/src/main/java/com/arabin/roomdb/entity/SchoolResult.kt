package com.arabin.roomdb.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * @author Arabin
 * @since 12/16/2022
 * An Entity plus data model
 * same model used to receive
 * server response an data insertion
 * Parcelable required to pass data
 * between fragments
 * */
@Parcelize
@Entity(tableName = "resultDetails")
data class SchoolResult(
    @PrimaryKey(autoGenerate = true)val id: Int? = null,
    val dbn: String,
    val num_of_sat_test_takers: String,
    val sat_critical_reading_avg_score: String,
    val sat_math_avg_score: String,
    val sat_writing_avg_score: String,
    val school_name: String
): Parcelable