package com.example.googleplacesautocomplete.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.android.gms.location.places.Place

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    val name: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    companion object {
        fun fromPlace(place: Place) = HistoryEntity(
            0,
            place.name.toString(),
            place.address.toString(),
            place.latLng.latitude,
            place.latLng.longitude
        )
    }
}
