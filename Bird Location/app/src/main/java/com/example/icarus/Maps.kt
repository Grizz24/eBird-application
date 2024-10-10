package com.example.icarus

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.location

class Maps : AppCompatActivity() {

    lateinit var permissionsManager: PermissionsManager
    //lateinit var mapView: com.mapbox.maps.MapView
    //private val birdViewModel: BirdViewModel by viewModels()

    var permissionsListener: PermissionsListener = object : PermissionsListener {
        override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        }

        override fun onPermissionResult(granted: Boolean) {
            if (granted) {
            } else {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MapboxOptions.accessToken = "pk.eyJ1IjoiZ3JpenoyNCIsImEiOiJjbTBjMmQ5bTkwZzNuMmlzYWpram5wdDJvIn0.wLdW6FPGcSndw8m3E2MnXw"
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_maps)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Permission sensitive logic called here, such as activating the Maps SDK's LocationComponent to show the device's location
        } else {
            permissionsManager = PermissionsManager(permissionsListener)
            permissionsManager.requestLocationPermissions(this)
        }

        val buttonFetch = findViewById<Button>(R.id.button)

        //button to get the birds
        buttonFetch.setOnClickListener {

            val birdViewModel: BirdViewModel by viewModels()
            val mapView = findViewById<MapView>(R.id.mapView)
            mapView.getMapboxMap().loadStyleUri("mapbox://styles/mapbox/streets-v12")
            val apiKey = "45gu3k4kq7kk"
            val editTextBirdName = findViewById<EditText>(R.id.editTextBirdName)
            val radius = findViewById<EditText>(R.id.radius)
            val birdName = editTextBirdName.text.toString().trim()
            if (birdName.isNotEmpty()) {

                //Get the user coordinates
                mapView.location.addOnIndicatorPositionChangedListener { location ->
                    val userLatitude = location.latitude()
                    val userLongitude = location.longitude()
                    birdViewModel.fetchBirdData(apiKey, userLatitude, userLongitude, birdName, radius.text.toString().toInt())
                    Toast.makeText(this, "Location ===$userLatitude,=== $userLongitude", Toast.LENGTH_SHORT).show()
                }
                //==========================================================================================

                birdViewModel.birdLocation.observe(this, Observer { locations ->

                    //map code here
                    val annotationConfig = AnnotationConfig(mapView.toString())
                    val annotationApi = mapView.annotations
                    val circleAnnotationManager = annotationApi.createCircleAnnotationManager(annotationConfig)
                    circleAnnotationManager.deleteAll()
                    val circleAnnotationOptions: CircleAnnotationOptions = CircleAnnotationOptions()
                        // Define a geographic coordinate.
                        .withPoint(Point.fromLngLat(locations[1].lng, locations[2].lat))
                        // Style the circle that will be added to the map.
                        .withCircleRadius(radius.text.toString().toDouble())
                        .withCircleColor("#FF6600")
                        .withCircleOpacity(0.90)
                        .withCircleStrokeWidth(2.0)
                        .withCircleStrokeColor("#ffffff")

                    circleAnnotationManager.create(circleAnnotationOptions)
                    //==============================================================================
                })
            } else {
                Toast.makeText(this, "Please enter a bird name", Toast.LENGTH_SHORT).show()
            }

            birdViewModel.errorMessage.observe(this, Observer { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            })

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}