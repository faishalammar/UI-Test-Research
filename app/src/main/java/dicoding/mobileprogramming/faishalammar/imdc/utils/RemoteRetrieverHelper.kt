package dicoding.mobileprogramming.faishalammar.imdc.utils

import android.content.Context
import android.util.Log
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.response.*
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

class RemoteRetrieverHelper(private val context: Context) {

    companion object {
        private const val API_KEY = "684d994290d5087d8c2f53a29a292a3a"
        private const val MAIN_DOMANIN = "https://api.themoviedb.org/3/"
        const val IMAGE_DOMANIN = "https://image.tmdb.org/t/p/w500/"
    }

    fun loadMovies() : ArrayList<MovieResponse> {
        var listMovies = ArrayList<MovieResponse>()
        var listMoviesResponse : Response<ListMovieResponse?>

        runBlocking{

            val deferredMovies = async(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                        .baseUrl(MAIN_DOMANIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                val retrofitService = retrofit.create(FilmRetrofitService::class.java)

                listMoviesResponse = retrofitService.getListMovies(API_KEY)?.execute()!!
                listMoviesResponse
            }

            listMoviesResponse = deferredMovies.await()

            listMovies = listMoviesResponse.body()?.listMovies!!

        }

        return listMovies
    }
    fun loadTvSeries() : ArrayList<TvSeriesResponse> {
        var listSeries = ArrayList<TvSeriesResponse>()
        var listSeriesResponse : Response<ListTvResponse?>

        runBlocking{

            val deferredMovies = async(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                        .baseUrl(MAIN_DOMANIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                val retrofitService = retrofit.create(FilmRetrofitService::class.java)

                listSeriesResponse = retrofitService.getListTvSeries(API_KEY)?.execute()!!
                listSeriesResponse
            }

            listSeriesResponse = deferredMovies.await()

            listSeries = listSeriesResponse.body()?.listTvSeries!!

        }

        return listSeries
    }

    fun loadMovieDetail(movieId : Int): MovieResponse {

        var movie : MovieResponse
        var movieResponse : Response<MovieResponse?>

        runBlocking{

            val deferredMovies = async(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                        .baseUrl(MAIN_DOMANIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                val retrofitService = retrofit.create(FilmRetrofitService::class.java)

                movieResponse = retrofitService.getMovieDetail(movieId, API_KEY)?.execute()!!
                movieResponse
            }

            movieResponse = deferredMovies.await()

            movie = movieResponse.body()!!

        }

        return movie
    }
    fun loadTvDetail(seriesId : Int): TvSeriesResponse {

        var series : TvSeriesResponse
        var seriesResponse : Response<TvSeriesResponse?>

        runBlocking{

            val deferredMovies = async(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                        .baseUrl(MAIN_DOMANIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                val retrofitService = retrofit.create(FilmRetrofitService::class.java)

                seriesResponse = retrofitService.getSeriesDetail(seriesId, API_KEY)?.execute()!!
                seriesResponse
            }

            seriesResponse = deferredMovies.await()

            series = seriesResponse.body()!!

        }

        return series
    }

    fun loadGenreList(forMovie : Boolean) : MutableMap<Int,String> {
        val mutableMap : MutableMap<Int,String> = mutableMapOf(1 to "genreName")
        var arrayListGenre : ArrayList<GenreResponse>
        var listGenreResponse : Response<ListGenreResponse?>

        runBlocking{

            val deferredGenres = async(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                        .baseUrl(MAIN_DOMANIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                val retrofitService = retrofit.create(FilmRetrofitService::class.java)

                if (forMovie ){
                    listGenreResponse = retrofitService.getMovieGenre(API_KEY)?.execute()!!
                } else {listGenreResponse = retrofitService.getTvGenre(API_KEY)?.execute()!!}

                listGenreResponse
            }

            listGenreResponse = deferredGenres.await()
            arrayListGenre = listGenreResponse.body()?.arrayListGenre!!

            for (genre in arrayListGenre) {
                mutableMap[genre.id] = genre.name
            }

        }




        return mutableMap
    }



}