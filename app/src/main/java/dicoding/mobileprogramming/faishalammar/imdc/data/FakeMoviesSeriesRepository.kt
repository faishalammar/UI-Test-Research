package dicoding.mobileprogramming.faishalammar.imdc.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dicoding.mobileprogramming.faishalammar.imdc.data.source.MoviesSeriesDataSource
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.LocalDataSource
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.ApiResponse
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.RemoteDataSource
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.response.MovieResponse
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.response.TvSeriesResponse
import dicoding.mobileprogramming.faishalammar.imdc.utils.AppExecutors
import dicoding.mobileprogramming.faishalammar.imdc.vo.Resource

class FakeMoviesSeriesRepository (private val remoteDataSource: RemoteDataSource,
                                  private val localDataSource: LocalDataSource,
                                  private val appExecutors: AppExecutors
) : MoviesSeriesDataSource {


    override fun getAllMovies(): LiveData<Resource<PagedList<MoviesAndTvShowsEntity>>> {
        val movieGenre : MutableMap<Int, String> = remoteDataSource.getAllGenre(true)

        return object : NetworkBoundResource<PagedList<MoviesAndTvShowsEntity>, List<MovieResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MoviesAndTvShowsEntity>> {

                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MoviesAndTvShowsEntity>?): Boolean =
                data == null || data.isEmpty()
            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()
            public override fun saveCallResult(data : List<MovieResponse>) {
                val movieList = ArrayList<MoviesAndTvShowsEntity>()
                for (response in data) {
                    val ListGenre = ArrayList<String>()

                    for(genreId in response.genre) {
                        val genreName : String = movieGenre[genreId.toInt()].toString()
                        ListGenre.add(genreName)
                    }

                    val movie = MoviesAndTvShowsEntity(
                        response.id,
                        response.title,
                        response.overview,
                        response.rating,
                        response.posterImg,
                        ListGenre,
                        true
                    )

                    movieList.add(movie)
                    localDataSource.insertFilm(movie)

                }

            }
        }.asLiveData()
    }

    override fun getAllTvSeries(): LiveData<Resource<PagedList<MoviesAndTvShowsEntity>>> {
        val seriesGenre : MutableMap<Int, String> = remoteDataSource.getAllGenre(false)
        return object : NetworkBoundResource<PagedList<MoviesAndTvShowsEntity>, List<TvSeriesResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MoviesAndTvShowsEntity>>{
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllSeries(), config).build()
            }
            override fun shouldFetch(data: PagedList<MoviesAndTvShowsEntity>?): Boolean =
                data == null || data.isEmpty()
            public override fun createCall(): LiveData<ApiResponse<List<TvSeriesResponse>>> =
                remoteDataSource.getAllSeries()
            public override fun saveCallResult(data : List<TvSeriesResponse>) {
                val seriesList = ArrayList<MoviesAndTvShowsEntity>()
                for (response in data) {
                    val ListGenre = ArrayList<String>()

                    for(genreId in response.genre) {
                        val genreName : String = seriesGenre[genreId.toInt()].toString()
                        ListGenre.add(genreName)
                    }

                    val series = MoviesAndTvShowsEntity(
                        response.id,
                        response.title,
                        response.overview,
                        response.rating,
                        response.posterImg,
                        ListGenre,
                        false
                    )

                    seriesList.add(series)
                    localDataSource.insertFilm(series)

                }

            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MoviesAndTvShowsEntity>> {
        return object : NetworkBoundResource<MoviesAndTvShowsEntity, MovieResponse>(appExecutors) {
            public override fun loadFromDB(): LiveData<MoviesAndTvShowsEntity> =
                localDataSource.getFilm(movieId.toString())
            override fun shouldFetch(data: MoviesAndTvShowsEntity?): Boolean =
                data == null
            public override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovieDetail(movieId)
            public override fun saveCallResult(data : MovieResponse) {
                val ListGenre : ArrayList<String> = ArrayList()

                for (genre in data.listGenre){
                    ListGenre.add(genre.name)
                }

                val movieDetail = MoviesAndTvShowsEntity(
                    data.id,
                    data.title,
                    data.overview,
                    data.rating,
                    data.posterImg,
                    ListGenre,
                    true
                )

            }
        }.asLiveData()
    }

    override fun getSeriesDetail(seriesId: Int): LiveData<Resource<MoviesAndTvShowsEntity>> {
        return object : NetworkBoundResource<MoviesAndTvShowsEntity, TvSeriesResponse>(appExecutors) {
            public override fun loadFromDB(): LiveData<MoviesAndTvShowsEntity> =
                localDataSource.getFilm(seriesId.toString())
            override fun shouldFetch(data: MoviesAndTvShowsEntity?): Boolean =
                data == null
            public override fun createCall(): LiveData<ApiResponse<TvSeriesResponse>> =
                remoteDataSource.getSeriesDetail(seriesId)
            public override fun saveCallResult(data : TvSeriesResponse) {
                val ListGenre : ArrayList<String> = ArrayList()

                for (genre in data.listGenre){
                    ListGenre.add(genre.name)
                }

                val movieDetail = MoviesAndTvShowsEntity(
                    data.id,
                    data.title,
                    data.overview,
                    data.rating,
                    data.posterImg,
                    ListGenre,
                    false
                )

            }
        }.asLiveData()
    }

    override fun getFavouriteMovies(): LiveData<Resource<PagedList<MoviesAndTvShowsEntity>>> {
        return object : NetworkBoundResource<PagedList<MoviesAndTvShowsEntity>, List<MovieResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MoviesAndTvShowsEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getFavouriteMovies(), config).build()
            }
            override fun shouldFetch(data: PagedList<MoviesAndTvShowsEntity>?): Boolean =
                false
            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()
            public override fun saveCallResult(data : List<MovieResponse>) {
            }
        }.asLiveData()
    }

    override fun getFavouriteSeries(): LiveData<Resource<PagedList<MoviesAndTvShowsEntity>>> {
        return object : NetworkBoundResource<PagedList<MoviesAndTvShowsEntity>, List<TvSeriesResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MoviesAndTvShowsEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getFavouriteSeries(), config).build()
            }
            override fun shouldFetch(data: PagedList<MoviesAndTvShowsEntity>?): Boolean =
                false
            public override fun createCall(): LiveData<ApiResponse<List<TvSeriesResponse>>> =
                remoteDataSource.getAllSeries()
            public override fun saveCallResult(data : List<TvSeriesResponse>) {
            }
        }.asLiveData()
    }

    override fun addFilmToFavourite(filmId : String) {
        localDataSource.addFilmToFavourite(filmId)
    }

    override fun removeFilmFromFavourite(filmId : String) {
        localDataSource.removeFilmFromFavourite(filmId)
    }
}