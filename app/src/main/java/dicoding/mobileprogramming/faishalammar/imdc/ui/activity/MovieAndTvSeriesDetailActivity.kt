package dicoding.mobileprogramming.faishalammar.imdc.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dicoding.mobileprogramming.faishalammar.imdc.R
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.databinding.ActivityMovieDetailBinding
import dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel.FilmDetailViewModel
import dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel.FilmListViewModel
import dicoding.mobileprogramming.faishalammar.imdc.ui.viewmodel.ViewModelFactory
import dicoding.mobileprogramming.faishalammar.imdc.utils.RemoteRetrieverHelper
import dicoding.mobileprogramming.faishalammar.imdc.vo.Status

class MovieAndTvSeriesDetailActivity : AppCompatActivity() {
    private var _binding: ActivityMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var movie : MoviesAndTvShowsEntity

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Inflate the layout for this fragment
        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root

        movie = intent.extras?.getParcelable<MoviesAndTvShowsEntity>(EXTRA_MOVIE)!!
        val factory = ViewModelFactory.getInstance(this)
        val viewModel : FilmDetailViewModel = ViewModelProvider(this, factory)[FilmDetailViewModel::class.java]



        if(movie.isMovies){
            viewModel.getMovieDetail(movieId = movie.id.toInt()).observe(LifecycleOwner{lifecycle}, { movieObserved->
                if(movieObserved!=null){
                    when (movieObserved.status) {
                        Status.SUCCESS -> {
                            movie = movieObserved.data!!
                            binding.filmDescriptionDetail.text = movie.overview
                            binding.filmDetailTitle.text = movie.title
                            binding.filmDetailRating.text = movie.rating.toString()

                            var genreString = ""
                            if (movie.genre != null){
                                for(genre in movie.genre!!){
                                    genreString += "$genre | "
                                }
                            }


                            binding.filmDetailGenre.text = genreString

                            val options: RequestOptions = RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .override(binding.filmDetailPoster.width, binding.filmDetailPoster.height)
                                    .error(R.drawable.ic_launcher_background)

                            val filmAvatar: String = "${RemoteRetrieverHelper.IMAGE_DOMANIN}${movie.posterImg}"

                            Glide.with(applicationContext)
                                    .load(filmAvatar)
                                    .apply(options)
                                    .into(binding.filmDetailPoster)


                        }
                        Status.ERROR -> {
                            Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }


            })
        } else {
            viewModel.getSeriesDetail(seriesId = movie.id.toInt()).observe(LifecycleOwner{lifecycle}, { seriesObserved->

                if(seriesObserved!=null){
                    when (seriesObserved.status) {
                        Status.SUCCESS -> {
                            movie = seriesObserved.data!!
                            binding.filmDescriptionDetail.text = movie.overview
                            binding.filmDetailTitle.text = movie.title
                            binding.filmDetailRating.text = movie.rating.toString()

                            var genreString = ""
                            if (movie.genre != null){
                                for(genre in movie.genre!!){
                                    genreString += "$genre | "
                                }
                            }


                            binding.filmDetailGenre.text = genreString

                            val options: RequestOptions = RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .override(binding.filmDetailPoster.width, binding.filmDetailPoster.height)
                                    .error(R.drawable.ic_launcher_background)

                            val filmAvatar: String = "${RemoteRetrieverHelper.IMAGE_DOMANIN}${movie.posterImg}"

                            Glide.with(applicationContext)
                                    .load(filmAvatar)
                                    .apply(options)
                                    .into(binding.filmDetailPoster)


                        }
                        Status.ERROR -> {
                            Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }


            })
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        setContentView(view)

    }
}