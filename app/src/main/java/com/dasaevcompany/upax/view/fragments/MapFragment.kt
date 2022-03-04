package com.dasaevcompany.upax.view.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.dasaevcompany.upax.databinding.FragmentMapBinding
import com.dasaevcompany.upax.utilities.ConnectivityUtil
import com.dasaevcompany.upax.utilities.NotificationsChannelUtil
import com.dasaevcompany.upax.utilities.PermissionUtil
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback{

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var binding: FragmentMapBinding
    private lateinit var locationCallback: LocationCallback
    private var locationRequest = LocationRequest()
    private var updateLocationReady = false
    private lateinit var map: GoogleMap

    /** Result for permission **/
    private val locationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) {
        if (it) {
            PermissionUtil().pushPermissionKey(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION,
                "DENY")
            getUserLocation()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        internet()
        createMapFragment()

    }

    private fun createMapFragment() {

        /** init channel notification **/
        NotificationsChannelUtil().channelLocation(requireActivity())

        /** get location manager **/
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager
            .findFragmentById(binding.fragmentMap.id) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getUserLocation()
    }

    private fun getUserLocation() {
        if (checkPermission()){
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(requireActivity(), "No se pudo obtener la localización", Toast.LENGTH_SHORT).show()
                } else if (location != null) {
                    map.isMyLocationEnabled = true
                    animationZoom(location,18f)
                    getLocationRealTime()
                } else {
                    Toast.makeText(requireActivity(), "No se pudo obtener la localización", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermission():Boolean{
        return if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtil().requestPermission(requireActivity(),locationPermission,Manifest.permission.ACCESS_FINE_LOCATION)
        } else{
            true
        }
    }

    private fun internet():Boolean{
        return ConnectivityUtil().internet(requireActivity())
    }

    private fun animationZoom(location : Location, zoom: Float) {
        val place = LatLng(location.latitude, location.longitude)
        map.animateCamera (
            CameraUpdateFactory.newLatLngZoom(place, zoom), 500, null)
    }

    private fun getLocationRealTime() {
        try {
            updateLocationReady = true
            val timeDelay:Long = 2*60000 /** Time to update location **/
            locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = timeDelay
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult : LocationResult) {
                    for (location in locationResult.locations) {
                        val place = LatLng(location.latitude, location.longitude)
                        NotificationsChannelUtil().notifyLocationUpdate(requireActivity(),place)
                        map.addMarker(MarkerOptions().position(place).title("Ubicación personalizada"))
                        Toast.makeText(requireActivity(), "Ubicacion Actualizada", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            startLocationUpdates()
        } catch (e: SecurityException) {
            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun startLocationUpdates() {
        if (checkPermission() && updateLocationReady){
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun stopLocationUpdates() {
        if (updateLocationReady){
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }
}