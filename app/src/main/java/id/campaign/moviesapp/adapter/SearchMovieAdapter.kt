package id.campaign.moviesapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import id.campaign.moviesapp.R
import id.campaign.moviesapp.databinding.RowsearchitemBinding
import id.campaign.moviesapp.response.SearchMovieResponse
import id.campaign.moviesapp.ui.DetailActivity
import id.campaign.moviesapp.utils.Constants

// untuk recyclerView pada search Menu
class SearchMovieAdapter: RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>() {
    private lateinit var binding: RowsearchitemBinding
    private lateinit var context : Context

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        fun bin(item: SearchMovieResponse.Result){
            binding.apply {
                movieTitle.text = item.title
                movieGenre.text = if(item.genreIds.isNullOrEmpty()){
                    "random"
                } else {Constants.GENRE[item.genreIds[0]]}
                movieRating.text = item.voteAverage.toString()
                val posterPath = Constants.POSTER_BASE_URL + item.posterPath
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
        binding = RowsearchitemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bin(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallBak=object : DiffUtil.ItemCallback<SearchMovieResponse.Result>(){
        override fun areItemsTheSame(oldItem: SearchMovieResponse.Result, newItem: SearchMovieResponse.Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchMovieResponse.Result, newItem: SearchMovieResponse.Result): Boolean {
            return oldItem==newItem
        }

    }

    val differ= AsyncListDiffer(this,differCallBak)
}