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
class FilmFavouriteViewModelTest {
    private lateinit var viewModel: FilmFavouriteViewModel

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
        viewModel = FilmFavouriteViewModel(repository)
    }

    @Test
    fun testGetFavouriteMovies() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(5)
        val liveDataMovies = MutableLiveData<Resource<PagedList<MoviesAndTvShowsEntity>>>()
        liveDataMovies.value = dummyMovies

        `when`(repository.getFavouriteMovies()).thenReturn(liveDataMovies)
        val listMovies = viewModel.getFavouriteMovies().value?.data

        verify(repository).getFavouriteMovies()
        assertNotNull(listMovies)
        assertEquals(listMovies?.size, 5)

        viewModel.getFavouriteMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }


    @Test
    fun testGetFavouriteSeries() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(5)
        val liveDataMovies = MutableLiveData<Resource<PagedList<MoviesAndTvShowsEntity>>>()
        liveDataMovies.value = dummyMovies

        `when`(repository.getFavouriteSeries()).thenReturn(liveDataMovies)

        val listMovies = viewModel.getFavouriteSeries().value?.data

        verify(repository).getFavouriteSeries()
        assertEquals(listMovies?.size, 5)

        viewModel.getFavouriteSeries().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

}