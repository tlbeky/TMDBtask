package com.example.themovie.Models

import com.google.gson.annotations.SerializedName

data class MovieVideos(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String,
    @SerializedName("site") val posterPath: String,
    @SerializedName("type") val backdropPath: String,
    @SerializedName("published_at") val rating: Float
) {
}