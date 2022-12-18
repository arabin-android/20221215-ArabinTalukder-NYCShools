package com.arabin.a20221215_arabintalukder_nycshools.utils

import android.content.Intent
import android.net.Uri
import java.util.*


/**
 * @author Arabin
 * @since 12/16/200
 * A class to create inents like CALL, EMAIL, MAP
 * */
class IntentHelper {

    companion object {
        /**returns phone intent to make call*/
        fun getPhoneIntent(strPhoneNumber: String): Intent? {
            val phIntent = Intent(Intent.ACTION_CALL)
            phIntent.data = Uri.parse("tel:$strPhoneNumber")
            return phIntent
        }

        /**returns email intent to send email*/
        fun getEmailIntent(aMesssage: String?, emailString: String, aSubject: String?): Intent? {
            val selectorIntent = Intent(Intent.ACTION_SENDTO)
            val types = arrayOf("plain/text", "application/octet-stream")
            selectorIntent.setDataAndType(Uri.parse("mailto:"), "*/*")
            selectorIntent.putExtra(Intent.EXTRA_MIME_TYPES, types)
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailString))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, aSubject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, aMesssage)
            emailIntent.selector = selectorIntent
            return emailIntent
        }

        /**returns map intent to show address on map*/
        fun getMapIntent(lat: Double, long: Double): Intent {
            val uri: String =
                String.format(Locale.ENGLISH, "geo:%f,%f?z=17&q=%f,%f", lat, long, lat, long);
            return Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        }

    }
}