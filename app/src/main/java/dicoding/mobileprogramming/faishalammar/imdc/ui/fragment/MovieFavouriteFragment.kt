package dicoding.mobileprogramming.faishalammar.imdc.ui.fragment

import android.content.Context
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
import dicoding.mobileprogramming.faishalammar.imdc.databinding.FragmentMovieFavouriteBinding
import dicoding.mobileprogramming.faishalammar.imdc.ui.adapter.FilmFavouriteAdapter
import dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel.FilmFavouriteViewModel
import dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel.ViewModelFactory
import dicoding.mobileprogramming.faishalammar.imdc.vo.Status
import kotlin.concurrent.thread


class MovieFavouriteFragment : Fragment() {

    private lateinit var binding: FragmentMovieFavouriteBinding
    private lateinit var viewModel: FilmFavouriteViewModel

    companion object {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieFavouriteBinding.inflate(layoutInflater, container, false)

        val activityContext : Context = activity?.applicationContext!!
        val factory = ViewModelFactory.getInstance(requireActivity())
        val moviesAdapter = FilmFavouriteAdapter(activityContext)

        viewModel = ViewModelProvider(this, factory)[FilmFavouriteViewModel::class.java]


        with(binding.rvFavMovie) {
            layoutManager = LinearLayoutManager(activityContext)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }


        viewModel.getFavouriteMovies().observe(viewLifecycleOwner, { listMovie ->
            if (listMovie != null){
                when (listMovie.status) {
                    Status.SUCCESS -> {
                        moviesAdapter.submitList(listMovie.data)
                        moviesAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, getString(R.string.error_message_retrieve), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })


        moviesAdapter.setOnItemClickCallback(object :
            FilmFavouriteAdapter.OnItemClickCallback {
            override fun onItemClicked(film: MoviesAndTvShowsEntity) {
            }

            override fun onDeleteButtonClicked(film: MoviesAndTvShowsEntity) {
                deleteFavouriteFilm(film)
            }
        })

        return binding.root
    }

    private fun deleteFavouriteFilm(film: MoviesAndTvShowsEntity) {
        Toast.makeText(
                context,
                getString(R.string.delete_favourite_film, film.title),
                Toast.LENGTH_SHORT
        ).show()
        thread(start = true){
            viewModel.removeFilmFromFavourite(film.id)
        }

    }

}
