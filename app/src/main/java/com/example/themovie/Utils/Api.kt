package com.example.themovie.Utils

import android.util.Log
import com.example.themovie.Models.GetMoviesResponse
import com.example.themovie.Models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "37a79d310f47a8de8b3d9d2ed88993bd",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("search/movie")
    fun getSearchMovies(
        @Query("api_key") apiKey: String = "37a79d310f47a8de8b3d9d2ed88993bd",
        @Query("page") page: Int,
        @Query("title") title: String
    ): Call<GetMoviesResponse>

    @GET("movie/{movie_id}/videos")
    fun getPopularVidoes(
        @Query("api_key") apiKey: String = "37a79d310f47a8de8b3d9d2ed88993bd",
        @Query("page") page: Int,
        movie_id : Int
    ): Call<GetMoviesResponse>

    object MoviesRepository {

        private val api: Api

        init {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            api = retrofit.create(Api::class.java)
        }
        fun getPopularMovies(
            page: Int = 1,
            onSuccess: (movies: List<Movie>) -> Unit,
            onError: () -> Unit
        ) {
            api.getPopularMovies(page = page)
                .enqueue(object : Callback<GetMoviesResponse> {
                    override fun onResponse(
                        call: Call<GetMoviesResponse>,
                        response: Response<GetMoviesResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                onSuccess.invoke(responseBody.movies)
                            } else {
                                onError.invoke()
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                    override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                        onError.invoke()
                    }
                })
        }
        fun getSearchMovies(
            page: Int = 1,
            title: String,
            onSuccess: (movies: List<Movie>) -> Unit,
            onError: () -> Unit,
        ) {
            api.getSearchMovies(page = page, title = title)
                .enqueue(object : Callback<GetMoviesResponse> {
                    override fun onResponse(
                        call: Call<GetMoviesResponse>,
                        response: Response<GetMoviesResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                onSuccess.invoke(responseBody.movies)
                            } else {
                                onError.invoke()
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                    override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                        onError.invoke()
                    }
                })
        }

    }

}