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
@Entity(tableName = "schoolDetails")
data class SchoolDetails(
    @PrimaryKey(autoGenerate = true)val id: Int? = null,
    val dbn: String?,
    val school_name: String?,
    val phone_number: String?,
    val school_email: String?,
    val website: String?,
    val latitude: String?,
    val longitude: String?,
    val primary_address_line_1: String?,
    val city: String?,
    val zip: String?,
    val state_code: String?,
): Parcelable
