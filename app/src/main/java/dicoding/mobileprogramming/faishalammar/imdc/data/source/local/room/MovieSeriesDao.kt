package dicoding.mobileprogramming.faishalammar.imdc.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity

@Dao
interface MovieSeriesDao {

    @Query("SELECT * FROM film WHERE isMovie = 1")
    fun getAllMovie(): DataSource.Factory<Int, MoviesAndTvShowsEntity>

    @Query("SELECT * FROM film WHERE isMovie = 0")
    fun getAllSeries(): DataSource.Factory<Int, MoviesAndTvShowsEntity>

    @Query("SELECT * FROM film WHERE id = :filmId")
    fun getFilm(filmId: String): LiveData<MoviesAndTvShowsEntity>

    @Query("SELECT * FROM film WHERE isMovie = 1 AND isFavourite=1")
    fun getAllFavouriteMovie(): DataSource.Factory<Int, MoviesAndTvShowsEntity>

    @Query("SELECT * FROM film WHERE isMovie = 0 AND isFavourite=1")
    fun getAllFavouriteSeries(): DataSource.Factory<Int, MoviesAndTvShowsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListFilm(listFilm: List<MoviesAndTvShowsEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFilm(film: MoviesAndTvShowsEntity)

    @Update
    fun updateFilm(film: MoviesAndTvShowsEntity)

    @Query("UPDATE film SET isFavourite = 1 WHERE id = :filmId")
    fun addFilmToFavourite(filmId: String)

    @Query("UPDATE film SET isFavourite = 0 WHERE id = :filmId")
    fun removeFilmFromFavourite(filmId: String)

}