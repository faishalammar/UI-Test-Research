package dicoding.mobileprogramming.faishalammar.imdc.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.vo.Resource

interface MoviesSeriesDataSource {

    fun getAllMovies(): LiveData<Resource<PagedList<MoviesAndTvShowsEntity>>>
    fun getAllTvSeries(): LiveData<Resource<PagedList<MoviesAndTvShowsEntity>>>
    fun getMovieDetail(movieId : Int) : LiveData<Resource<MoviesAndTvShowsEntity>>
    fun getSeriesDetail(seriesId : Int) : LiveData<Resource<MoviesAndTvShowsEntity>>
    fun getFavouriteMovies() : LiveData<Resource<PagedList<MoviesAndTvShowsEntity>>>
    fun getFavouriteSeries() : LiveData<Resource<PagedList<MoviesAndTvShowsEntity>>>
    fun addFilmToFavourite(filmId : String)
    fun removeFilmFromFavourite(filmId : String)
}