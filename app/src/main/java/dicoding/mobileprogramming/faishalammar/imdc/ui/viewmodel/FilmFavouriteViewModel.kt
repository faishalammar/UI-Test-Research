package dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import dicoding.mobileprogramming.faishalammar.imdc.data.MoviesSeriesRepository
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.vo.Resource

class FilmFavouriteViewModel(private val moviesSeriesRepository: MoviesSeriesRepository) : ViewModel() {
    fun getFavouriteSeries(): LiveData<Resource<PagedList<MoviesAndTvShowsEntity>>> = moviesSeriesRepository.getFavouriteSeries()
    fun getFavouriteMovies(): LiveData<Resource<PagedList<MoviesAndTvShowsEntity>>> = moviesSeriesRepository.getFavouriteMovies()
    fun addFilmToFavourite(filmId : String) = moviesSeriesRepository.addFilmToFavourite(filmId)
    fun removeFilmFromFavourite(filmId: String) = moviesSeriesRepository.removeFilmFromFavourite(filmId)
}