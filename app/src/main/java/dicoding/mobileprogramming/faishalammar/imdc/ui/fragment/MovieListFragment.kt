package dicoding.mobileprogramming.faishalammar.imdc.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dicoding.mobileprogramming.faishalammar.imdc.R
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.databinding.FragmentMovieListBinding
import dicoding.mobileprogramming.faishalammar.imdc.ui.activity.MovieAndTvSeriesDetailActivity
import dicoding.mobileprogramming.faishalammar.imdc.ui.adapter.FilmListAdapter
import dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel.FilmFavouriteViewModel
import dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel.FilmListViewModel
import dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel.ViewModelFactory
import dicoding.mobileprogramming.faishalammar.imdc.vo.Status
import kotlin.concurrent.thread


class MovieListFragment : Fragment() {

    private lateinit var fragmentMovieListBinding: FragmentMovieListBinding
    private lateinit var filmListViewModel: FilmListViewModel
    private lateinit var filmFavouriteViewModel: FilmFavouriteViewModel
    private lateinit var moviesAdapter: FilmListAdapter

    companion object {

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMovieListBinding =
            FragmentMovieListBinding.inflate(layoutInflater, container, false)

        val activityContext: Context = activity?.applicationContext!!
        val factory = ViewModelFactory.getInstance(requireActivity())
        moviesAdapter = FilmListAdapter(activityContext)

        filmListViewModel = ViewModelProvider(this, factory)[FilmListViewModel::class.java]
        filmFavouriteViewModel =
            ViewModelProvider(this, factory)[FilmFavouriteViewModel::class.java]

        with(fragmentMovieListBinding.rvMovie) {
            layoutManager = LinearLayoutManager(activityContext)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }


        filmListViewModel.getMovies().observe(viewLifecycleOwner, { listMovie ->
            if (listMovie != null) {

                when (listMovie.status) {
                    Status.SUCCESS -> {
                        moviesAdapter.submitList(listMovie.data)
                        moviesAdapter.notifyDataSetChanged()

                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })


        /*
        Movies Click to Show Detail
         */

        moviesAdapter.setOnItemClickCallback(object :
            FilmListAdapter.OnItemClickCallback {
            override fun onItemClicked(film: MoviesAndTvShowsEntity) {
                showMovieDetail(film)
            }

            override fun onFavButtonClicked(film: MoviesAndTvShowsEntity) {
                if (film.isFavourite) {
                    deleteFavouriteFilm(film)
                } else {
                    addFavouriteFilm(film)
                }
            }

        }

        )

        return fragmentMovieListBinding.root
    }


    private fun showMovieDetail(film: MoviesAndTvShowsEntity) {
        val intent = Intent(activity, MovieAndTvSeriesDetailActivity::class.java)
        val movie: MoviesAndTvShowsEntity = film
        val mBundle = Bundle()
        mBundle.putParcelable(MovieAndTvSeriesDetailActivity.EXTRA_MOVIE, movie)
        intent.putExtras(mBundle);
        startActivity(intent)
    }

    private fun addFavouriteFilm(film: MoviesAndTvShowsEntity) {
        thread(start = true) {
            filmFavouriteViewModel.addFilmToFavourite(film.id)
        }
        Toast.makeText(
            context,
            getString(R.string.add_favourite_film, film.title),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun deleteFavouriteFilm(film: MoviesAndTvShowsEntity) {
        thread(start = true) {
            filmFavouriteViewModel.removeFilmFromFavourite(film.id)
        }
        Toast.makeText(
            context,
            getString(R.string.delete_favourite_film, film.title),
            Toast.LENGTH_SHORT
        ).show()
    }

}