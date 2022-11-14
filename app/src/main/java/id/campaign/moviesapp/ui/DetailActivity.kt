package id.campaign.moviesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.size.Scale
import id.campaign.moviesapp.R
import id.campaign.moviesapp.adapter.ActorMovieAdapter
import id.campaign.moviesapp.api.ApiClient
import id.campaign.moviesapp.api.ApiService
import id.campaign.moviesapp.databinding.ActivityDetailBinding
import id.campaign.moviesapp.request.FavoriteRequest
import id.campaign.moviesapp.response.ActorMovieResponse
import id.campaign.moviesapp.response.AddFavoriteResponse
import id.campaign.moviesapp.response.DetailMovieResponse
import id.campaign.moviesapp.response.MovieListResponse
import id.campaign.moviesapp.utils.Constants
import id.campaign.moviesapp.utils.Constants.POSTER_BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Mengatur tata letak, fungsionalitas dari detail movie
class DetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailBinding
    lateinit var actorAdapter : ActorMovieAdapter
    private val api: ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }

    companion object{
        var MOVIE_ID = "movie_id"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var queryMovieId = intent.getIntExtra(MOVIE_ID, 0)
        actorAdapter = ActorMovieAdapter()

        binding.apply {
            //show loading
            prgBarMovies3.visibility = View.VISIBLE
            //Call movies api
            val callMoviesApi = api.getDetailMovie(queryMovieId)
            val callActorApi = api.getAllActorMovie(queryMovieId)
            callMoviesApiFunction(callMoviesApi)
            callActorApiFunction(callActorApi)

        }
        binding.fabFavorite.setOnClickListener{
            val callFavoriteRequest = api.createFavoriteMovie(15767228, "c888c0abc5e8a9df2c01458b9feae485e3f0b425",
            FavoriteRequest(true, queryMovieId, "movie")
            )
            createFavoriteMovieFunction(callFavoriteRequest)
        }
        binding.backImage.setOnClickListener{onBackPressed()}
    }

    private fun createFavoriteMovieFunction(callFavoriteRequest: Call<AddFavoriteResponse>) {
        callFavoriteRequest.enqueue(object : Callback<AddFavoriteResponse> {
            override fun onResponse(call: Call<AddFavoriteResponse>, response: Response<AddFavoriteResponse>) {
                when (response.code()) {
                    in 200..299 -> {
                        Log.d("Response Code", " success messages : ${response.code()}")
                        response.body()?.let { itBody ->
                            Toast.makeText(this@DetailActivity, "${itBody.statusMessage} add to Favofite", Toast.LENGTH_SHORT).show()
                        }
                    }
                    in 300..399 -> {
                        Log.d("Response Code", " Redirection messages : ${response.code()}")
                    }
                    in 400..499 -> {
                        Log.d("Response Code", " Client error responses : ${response.code()}")
                    }
                    in 500..599 -> {
                        Log.d("Response Code", " Server error responses : ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<AddFavoriteResponse>, t: Throwable) {
                Log.e("onFailure", "Err : ${t.message}")
            }
        })
    }

    private fun callActorApiFunction(callActorApi: Call<ActorMovieResponse>) {
        callActorApi.enqueue(object : Callback<ActorMovieResponse> {
            override fun onResponse(call: Call<ActorMovieResponse>, response: Response<ActorMovieResponse>) {
                binding.prgBarMovies.visibility = View.GONE
                when (response.code()) {
                    in 200..299 -> {
                        Log.d("Response Code", " success messages : ${response.code()}")
                        response.body()?.let { itBody ->
                            itBody.cast.let { itData ->
                                if(itData.isNotEmpty()){
                                    actorAdapter.differ.submitList(itData)
                                    // recycler
                                    binding.rvActorMovies.apply {
                                        layoutManager= LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                                        adapter = actorAdapter
                                    }
                                }
                            }

                        }
                    }
                    in 300..399 -> {
                        Log.d("Response Code", " Redirection messages : ${response.code()}")
                    }
                    in 400..499 -> {
                        Log.d("Response Code", " Client error responses : ${response.code()}")
                    }
                    in 500..599 -> {
                        Log.d("Response Code", " Server error responses : ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<ActorMovieResponse>, t: Throwable) {
                binding.prgBarMovies.visibility = View.GONE
                Log.e("onFailure", "Err : ${t.message}")
            }
        })
    }

    private fun callMoviesApiFunction(callMoviesApi : Call<DetailMovieResponse>) {
        callMoviesApi.enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(call: Call<DetailMovieResponse>, response: Response<DetailMovieResponse>) {
                binding.prgBarMovies3.visibility = View.GONE
                when (response.code()) {
                    in 200..299 -> {
                        Log.d("Response Code", " success messages : ${response.code()}")
                        response.body()?.let { itBody ->
                            val bannerPath = POSTER_BASE_URL + itBody.backdropPath
                            val posterPath = POSTER_BASE_URL + itBody.posterPath
                            binding.homeBanner.load(bannerPath){
                                placeholder(R.drawable.banner_background)
                                scale(Scale.FILL)
                            }
                            binding.homePoster.load(posterPath){
                                placeholder(R.drawable.banner_background)
                                scale(Scale.FILL)
                            }
                            binding.movieTitle.text=itBody.title
                            binding.movieGenre.text = itBody.genres[0].name
                            binding.movieRating.text = itBody.voteAverage.toString()
                            binding.movieOverview.text = itBody.overview
                            binding.movieProductionCompany.text = if(itBody.productionCompanies.isNullOrEmpty()){
                                "none"
                            } else {itBody.productionCompanies[0].name}
                        }
                    }
                    in 300..399 -> {
                        Log.d("Response Code", " Redirection messages : ${response.code()}")
                    }
                    in 400..499 -> {
                        Log.d("Response Code", " Client error responses : ${response.code()}")
                    }
                    in 500..599 -> {
                        Log.d("Response Code", " Server error responses : ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                binding.prgBarMovies3.visibility = View.GONE
                Log.e("onFailure", "Err : ${t.message}")
            }
        })
    }
}