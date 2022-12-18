package com.arabin.a20221215_arabintalukder_nycshools.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arabin.a20221215_arabintalukder_nycshools.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }

}