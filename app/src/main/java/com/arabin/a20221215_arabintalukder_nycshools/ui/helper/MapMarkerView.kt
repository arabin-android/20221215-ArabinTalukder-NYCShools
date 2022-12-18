package com.arabin.a20221215_arabintalukder_nycshools.ui.helper

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.arabin.a20221215_arabintalukder_nycshools.R
import com.arabin.a20221215_arabintalukder_nycshools.databinding.MapMarkerBinding


/**
 * @author Arabin
 * @since 12/16/2022
 * A customized marker view to show on map
 * takes on 1st character of the school name
 * to show on the map
 * */
class MapMarkerView(context: Context) : LinearLayout(context) {

    private lateinit var mapMarkerBinding: MapMarkerBinding

    init {
        initView()
    }

    /** Initialize view*/
    private fun initView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mapMarkerBinding = MapMarkerBinding.inflate(inflater, this, false)
    }

    /** Get the bitmap of the view*/
    fun getBitmap(name: String): Bitmap {
        mapMarkerBinding.totalJobsMarker.text = name[1].toString()
        return getBitmapFromView(mapMarkerBinding.root)
    }

    /**For future use*/
    fun getBitmapSelected(schoolCount: Int): Bitmap? {
        mapMarkerBinding.totalJobsMarker.text = schoolCount.toString()
        mapMarkerBinding.totalJobsMarker.setTextColor(ContextCompat.getColor(context, R.color.purple_200))
        mapMarkerBinding.markerMainId.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_job_marker_selected, null)
        return getBitmapFromView(mapMarkerBinding.root)
    }

    /** returns the bitmap*/
    private fun getBitmapFromView(view: View): Bitmap {
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.draw(canvas)
        return bitmap
    }

}