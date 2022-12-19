package com.arabin.a20221215_arabintalukder_nycshools.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.arabin.a20221215_arabintalukder_nycshools.R
import com.arabin.a20221215_arabintalukder_nycshools.databinding.FragmentSplashScreenBinding
import com.arabin.a20221215_arabintalukder_nycshools.ui.fragments.base.BaseFragment
import com.arabin.a20221215_arabintalukder_nycshools.ui.helper.ViewHelper
import com.arabin.a20221215_arabintalukder_nycshools.utils.NetworkUtil
import com.google.android.material.snackbar.Snackbar

/**
 * @author Arabin
 * @since 12/16/2022
 * A simple splash fragment to start the app
 * Checking network here if not connected
 * use local data
 * */
class WelcomeScreen : BaseFragment() {

    private lateinit var splashScreenBinding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        splashScreenBinding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return splashScreenBinding.root
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
           val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
           navController.navigate(R.id.details_fragment, null, ViewHelper.getNavigationOptions())
        },1500)
    }

}