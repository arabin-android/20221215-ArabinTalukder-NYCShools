package com.arabin.a20221215_arabintalukder_nycshools.ui.fragments

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.arabin.a20221215_arabintalukder_nycshools.R
import com.arabin.a20221215_arabintalukder_nycshools.databinding.SchoolMarksDetailsBinding
import com.arabin.a20221215_arabintalukder_nycshools.ui.helper.ViewHelper
import com.arabin.a20221215_arabintalukder_nycshools.utils.PermissionDelegate
import com.arabin.a20221215_arabintalukder_nycshools.utils.IntentHelper
import com.arabin.a20221215_arabintalukder_nycshools.utils.IntentLauncherHelper
import com.arabin.roomdb.entity.SchoolDetails
import com.arabin.roomdb.entity.SchoolResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar


/**
 * @author Arabin
 * @since 12/16/2022
 * A bottom sheet fragment
 * to show result information of each clicked school
 * */
class DetailsScoreBottomSheet(
    private val schoolResult: SchoolResult?,
    private val school: SchoolDetails?
) :
    BottomSheetDialogFragment(), PermissionDelegate.PermissionDelegateInterface, View.OnClickListener {

    private lateinit var schoolMarksBinding: SchoolMarksDetailsBinding

    /**
     * launcher to check permission acceptance or denial
     * we take decisiono on enums [PermissionDelegate.PERMISSIONS]
     * */
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.forEach { entry ->
                if (entry.key == PermissionDelegate.PERMISSIONS.REQUEST_PHONE_CALL.aPermissionType && entry.value)
                    onPermissionAccepted(PermissionDelegate.PERMISSIONS.REQUEST_PHONE_CALL.aPermissionType)
                else
                    onPermissionDenied(PermissionDelegate.PERMISSIONS.REQUEST_PHONE_CALL.aPermissionType)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        schoolMarksBinding = SchoolMarksDetailsBinding.inflate(inflater, container, false)
        return schoolMarksBinding.root
    }

    override fun onResume() {
        super.onResume()
        updateUi()
    }

    /**
     * Updates the UI as per data
     * */
    @SuppressLint("SetTextI18n")
    private fun updateUi() {
        if (school != null && schoolResult != null) {
            /**Here is the reusability of [ViewHelper][setSchoolInfo]*/
            ViewHelper.setSchoolInfo(schoolMarksBinding.schoolCell, school)
            schoolMarksBinding.testTakers.text =
                getString(R.string.participants).uppercase() + schoolResult.num_of_sat_test_takers
            schoolMarksBinding.satReadingAvgScore.text =
                getString(R.string.reading_score).uppercase() + schoolResult.sat_critical_reading_avg_score
            schoolMarksBinding.satMathAvgScore.text =
                getString(R.string.math_score).uppercase() + schoolResult.sat_math_avg_score
            schoolMarksBinding.satWritingScore.text =
                getString(R.string.writing_score).uppercase() + schoolResult.sat_writing_avg_score
            schoolMarksBinding.schoolCell.phone.setOnClickListener(this)
            schoolMarksBinding.schoolCell.email.setOnClickListener(this)
            schoolMarksBinding.schoolCell.website.setOnClickListener(this)
            schoolMarksBinding.close.setOnClickListener(this)
            schoolMarksBinding.schoolCell.schoolLocation.setOnClickListener(this)
        }
    }

    /**
     * Take decision based on permission type
     * we do phone call here as we asking for it
     * */
    override fun onPermissionAccepted(aPermissionCode: String) {
        if (aPermissionCode == PermissionDelegate.PERMISSIONS.REQUEST_PHONE_CALL.aPermissionType)
            callPhone()
    }

    /**Starts activity to place phone call*/
    private fun callPhone() {
        val phIntent = school?.phone_number?.let { IntentHelper.getPhoneIntent(it) }
        try {
            requireActivity().startActivity(Intent.createChooser(phIntent, null))
        } catch (ex: ActivityNotFoundException) {
            Snackbar.make(
                requireView(), "There are no phone clients installed.",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    /**If permission denied show a message to user*/
    override fun onPermissionDenied(aPermissionCode: String) {
        Snackbar.make(requireView(), "Permission denied", Snackbar.LENGTH_SHORT).show()
    }

    /**
     * Handles all click events here
     * on different items on the list
     * */
    override fun onClick(v: View?) {
        when (v?.id) {
            schoolMarksBinding.schoolCell.phone.id -> {
                /** Asks for phone call permission result
                 * will be passed in this fragment by [PermissionDelegate.PermissionDelegateInterface]
                 * and [resultLauncher]*/
                PermissionDelegate(
                    this,
                    launcher = resultLauncher
                ).checkPhonePermission(
                    PermissionDelegate.PERMISSIONS.REQUEST_PHONE_CALL.aPermissionType,
                    requireContext()
                )
            }
            /** Opens gmail to send email*/
            schoolMarksBinding.schoolCell.email.id -> {
                school?.school_email?.let { IntentLauncherHelper.launchEmail(it, requireActivity()) }
            }
            /** Redirects to browser to browse website*/
            schoolMarksBinding.schoolCell.website.id -> {
                school?.website?.let { IntentLauncherHelper.launchBrowser(it, requireActivity()) }
            }
            /**
             * Launches map apps to show location
             * */
            schoolMarksBinding.schoolCell.schoolLocation.id->{
                school?.longitude?.toDouble()?.let {
                    school?.latitude?.toDouble()?.let { it1 ->
                        IntentLauncherHelper.launchMap(
                            it1,
                            it, requireActivity())
                    }
                }
            }
            schoolMarksBinding.close.id->dismiss()
        }
    }


}