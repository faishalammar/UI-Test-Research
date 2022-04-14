package dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dicoding.mobileprogramming.faishalammar.imdc.data.MoviesSeriesRepository
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.vo.Resource

class  FilmDetailViewModel(private val moviesSeriesRepository: MoviesSeriesRepository) : ViewModel() {
    fun getMovieDetail(movieId : Int): LiveData<Resource<MoviesAndTvShowsEntity>> = moviesSeriesRepository.getMovieDetail(movieId)
    fun getSeriesDetail(seriesId : Int): LiveData<Resource<MoviesAndTvShowsEntity>> = moviesSeriesRepository.getSeriesDetail(seriesId)

}