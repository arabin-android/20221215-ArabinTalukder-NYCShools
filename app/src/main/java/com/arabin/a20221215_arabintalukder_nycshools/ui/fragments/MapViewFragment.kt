package com.arabin.a20221215_arabintalukder_nycshools.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arabin.a20221215_arabintalukder_nycshools.R
import com.arabin.a20221215_arabintalukder_nycshools.databinding.FragmentMapViewBinding
import com.arabin.a20221215_arabintalukder_nycshools.ui.fragments.base.BaseFragment
import com.arabin.a20221215_arabintalukder_nycshools.ui.helper.MapMarkerView
import com.arabin.retrofit.restapihelper.RestAPIStatus
import com.arabin.roomdb.entity.SchoolDetails
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

/**
 * @author Arabin
 * @since 12/16/2022
 * Map fragment to show schools on map
 * accepts clicks to show detail result
 * */
class MapViewFragment : BaseFragment(), OnMapReadyCallback, OnMarkerClickListener {

    private lateinit var mMapViewBinding: FragmentMapViewBinding
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mMapViewBinding = FragmentMapViewBinding.inflate(inflater, container, false)
        return mMapViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchView(mMapViewBinding.search.searchBar)
        mMapViewBinding.search.mapView.setOnClickListener {
            launchFragment(R.id.details_fragment)
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**call api once map ready Observe value once the map is ready to draw icons*/
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        mainViewModel.loadSchoolDetails()
        try {
            val mapUiSettings = googleMap.uiSettings
            mapUiSettings.isScrollGesturesEnabled = true
            mapUiSettings.isZoomControlsEnabled = true
            observeValue()
        } catch (e: Exception) {
            e.message?.let { Log.d("MapFragment", it) }
        }
    }

    /** observes items and call [drawSchoolMarker]*/
    private fun observeValue() {
        mainViewModel.localSchoolData.observe(viewLifecycleOwner) { apiState ->
            when (apiState.status) {
                RestAPIStatus.SUCCESS -> {
                    mMapViewBinding.progressBar1.visibility = View.GONE
                    if (!apiState.data.isNullOrEmpty())
                        drawSchoolMarker(apiState.data)
                    else
                        Snackbar.make(requireView(), "No results found",
                            Toast.LENGTH_SHORT).show()
                }
                RestAPIStatus.LOADING -> {
                    mMapViewBinding.progressBar1.visibility = View.VISIBLE
                }
                RestAPIStatus.ERROR -> {
                    mMapViewBinding.progressBar1.visibility = View.GONE
                }
                else -> {}
            }
        }
    }

    /**
     * Draw each marker on the map
     * set [SchoolDetails] item
     * as a tag to each marker
     * so we can use it later for
     * on click
     * */
    private fun drawSchoolMarker(data: List<SchoolDetails>?) {
        googleMap.clear()
        /** iterate over data and create each marker*/
        data?.forEach {
            val marker = MapMarkerView(requireContext())
            /**get bitmap marker*/
            val markerBitmap = it.school_name?.let { it1 -> marker.getBitmap(it1) }

            val locationMarker =
                it.longitude?.toDouble()?.let { it1 -> it.latitude?.toDouble()
                    ?.let { it2 -> LatLng(it2, it1) } }
            /** create a marker option to it on map*/
            val markerOptions = locationMarker?.let { it1 ->
                MarkerOptions().position(it1)
                    .icon(markerBitmap?.let { it2 -> BitmapDescriptorFactory.fromBitmap(it2) })
            }

            markerOptions?.let { it1 ->
                val m = googleMap.addMarker(it1)
                m?.tag = it
            }
        }

        /** once drawing done update camera to the 1st one and animate towards it*/
        val latLng =  data?.get(0)?.run {
            val lat = latitude?.toDouble()
            val long = longitude?.toDouble()
            return@run lat?.let { long?.let { it1 -> LatLng(it, it1) } }
        }
        val cameraUpdate = latLng?.let { CameraUpdateFactory.newLatLngZoom(it, 8F) }
        if (cameraUpdate != null) {
            googleMap.animateCamera(cameraUpdate)
        }
        googleMap.setOnMarkerClickListener(this)
    }

    /**Invoke [onItemClicked] from base to show bottom-sheet for details*/
    override fun onMarkerClick(marker: Marker): Boolean {
        val id = marker.tag as SchoolDetails
        super.onItemClicked(id)
        return true
    }
}