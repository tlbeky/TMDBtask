package com.example.themovie.Models

import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val videos: List<MovieVideos>,
) {
}