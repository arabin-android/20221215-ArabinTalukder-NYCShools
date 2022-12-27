package com.arabin.a20221215_arabintalukder_nycshools.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arabin.a20221215_arabintalukder_nycshools.R
import com.arabin.a20221215_arabintalukder_nycshools.ui.adapter.SchoolListAdapter
import com.arabin.a20221215_arabintalukder_nycshools.databinding.FragmentSchoolListBinding
import com.arabin.a20221215_arabintalukder_nycshools.ui.fragments.base.BaseFragment
import com.arabin.a20221215_arabintalukder_nycshools.ui.helper.hide
import com.arabin.a20221215_arabintalukder_nycshools.ui.helper.show
import com.arabin.a20221215_arabintalukder_nycshools.utils.NetworkUtil
import com.arabin.retrofit.restapihelper.RestAPIStatus
import com.arabin.roomdb.entity.SchoolDetails
import com.google.android.material.snackbar.Snackbar

/**
 * @author Arabin
 * @since 12/16/2022
 * SchoolList fragment to show list of schools
 * with small summary
 * */
class SchoolListFragment : BaseFragment() {

    private lateinit var schoolListFragmentBinding: FragmentSchoolListBinding

    /**
     * Handle back pressed here
     * as this is the home of app once UI
     * is here finish the [MainActivity]
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        schoolListFragmentBinding = FragmentSchoolListBinding.inflate(inflater, container, false)
        return schoolListFragmentBinding.root
    }

    /** launches map fragment*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchView(schoolListFragmentBinding.search.searchBar)
        schoolListFragmentBinding.search.mapView.setOnClickListener {
            launchFragment(R.id.map_fragment)
        }
    }

    /** check internet and call api to observe the response and populate UI*/
    override fun onResume() {
        super.onResume()
        /**checks network connectivity*/
        if (!NetworkUtil.hasInternetConnection(requireContext()))
            Snackbar.make(
                requireView(),
                "No internet connection. Loading local data!!",
                Snackbar.LENGTH_SHORT
            ).show()

        /**call and observer the response*/
        mainViewModel.loadSchoolDetails()
        mainViewModel.localSchoolData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { apiState ->
                when (apiState.status) {
                    RestAPIStatus.SUCCESS -> {
                        schoolListFragmentBinding.progressBar1.hide()
                        if (!apiState.data.isNullOrEmpty()) {
                            updateUi(apiState?.data)
                        } else {
                            handleError()
                        }
                    }
                    RestAPIStatus.LOADING -> {
                        schoolListFragmentBinding.progressBar1.show()
                    }
                    RestAPIStatus.ERROR -> {
                        handleError()
                    }
                    else -> {}
                }
            }
        }
    }

    /**handle error if there is no data enable try again button*/
    private fun handleError() {
        schoolListFragmentBinding.progressBar1.hide()
        schoolListFragmentBinding.schoolList.hide()
        schoolListFragmentBinding.error.show()
        schoolListFragmentBinding.tryAgain.show()
        schoolListFragmentBinding.tryAgain.setOnClickListener {
            mainViewModel.loadSchoolDetails()
        }
    }

    /**update the ui*/
    private fun updateUi(data: List<SchoolDetails>?) {
        schoolListFragmentBinding.error.visibility = View.GONE
        schoolListFragmentBinding.tryAgain.visibility = View.GONE
        schoolListFragmentBinding.schoolList.apply {
            show()
            adapter = data?.let { SchoolListAdapter(it, ::onItemClicked) }
        }
    }
}