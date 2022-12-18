package com.arabin.a20221215_arabintalukder_nycshools.ui.helper

import androidx.navigation.NavOptions
import com.arabin.a20221215_arabintalukder_nycshools.databinding.SchoolListCellBinding
import com.arabin.roomdb.entity.SchoolDetails

/**
 * @author Arabin
 * @since 12/16/2022
 * A view helper utility class
 * to minimize boilerplate code
 * */
class ViewHelper {

    companion object {
        /**
         * A helper function to animate fragment transaction
         * */
        fun getNavigationOptions(): NavOptions? {
            return NavOptions.Builder()
                .setLaunchSingleTop(true) // Used to prevent multiple copies of the same destination
                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                .build()
        }

        /**
         * A helper function to show all school related attributes
         * used here to call in multiple places if required
         * */
        fun setSchoolInfo(schoolListCellBinding: SchoolListCellBinding, item: SchoolDetails){
            schoolListCellBinding.apply {
                schoolName.text = item.school_name
                val address =
                    item.primary_address_line_1 + "," + item.city + " " + item.state_code + " " + item.zip
                schoolLocation.text = address
                phone.text = item.phone_number
                email.text = item.school_email
                website.text = item.website
            }
        }
    }

}