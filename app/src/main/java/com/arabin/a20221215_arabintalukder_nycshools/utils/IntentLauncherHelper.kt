package com.arabin.a20221215_arabintalukder_nycshools.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.arabin.roomdb.entity.SchoolDetails

/**
 * @author Arabin
 * @since 12/16/2022
 * A helper class to launch email, browser or map
 * */
class IntentLauncherHelper {
    companion object{

        /**Luanches email app*/
        fun launchEmail(email : String, activity: Activity){
            val intent = IntentHelper.getEmailIntent(
                "Please write message here...",
                email, "Hello"
            )
            activity.startActivity(intent)
        }

        /** Launches browser*/
        fun launchBrowser(aUrl: String, activity: Activity){
            val url =
                if (aUrl.startsWith("http://") || aUrl.startsWith("https://")) aUrl
                else "http://$aUrl"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity.startActivity(browserIntent)
        }

        /** Launches map*/
        fun launchMap(lat: Double, long: Double, activity: Activity){
            activity.startActivity(IntentHelper.getMapIntent(lat, long))
        }

    }
}