package dicoding.mobileprogramming.faishalammar.imdc.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.room.MovieSeriesDao

class LocalDataSource private constructor(private val mFilmDao: MovieSeriesDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(MovieSeriesDao: MovieSeriesDao): LocalDataSource =
                INSTANCE ?: LocalDataSource(MovieSeriesDao)
    }

    fun getFilm(filmId : String) : LiveData<MoviesAndTvShowsEntity> = mFilmDao.getFilm(filmId)

    fun getAllMovies(): DataSource.Factory<Int, MoviesAndTvShowsEntity> = mFilmDao.getAllMovie()

    fun getAllSeries(): DataSource.Factory<Int, MoviesAndTvShowsEntity> = mFilmDao.getAllSeries()

    fun getFavouriteMovies(): DataSource.Factory<Int, MoviesAndTvShowsEntity> = mFilmDao.getAllFavouriteMovie()

    fun getFavouriteSeries(): DataSource.Factory<Int, MoviesAndTvShowsEntity> = mFilmDao.getAllFavouriteSeries()

    fun addFilmToFavourite(filmId: String) = mFilmDao.addFilmToFavourite(filmId)

    fun removeFilmFromFavourite(filmId: String) = mFilmDao.removeFilmFromFavourite(filmId)

    fun insertFilm(film : MoviesAndTvShowsEntity) = mFilmDao.insertFilm(film)


}