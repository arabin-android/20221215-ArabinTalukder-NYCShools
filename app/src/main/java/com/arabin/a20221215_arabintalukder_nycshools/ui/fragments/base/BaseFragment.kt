package com.arabin.a20221215_arabintalukder_nycshools.ui.fragments.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.arabin.a20221215_arabintalukder_nycshools.R
import com.arabin.a20221215_arabintalukder_nycshools.ui.fragments.DetailsScoreBottomSheet
import com.arabin.a20221215_arabintalukder_nycshools.ui.helper.ViewHelper
import com.arabin.a20221215_arabintalukder_nycshools.ui.viewmodel.SchoolDataViewModel
import com.arabin.retrofit.restapihelper.RestAPIStatus
import com.arabin.roomdb.entity.SchoolDetails
import com.arabin.roomdb.entity.SchoolResult
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author Arabin
 * @since 12/16/2022
 * */

/**
 * Base Fragment should not have any view
 * all child fragments derived from here
 * just to control central functionalities
 * such as [onItemClicked] for Map and List
 * fragment, [initSearchView] called in both fragments
 * [launchFragment], [onQueryTextChange], [onQueryTextSubmit]
 * all of these are used in both derived class
 * so it should go to a parent to avoid boiler plate code
 * */
@AndroidEntryPoint
open class BaseFragment : Fragment(), SearchView.OnQueryTextListener {

    /**Here used the viewModel as we have only
     * 2 fragments and both has almost same functionality
     * except views are different, */
    protected val mainViewModel: SchoolDataViewModel by viewModels()
    private lateinit var mSchool: SchoolDetails

    /**initializes search view for both fragments*/
    protected fun initSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(this)
    }

    /**handles list item and map item */
    fun onItemClicked(school: SchoolDetails) {
        mSchool = school
        school.dbn?.let { mainViewModel.findResult(it) }
    }

    /**
     * observe the result based on search and update ui
     * */
    override fun onResume() {
        super.onResume()
        mainViewModel.marksDetails.observe(viewLifecycleOwner) { it ->
            it.getContentIfNotHandled()?.let {apiState->
                when (apiState.status) {
                    RestAPIStatus.SUCCESS -> {
                        if (apiState.data?.isNotEmpty() == true && ::mSchool.isInitialized)
                            showResultInfo(apiState.data?.get(0), mSchool)
                        else
                            Snackbar.make(requireView(), getString(R.string.no_results_found),
                                Toast.LENGTH_SHORT).show()
                    }
                    RestAPIStatus.LOADING -> {}
                    RestAPIStatus.ERROR -> {
                        Snackbar.make(
                            requireView(),
                            getString(R.string.error_try_again),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {}
                }
            }
        }
    }

    /**
     * trigger the bottom sheet fragment to show result details
     * */
    private fun showResultInfo(schoolResult: SchoolResult?, school: SchoolDetails) {
        val detailsBottomSheet = DetailsScoreBottomSheet(schoolResult, school)
        detailsBottomSheet.show(childFragmentManager, DetailsScoreBottomSheet::class.java.name)
    }

    /**
     * performs search when we press enter
     * */
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            findSearchedSchool(query)
        }
        return true
    }

    /**
     * listens to text change and performs search accordingly
     *  */
    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            findSearchedSchool(query)
        }
        return true
    }

    // We have just created this function for searching our database
    private fun findSearchedSchool(query: String) {
        // %" "% because our custom sql query will require that
        mainViewModel.findSearchedSchools("%$query%")
    }

    /**
     * navigates between fragments
     * */
    protected fun launchFragment(fragmentId: Int) {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(fragmentId, null, ViewHelper.getNavigationOptions())
    }


}