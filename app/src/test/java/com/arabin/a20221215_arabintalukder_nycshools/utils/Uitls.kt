package com.arabin.a20221215_arabintalukder_nycshools.utils

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * @author Arabin
 * @since 12/16/2022
 * Simple unit tests to test network and intent function
 * */
@RunWith(AndroidJUnit4::class)
@Config(manifest= Config.NONE)
class Uitls {

    @Test
    fun isNetWorkConnected(){
        val result = NetworkUtil.hasInternetConnection(ApplicationProvider.getApplicationContext())
        assertThat(result).isNotNull()
    }

    @Test
    fun isIntent(){
        val result = IntentHelper.getMapIntent(0.0,0.0)
        assertThat(result).isNotNull()
    }

}