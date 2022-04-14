package dicoding.mobileprogramming.faishalammar.imdc.utils

import android.content.Context
import dicoding.mobileprogramming.faishalammar.imdc.data.MoviesSeriesRepository
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.LocalDataSource
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.room.MoviesSeriesDatabase
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): MoviesSeriesRepository {
        val database = MoviesSeriesDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(RemoteRetrieverHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.movieSeriesDao())
        val appExecutors = AppExecutors()

        return MoviesSeriesRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}