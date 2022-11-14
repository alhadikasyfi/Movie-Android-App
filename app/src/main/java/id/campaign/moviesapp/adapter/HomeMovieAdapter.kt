package id.campaign.moviesapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import id.campaign.moviesapp.R
import id.campaign.moviesapp.databinding.RowhomeitemBinding
import id.campaign.moviesapp.response.MovieListResponse
import id.campaign.moviesapp.ui.DetailActivity
import id.campaign.moviesapp.utils.Constants.GENRE
import id.campaign.moviesapp.utils.Constants.POSTER_BASE_URL

// Untuk RecyclerView pada menu HOme
// menampilkan trending movies
class HomeMovieAdapter : RecyclerView.Adapter<HomeMovieAdapter.ViewHolder>() {
    private lateinit var binding: RowhomeitemBinding
    private lateinit var context : Context

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        fun bin(item: MovieListResponse.Result){
            binding.apply {
                movieTitle.text = item.title
                movieGenre.text = GENRE[item.genreIds[0]]
                movieRating.text = item.voteAverage.toString()
                val bannerPath = POSTER_BASE_URL + item.backdropPath
                val posterPath = POSTER_BASE_URL + item.posterPath
                homeBanner.load(bannerPath){
                    placeholder(R.drawable.banner_background)
                    scale(Scale.FILL)
                }
                homePoster.load(posterPath){
                    placeholder(R.drawable.banner_background)
                    scale(Scale.FILL)
                }
                root.setOnClickListener{
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.MOVIE_ID, item.id)
                    root.context.startActivity(intent)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RowhomeitemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bin(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallBak=object : DiffUtil.ItemCallback<MovieListResponse.Result>(){
        override fun areItemsTheSame(oldItem: MovieListResponse.Result, newItem: MovieListResponse.Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieListResponse.Result, newItem: MovieListResponse.Result): Boolean {
            return oldItem==newItem
        }

    }

    val differ= AsyncListDiffer(this,differCallBak)
}