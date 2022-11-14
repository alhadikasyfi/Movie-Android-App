package id.campaign.moviesapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.campaign.moviesapp.R
import id.campaign.moviesapp.adapter.HomeMovieAdapter
import id.campaign.moviesapp.api.ApiClient
import id.campaign.moviesapp.api.ApiService
import id.campaign.moviesapp.databinding.FragmentHomeBinding
import id.campaign.moviesapp.response.MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


// Mengatur tata letak, fungsionalitas dari fragment Home
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieAdapter: HomeMovieAdapter
    private val api: ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentHomeBinding.inflate(inflater, container, false)
        movieAdapter = HomeMovieAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            //show loading
            prgBarMovies.visibility = View.VISIBLE
            //Call movies api
            val callMoviesApi = api.getMovieTrendingList("movie", "day")
            callMoviesApi.enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(call: Call<MovieListResponse>, response: Response<MovieListResponse>) {
                    prgBarMovies.visibility = View.GONE
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        movieAdapter.differ.submitList(itData)
                                        //Recycler
                                        rvTrendingMovies.apply {
                                            layoutManager = LinearLayoutManager(rvTrendingMovies.context, LinearLayoutManager.HORIZONTAL, false)
                                            Log.d("kou koku kou koku okkkuko", "asldkjaldskfj")
                                            adapter = movieAdapter
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

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                    Log.e("onFailure", "Err : ${t.message}")
                }
            })
        }
    }

}