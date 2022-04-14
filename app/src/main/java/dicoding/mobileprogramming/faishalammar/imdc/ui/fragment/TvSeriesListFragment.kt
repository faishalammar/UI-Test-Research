package dicoding.mobileprogramming.faishalammar.imdc.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dicoding.mobileprogramming.faishalammar.imdc.R
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.databinding.FragmentTvSeriesListBinding
import dicoding.mobileprogramming.faishalammar.imdc.ui.activity.MovieAndTvSeriesDetailActivity
import dicoding.mobileprogramming.faishalammar.imdc.ui.adapter.FilmListAdapter
import dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel.FilmFavouriteViewModel
import dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel.FilmListViewModel
import dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel.ViewModelFactory
import dicoding.mobileprogramming.faishalammar.imdc.vo.Status
import kotlin.concurrent.thread


class TvSeriesListFragment : Fragment() {

    private lateinit var fragmentTvSeriesListBinding: FragmentTvSeriesListBinding
    private lateinit var filmListViewModel: FilmListViewModel
    private lateinit var filmFavouriteViewModel: FilmFavouriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentTvSeriesListBinding = FragmentTvSeriesListBinding.inflate(layoutInflater, container, false)
        val activityContext : Context = activity?.applicationContext!!
        val factory = ViewModelFactory.getInstance(requireActivity())
        filmListViewModel  = ViewModelProvider(this, factory)[FilmListViewModel::class.java]
        filmFavouriteViewModel = ViewModelProvider(this, factory)[FilmFavouriteViewModel::class.java]

        val seriesAdapter = FilmListAdapter(activityContext)

        filmListViewModel.getSeries().observe(viewLifecycleOwner, { listMovie ->
            if (listMovie != null){

                when (listMovie.status) {
                    Status.SUCCESS -> {
                        seriesAdapter.submitList(listMovie.data)
                        seriesAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        with(fragmentTvSeriesListBinding.rvSeries) {
            layoutManager = LinearLayoutManager(activityContext)
            setHasFixedSize(true)
            adapter = seriesAdapter
        }

        /*
        Movies Click to Show Detail
         */

        seriesAdapter.setOnItemClickCallback(object :
            FilmListAdapter.OnItemClickCallback {

            override fun onItemClicked(film: MoviesAndTvShowsEntity) {
                showSeriesDetail(film)
            }

            override fun onFavButtonClicked(
                film : MoviesAndTvShowsEntity
            ) {
                if (film.isFavourite) {
                    deleteFavouriteFilm(film)
                } else {
                    addFavouriteFilm(film)
                }
            }
        }
        )

        return fragmentTvSeriesListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showSeriesDetail(film: MoviesAndTvShowsEntity) {
        val intent = Intent(activity, MovieAndTvSeriesDetailActivity::class.java)
        val movie: MoviesAndTvShowsEntity = film
        val mBundle = Bundle()
        mBundle.putParcelable(MovieAndTvSeriesDetailActivity.EXTRA_MOVIE, movie)
        intent.putExtras(mBundle);
        startActivity(intent)
    }

    private fun addFavouriteFilm(film: MoviesAndTvShowsEntity) {
        thread(start = true){
            filmFavouriteViewModel.addFilmToFavourite(film.id)
        }
        Toast.makeText(
                context,
                getString(R.string.add_favourite_film, film.title),
                Toast.LENGTH_SHORT
        ).show()
    }

    private fun deleteFavouriteFilm(film: MoviesAndTvShowsEntity) {
        thread(start = true){
            filmFavouriteViewModel.removeFilmFromFavourite(film.id)
        }
        Toast.makeText(
                context,
                getString(R.string.delete_favourite_film, film.title),
                Toast.LENGTH_SHORT
        ).show()
    }

}