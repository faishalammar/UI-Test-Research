package dicoding.mobileprogramming.faishalammar.imdc.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import dicoding.mobileprogramming.faishalammar.imdc.data.FakeMoviesSeriesRepository
import dicoding.mobileprogramming.faishalammar.imdc.data.source.remote.RemoteDataSource
import dicoding.mobileprogramming.faishalammar.imdc.utils.DataDummy
import junit.framework.Assert.*
import org.junit.Rule
import org.junit.Test
import com.nhaarman.mockitokotlin2.verify
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.LocalDataSource
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.utils.AppExecutors
import dicoding.mobileprogramming.faishalammar.imdc.utils.LiveDataTestUtil
import dicoding.mobileprogramming.faishalammar.imdc.vo.Resource
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


class MoviesSeriesRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val repository = FakeMoviesSeriesRepository(remote, local, appExecutors)

    private val movieForTest = DataDummy.generateRemoteDummyMovies()[0]
    private val seriesForTest = DataDummy.generateRemoteDummySeries()[0]
    private val listMovieResponse = DataDummy.generateRemoteDummyMovies()
    private val listSeriesResponse = DataDummy.generateRemoteDummySeries()

    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesAndTvShowsEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        repository.getAllMovies()

        val listMoviesEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovies()
        assertNotNull(listMoviesEntities)
        assertEquals(listMovieResponse.size.toLong(), listMoviesEntities.data!!.size.toLong())
    }

    @Test
    fun getAllSeries() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesAndTvShowsEntity>
        `when`(local.getAllSeries()).thenReturn(dataSourceFactory)
        repository.getAllTvSeries()

        val listSeriesEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummySeries()))
        verify(local).getAllSeries()
        assertNotNull(listSeriesEntities)
        assertEquals(listSeriesResponse.size.toLong(), listSeriesEntities.data!!.size.toLong())
    }

    @Test
    fun getAllFavouriteSeries() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesAndTvShowsEntity>
        `when`(local.getFavouriteMovies()).thenReturn(dataSourceFactory)
        repository.getFavouriteMovies()

        val listSeriesEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummySeries()))
        verify<LocalDataSource>(local).getFavouriteMovies()
        assertNotNull(listSeriesEntities)
        assertEquals(listSeriesResponse.size.toLong(), listSeriesEntities.data!!.size.toLong())
    }

    @Test
    fun getAllFavouriteMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesAndTvShowsEntity>
        `when`(local.getFavouriteMovies()).thenReturn(dataSourceFactory)
        repository.getFavouriteMovies()

        val listMoviesEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify<LocalDataSource>(local).getFavouriteMovies()
        assertNotNull(listMoviesEntities)
        assertEquals(listMovieResponse.size.toLong(), listMoviesEntities.data!!.size.toLong())
    }

    @Test
    fun getMovieDetail() {
        val dummyMovies = MutableLiveData<MoviesAndTvShowsEntity>()
        dummyMovies.value = DataDummy.generateDummyMovies()[0]
        val dummyId = dummyMovies.value?.id!!

        `when`(local.getFilm(dummyId)).thenReturn(dummyMovies)

        val movieEntities = LiveDataTestUtil.getValue(repository.getMovieDetail(dummyId.toInt()))
        verify(local).getFilm(dummyId)


        assertEquals(movieForTest.title, movieEntities.data!!.title)
    }

    @Test
    fun getSeriesDetail() {
        val dummySeries = MutableLiveData<MoviesAndTvShowsEntity>()
        dummySeries.value = DataDummy.generateDummySeries()[0]
        val dummyId = dummySeries.value?.id!!

        `when`(local.getFilm(dummyId)).thenReturn(dummySeries)
        val movieEntities = LiveDataTestUtil.getValue(repository.getSeriesDetail(dummyId.toInt()))

        verify(local).getFilm(dummyId)
        assertEquals(seriesForTest.title, movieEntities.data!!.title)
    }

    object PagedListUtil {

        fun <T> mockPagedList(list: List<T>): PagedList<*> {
            val pagedList = mock(PagedList::class.java) as PagedList<*>
            `when`(pagedList[anyInt()]).then { invocation ->
                val index = invocation.arguments.first() as Int
                list[index]
            }
            `when`(pagedList.size).thenReturn(list.size)

            return pagedList
        }
    }

}


