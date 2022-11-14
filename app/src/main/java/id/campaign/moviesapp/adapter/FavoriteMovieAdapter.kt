package id.campaign.moviesapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import id.campaign.moviesapp.R
import id.campaign.moviesapp.databinding.RowfavoriteitemBinding
import id.campaign.moviesapp.response.GetFaforiteResponse
import id.campaign.moviesapp.ui.DetailActivity
import id.campaign.moviesapp.utils.Constants
import id.campaign.moviesapp.utils.Constants.POSTER_BASE_URL

// Untuk recyclerView yang ada di menu Favorite
class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>(){
    private lateinit var binding: RowfavoriteitemBinding
    private lateinit var context : Context

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        fun bin(item: GetFaforiteResponse.Result){
            binding.apply {
                movieTitle.text = item.title
                movieGenre.text = Constants.GENRE[item.genreIds[0]]
                movieRating.text = item.voteAverage.toString()
                val posterPath = POSTER_BASE_URL + item.posterPath

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
        binding = RowfavoriteitemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bin(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallBak=object : DiffUtil.ItemCallback<GetFaforiteResponse.Result>(){
        override fun areItemsTheSame(oldItem: GetFaforiteResponse.Result, newItem: GetFaforiteResponse.Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GetFaforiteResponse.Result, newItem: GetFaforiteResponse.Result): Boolean {
            return oldItem==newItem
        }

    }

    val differ= AsyncListDiffer(this,differCallBak)

}