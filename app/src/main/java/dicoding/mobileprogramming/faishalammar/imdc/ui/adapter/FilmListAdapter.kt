package dicoding.mobileprogramming.faishalammar.imdc.ui.adapter

import android.content.Context
import android.graphics.Movie
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dicoding.mobileprogramming.faishalammar.imdc.R
import dicoding.mobileprogramming.faishalammar.imdc.data.source.local.entity.MoviesAndTvShowsEntity
import dicoding.mobileprogramming.faishalammar.imdc.databinding.FilmItemRowBinding
import dicoding.mobileprogramming.faishalammar.imdc.utils.RemoteRetrieverHelper


class FilmListAdapter(val appContext: Context): PagedListAdapter<MoviesAndTvShowsEntity, FilmListAdapter.FilmViewHolder>(DIFF_CALLBACK) {


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MoviesAndTvShowsEntity>() {
            override fun areItemsTheSame(oldItem: MoviesAndTvShowsEntity, newItem: MoviesAndTvShowsEntity): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: MoviesAndTvShowsEntity, newItem: MoviesAndTvShowsEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var _binding: FilmItemRowBinding? = null
    private val binding get() = _binding!!

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FilmViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(viewGroup.context)

        _binding = FilmItemRowBinding.inflate(inflater, viewGroup, false)
        val view = binding.root
                
        return FilmViewHolder(view)
    }



    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {

        val film = getItem(position)!!

        val filmAvatar: String = "${RemoteRetrieverHelper.IMAGE_DOMANIN}${film.posterImg}"

        val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .override(67, 98)
                .error(R.drawable.ic_launcher_background)


        Glide.with(holder.itemView.context)
                .load(filmAvatar)
                .apply(options)
                .into(holder.filmPoster)

        holder.filmTitle.text = film.title

        holder.genreContainer.removeAllViews()
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 10, 0)


        var genreTextLength : Int = 0

        val newLL = LinearLayout(appContext)
        newLL.layoutParams = holder.genreContainer.layoutParams


        for((textViewId, genre) in film.genre.withIndex()){

            genreTextLength += genre.length

            val genreColor: Int = R.color.primaryYellow

            val layoutInflater: LayoutInflater = LayoutInflater.from(appContext)
            val genreBoxView: View = layoutInflater.inflate(R.layout.genre_box, null)
            val genreTextView: TextView = genreBoxView.findViewById(R.id.genre)
            genreTextView.layoutParams = params

            genreTextView.text = genre
            genreTextView.background = ColorDrawable(ContextCompat.getColor(appContext, genreColor))
            genreTextView.id = textViewId

            if(genreTextLength < 20){
                holder.genreContainer.addView(genreTextView)
            }

            else if(genreTextLength >= 20) {
                newLL.addView(genreTextView)

                if(textViewId == genre.length - 1) {
                    binding.genreLinearLayoutBox.addView(newLL)
                }


            }

        }

        if (!film.isFavourite) {
            holder.favButton.setImageResource(R.drawable.ic_favorite_border_white_24dp)
        } else {
            holder.favButton.setImageResource(R.drawable.ic_favorite_white_24dp)
        }

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(film);
        }

        holder.favButton.setOnClickListener{
            onItemClickCallback.onFavButtonClicked(film)
            changeFavButtonIcon(holder, film.isFavourite)
        }



    }



    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var filmPoster: ImageView = binding.filmPoster
        var filmTitle: TextView = binding.filmTitle
        var genreContainer : LinearLayout = binding.genreLinearLayout
        var genreContainerBox : LinearLayout = binding.genreLinearLayoutBox
        var favButton : ImageButton = binding.favButton
    }

    interface OnItemClickCallback {
        fun onItemClicked(film : MoviesAndTvShowsEntity)
        fun onFavButtonClicked(film : MoviesAndTvShowsEntity)
    }

    private fun changeFavButtonIcon(holder: FilmViewHolder, selectedFilmIsFav : Boolean) {
        if (selectedFilmIsFav) {
            holder.favButton.setImageResource(R.drawable.ic_favorite_border_white_24dp)
        } else {
            holder.favButton.setImageResource(R.drawable.ic_favorite_white_24dp)
        }
    }
}