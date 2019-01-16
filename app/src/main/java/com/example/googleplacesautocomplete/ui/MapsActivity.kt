package com.example.googleplacesautocomplete.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.example.googleplacesautocomplete.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Marker(val title: String, val lat: Double, val lng: Double) : Parcelable

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.apply {
            val marker: Marker =
                intent.getParcelableExtra(INTENT_POST) ?: throw IllegalStateException("field $INTENT_POST missing")
            val position = LatLng(marker.lat, marker.lng)
            addMarker(MarkerOptions().position(position).title(marker.title))
            animateCamera(CameraUpdateFactory.newLatLngZoom(position, 10f))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_slide_down)
    }

    companion object {
        const val INTENT_POST = "intent_marker"

        fun newIntent(context: Context, marker: Marker) = Intent(context, MapsActivity::class.java).apply {
            putExtra(INTENT_POST, marker)
        }
    }
}
