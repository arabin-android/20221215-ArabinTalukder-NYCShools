package com.arabin.a20221215_arabintalukder_nycshools.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat


/**
 * @author Arabin
 * @since 12/16/2022
 * Permission delegate is modularized
 * to handle all permissoin here
 * [aPermissionDlgInterface] a interface to decide
 * acceptance or denial
 * [launcher] to launch permission window
 * This class is intended to handle all permissions
 * based on the enum it take perform different permission check
 * [isPermissionGranted] and [launchPerMission]
 * these two functions are generic for all
 * [checkPhonePermission] is an example of checking permission here
 * we can add others here too, to avoid boiler plate code
 * */
class PermissionDelegate(
    private val aPermissionDlgInterface: PermissionDelegateInterface,
    private val launcher: ActivityResultLauncher<Array<String>>
) {

    /** Checking phone permission*/
    fun checkPhonePermission(aPermissionType: String, context: Context) {
        if (isPermissionGranted(context, aPermissionType)){
            aPermissionDlgInterface.onPermissionAccepted(aPermissionType)
        }else{
           launchPerMission(aPermissionType)
        }
    }

    /** Generic function to check if permission is granted or not*/
    private fun isPermissionGranted(context: Context, aPermissionType: String): Boolean{
        return (ContextCompat.checkSelfPermission(
            context, aPermissionType
        ) == PackageManager.PERMISSION_GRANTED)
    }

    /** launcher launches permission widow to the parent UI*/
    private fun launchPerMission(aPermissionType: String){
        launcher.launch(arrayOf(aPermissionType))
    }

    /** All permissions can be defined here as enums*/
    enum class PERMISSIONS(val aPermissionType: String) {
        REQUEST_PHONE_CALL(aPermissionType = Manifest.permission.CALL_PHONE),
    }

    /** A inteface to pass permission result*/
    interface PermissionDelegateInterface {
        fun onPermissionAccepted(aPermissionCode: String)
        fun onPermissionDenied(aPermissionCode: String)
    }

}