package id.campaign.moviesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import id.campaign.moviesapp.R
import id.campaign.moviesapp.databinding.RowBlankBinding
import id.campaign.moviesapp.databinding.RowactoritemBinding
import id.campaign.moviesapp.response.ActorMovieResponse
import id.campaign.moviesapp.utils.Constants.POSTER_BASE_URL

// Untuk RecyclerView pada aktor di DetailActivity
class ActorMovieAdapter: RecyclerView.Adapter<ActorMovieAdapter.ViewHolder>() {
    private lateinit var binding: RowactoritemBinding
    private lateinit var notBinding: RowBlankBinding
    private lateinit var context : Context

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        fun bin(item: ActorMovieResponse.Cast){
            binding.apply {
                actorName.text = item.name
                val posterPath = POSTER_BASE_URL + item.profilePath
                homeBanner.load(posterPath){
                    placeholder(R.drawable.banner_background)
                    scale(Scale.FILL)
                }

                root.setOnClickListener{
                    Toast.makeText(it.context, "${item.name}", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RowactoritemBinding.inflate(inflater, parent, false)
        notBinding = RowBlankBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(differ.currentList[position].profilePath.isEmpty()){
            notBinding.apply {
            }
        } else{
            holder.bin(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallBak=object : DiffUtil.ItemCallback<ActorMovieResponse.Cast>(){
        override fun areItemsTheSame(oldItem: ActorMovieResponse.Cast, newItem: ActorMovieResponse.Cast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ActorMovieResponse.Cast, newItem: ActorMovieResponse.Cast): Boolean {
            return oldItem==newItem
        }

    }

    val differ= AsyncListDiffer(this,differCallBak)
}