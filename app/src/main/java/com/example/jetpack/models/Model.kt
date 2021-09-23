package com.example.jetpack.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Country(
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val countryName: String? = null,

    @ColumnInfo(name = "region")
    @SerializedName("region")
    val countryRegion: String? = null,

    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val countryCapital: String? = null,

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    val countryCurrency: String? = null,

    @ColumnInfo(name = "language")
    @SerializedName("language")
    val countryLanguage: String? = null,

    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val imageUrl: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}