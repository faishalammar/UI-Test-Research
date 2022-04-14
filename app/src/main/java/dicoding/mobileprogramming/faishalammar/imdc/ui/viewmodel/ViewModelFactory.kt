package dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dicoding.mobileprogramming.faishalammar.imdc.data.MoviesSeriesRepository
import dicoding.mobileprogramming.faishalammar.imdc.utils.Injection

class ViewModelFactory private constructor(private val mFilmRepository: MoviesSeriesRepository) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FilmListViewModel::class.java) -> {
                FilmListViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(FilmDetailViewModel::class.java) -> {
                FilmDetailViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(FilmFavouriteViewModel::class.java) -> {
                FilmFavouriteViewModel(mFilmRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}