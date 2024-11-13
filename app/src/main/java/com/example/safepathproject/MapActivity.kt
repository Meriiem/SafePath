package com.example.safepath

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location // Ensure correct import
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var fabLocationShare: FloatingActionButton
    private lateinit var btnSafeZones: Button
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fabLocationShare = findViewById(R.id.fab_location_share)
        btnSafeZones = findViewById(R.id.btn_safe_zones)

        // Initialize Google Maps
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fabLocationShare.setOnClickListener {
            shareLocationWithContacts()
        }

        btnSafeZones.setOnClickListener {
            // Show Safe Zones logic
        }
    }

    // onMapReady implementation without nullable googleMap
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }

        mMap.isMyLocationEnabled = true

        // Get current location
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val userLocation = LatLng(it.latitude, it.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                mMap.addMarker(MarkerOptions().position(userLocation).title("You are here"))
                // You can add route guidance here
            }
        }
    }

    private fun shareLocationWithContacts() {
        val currentUser = auth.currentUser
        val location = mMap.cameraPosition.target // LatLng object
        val geoPoint = GeoPoint(location.latitude, location.longitude)

        currentUser?.let {
            firestore.collection("users")
                .document(it.uid)
                .update("currentLocation", geoPoint)
            // Add logic to share location with trusted contacts here
        }
    }
}
