package dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import dicoding.mobileprogramming.faishalammar.imdc.data.MoviesSeriesRepository
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.utils.DataDummy
import dicoding.mobileprogramming.faishalammar.imdc.vo.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FilmDetailViewModelTest {
    private lateinit var viewModel: FilmDetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MoviesSeriesRepository

    @Mock
    private lateinit var observer: Observer<Resource<MoviesAndTvShowsEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MoviesAndTvShowsEntity>

    @Before
    fun setUp() {
        viewModel = FilmDetailViewModel(repository)
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Test
    fun testGetSeriesDetail() {
        val dummySeries = Resource.success(DataDummy.generateDummySeries()[0])
        val series = MutableLiveData<Resource<MoviesAndTvShowsEntity>>()
        series.value = dummySeries

        val seriesId = series.value!!.data!!.id.toInt()
        
        Mockito.`when`(repository.getSeriesDetail(seriesId)).thenReturn(series)
        val seriesEntity : MoviesAndTvShowsEntity? = viewModel.getSeriesDetail(seriesId).value?.data
        Mockito.verify(repository).getSeriesDetail(seriesId)

        assertNotNull(seriesEntity)
        assertEquals(seriesEntity, dummySeries.data)

        viewModel.getSeriesDetail(seriesId).observeForever(observer)
        Mockito.verify(observer).onChanged(dummySeries)
    }

    @Test
    fun testGetMovieDetail() {
        val dummyMovies = Resource.success(DataDummy.generateDummyMovies()[0])
        val movie = MutableLiveData<Resource<MoviesAndTvShowsEntity>>()
        movie.value = dummyMovies

        val movieId = movie.value!!.data!!.id.toInt()

        Mockito.`when`(repository.getMovieDetail(movieId)).thenReturn(movie)
        val movieEntity : MoviesAndTvShowsEntity? = viewModel.getMovieDetail(movieId).value?.data
        Mockito.verify(repository).getMovieDetail(movieId)

        assertNotNull(movieEntity)
        assertEquals(movieEntity, dummyMovies.data)

        viewModel.getMovieDetail(movieId).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }
    
}