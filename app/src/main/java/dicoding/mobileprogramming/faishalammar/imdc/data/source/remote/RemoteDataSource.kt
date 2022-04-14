package dicoding.mobileprogramming.faishalammar.imdc.data.source.remote

import android.graphics.Movie
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.response.MovieResponse
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.response.TvSeriesResponse
import dicoding.mobileprogramming.faishalammar.imdc.utils.EspressoIdlingResource
import dicoding.mobileprogramming.faishalammar.imdc.utils.RemoteRetrieverHelper

class RemoteDataSource private constructor(private val remoteRetrieverHelper: RemoteRetrieverHelper) {

    private val handler = Handler(Looper.getMainLooper())

    companion object {

        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: RemoteRetrieverHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getAllMovies() : LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        handler.postDelayed({
            resultMovie.value = ApiResponse.success(remoteRetrieverHelper.loadMovies())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultMovie
    }

    fun getAllSeries() : LiveData<ApiResponse<List<TvSeriesResponse>>> {
        EspressoIdlingResource.increment()
        val resultSeries = MutableLiveData<ApiResponse<List<TvSeriesResponse>>>()
        handler.postDelayed({
            resultSeries.value = ApiResponse.success(remoteRetrieverHelper.loadTvSeries())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultSeries
    }

    fun getAllGenre(forMovie : Boolean): MutableMap<Int, String> = remoteRetrieverHelper.loadGenreList(forMovie)

    fun getMovieDetail(movieId : Int): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<MovieResponse>>()
        handler.postDelayed({
            result.value = ApiResponse.success(remoteRetrieverHelper.loadMovieDetail(movieId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return result
    }

    fun getSeriesDetail(seriesId : Int) : LiveData<ApiResponse<TvSeriesResponse>>{
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<TvSeriesResponse>>()
        handler.postDelayed({
            result.value = ApiResponse.success(remoteRetrieverHelper.loadTvDetail(seriesId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return result
    }



}