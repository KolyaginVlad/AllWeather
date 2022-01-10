package ru.kolyagin.allweather.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import ru.kolyagin.allweather.R

class LocationHelper(
    private val activity: ComponentActivity,
    private val callback: (location: Location) -> Unit
) {
    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)

    fun checkPermissionAndGetLocation() {
        Log.d("location1", "checkPermissionAndGetLocation")
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("location1", "requestPermissions")
            val requestMultiplePermissions = activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
                var isFailed = false
                permissions.values.forEach {
                    if (!it){
                        isFailed = true
                    }
                }
                if (isFailed){
                    Toast.makeText(activity, activity.getString(R.string.turn_on_location), Toast.LENGTH_LONG).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    activity.startActivity(intent)
                } else{
                    checkPermissionAndGetLocation()
                }
            }
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            getLastLocation()
        }
    }

    private fun isLocationEnabled(): Boolean {
        Log.d("location1", "isLocationEnabled")
        val locationManager: LocationManager =
            activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        Log.d("location1", "getLastLocation")
        if (isLocationEnabled()) {
            Log.d("location1", "LocationEnabled")
            fusedLocationClient.lastLocation.addOnCompleteListener(activity) { task ->
                val location: Location? = task.result
                if (location == null) {
                    requestNewLocationData()
                } else {
                    callback(location)
                }
            }
        } else {
            Log.d("location1", "LocationNOTEnabled")
            Toast.makeText(
                activity,
                activity.getString(R.string.turn_on_location),
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            activity.startActivity(intent)
        }

    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        Log.d("location1", "requestNewLocationData")
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        Looper.myLooper()?.let {
            fusedLocationClient.requestLocationUpdates(
                mLocationRequest, object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        super.onLocationResult(result)
                        callback(result.lastLocation)
                    }
                },
                it
            )
        }
    }
}