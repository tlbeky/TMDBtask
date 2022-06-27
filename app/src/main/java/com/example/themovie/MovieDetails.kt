package com.example.themovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class MovieDetails : AppCompatActivity() {
    private lateinit var  posterBig: ImageView
    private lateinit var  posterSmall: ImageView
    private lateinit var  moviename: TextView
    private lateinit var  moviedesc: TextView
    private lateinit var  ratingBar: RatingBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        posterBig = findViewById(R.id.imageBig)
        posterSmall = findViewById(R.id.imageSmall)
        moviename = findViewById(R.id.title)
        ratingBar = findViewById(R.id.movie_rating)
        moviedesc = findViewById(R.id.description)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }
    private fun populateDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(posterBig)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(posterSmall)
        }
        ratingBar.numStars = 5

        moviename.text = extras.getString(MOVIE_TITLE, "")
        ratingBar.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        moviedesc.text = extras.getString(MOVIE_OVERVIEW, "")
    }

    fun btnBack(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}