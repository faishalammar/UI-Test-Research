package dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel

import android.graphics.Movie
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import dicoding.mobileprogramming.faishalammar.imdc.data.MoviesSeriesRepository
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity

import dicoding.mobileprogramming.faishalammar.imdc.vo.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FilmListViewModelTest {
    private lateinit var viewModel: FilmListViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MoviesSeriesRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MoviesAndTvShowsEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MoviesAndTvShowsEntity>

    @Before
    fun setUp() {
        viewModel = FilmListViewModel(repository)
    }

    @Test
    fun testGetMovies() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(5)
        val liveDataMovies = MutableLiveData<Resource<PagedList<MoviesAndTvShowsEntity>>>()
        liveDataMovies.value = dummyMovies

        `when`(repository.getAllMovies()).thenReturn(liveDataMovies)
        val listMovies = viewModel.getMovies().value?.data

        verify(repository).getAllMovies()
        assertNotNull(listMovies)
        assertEquals(listMovies?.size, 5)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun testGetSeries() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(5)
        val liveDataMovies = MutableLiveData<Resource<PagedList<MoviesAndTvShowsEntity>>>()
        liveDataMovies.value = dummyMovies

        `when`(repository.getAllMovies()).thenReturn(liveDataMovies)
        val listMovies = viewModel.getMovies().value?.data

        verify(repository).getAllMovies()
        assertNotNull(listMovies)
        assertEquals(listMovies?.size, 5)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}