package com.arabin.a20221215_arabintalukder_nycshools.ui.helper

import android.view.View

/**
 * Makes the view hidden by changing visibility to [View.GONE]
 * @author Arabin
 * @since 12/16/2022
 */
fun View?.hide() {
    if (this != null) {
        this.visibility = View.GONE
    }
}

/**
 * Makes the view visible
 * @author Arabin
 * @since 12/16/2022
 **/
fun View?.show() {
    if (this != null) {
        this.visibility = View.VISIBLE
    }
}