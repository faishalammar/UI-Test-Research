package dicoding.mobileprogramming.faishalammar.imdc.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.utils.Converters

@Database(entities = [MoviesAndTvShowsEntity::class],
        version = 1,
        exportSchema = false)
@TypeConverters(Converters::class)
abstract class MoviesSeriesDatabase : RoomDatabase(){
    abstract fun movieSeriesDao() : MovieSeriesDao

    companion object {

        @Volatile
        private var INSTANCE: MoviesSeriesDatabase? = null

        fun getInstance(context: Context): MoviesSeriesDatabase =
                INSTANCE ?: synchronized(this) {
                    Room.databaseBuilder(
                            context.applicationContext,
                            MoviesSeriesDatabase::class.java,
                            "Academies.db"
                    ).build().apply {
                        INSTANCE = this
                    }
                }
    }

}