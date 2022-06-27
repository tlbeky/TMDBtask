package com.example.themovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovie.Models.Movie
import com.example.themovie.Utils.Api

class MainActivity : AppCompatActivity() {
    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var searchText : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar?.hide();

        popularMovies = findViewById(R.id.popular_movies)
        popularMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        popularMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        popularMovies.adapter = popularMoviesAdapter

        searchText = findViewById(R.id.editSearch)
        searchText.addTextChangedListener(){
            Api.MoviesRepository.getSearchMovies(
                onSuccess = ::onPopularMoviesFetched,
                onError = ::onError,
                title = "$it"
            )
        }

        Api.MoviesRepository.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )

    }
    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.updateMovies(movies)
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetails::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }
}

