package dicoding.mobileprogramming.faishalammar.imdc.utils

import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmRetrofitService {
    @GET("movie/popular")
    fun getListMovies(@Query("api_key") api_key: String): Call<ListMovieResponse?>?

    @GET("tv/popular")
    fun getListTvSeries(@Query("api_key") api_key: String): Call<ListTvResponse?>?

    @GET("genre/tv/list")
    fun getTvGenre(@Query("api_key") api_key: String): Call<ListGenreResponse?>?

    @GET("genre/movie/list")
    fun getMovieGenre(@Query("api_key") api_key: String): Call<ListGenreResponse?>?

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") movieId : Int, @Query("api_key") api_key: String): Call<MovieResponse?>?

    @GET("tv/{id}")
    fun getSeriesDetail(@Path("id") movieId : Int, @Query("api_key") api_key: String ): Call<TvSeriesResponse?>?

}