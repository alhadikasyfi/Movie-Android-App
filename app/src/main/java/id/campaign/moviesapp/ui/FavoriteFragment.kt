package id.campaign.moviesapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import id.campaign.moviesapp.R
import id.campaign.moviesapp.adapter.FavoriteMovieAdapter
import id.campaign.moviesapp.adapter.HomeMovieAdapter
import id.campaign.moviesapp.api.ApiClient
import id.campaign.moviesapp.api.ApiService
import id.campaign.moviesapp.databinding.FragmentFavoriteBinding
import id.campaign.moviesapp.databinding.FragmentHomeBinding
import id.campaign.moviesapp.response.GetFaforiteResponse
import id.campaign.moviesapp.response.MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

// Mengatur tata letak, fungsionalitas dari fragment favorite
class FavoriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var movieAdapter: FavoriteMovieAdapter
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
        binding =FragmentFavoriteBinding.inflate(inflater, container, false)
        movieAdapter = FavoriteMovieAdapter()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoriteIconTap.setOnClickListener{
            Toast.makeText(binding.root.context, "To add favorite movies, please go to Detail Movies",
            Toast.LENGTH_SHORT).show()
        }

        binding.apply {
            //show loading
            prgBarMovies.visibility = View.VISIBLE
            //Call movies api
            val callMoviesApi = api.getAllFavoriteMovies(15767228, "c888c0abc5e8a9df2c01458b9feae485e3f0b425")
            callMoviesApi.enqueue(object : Callback<GetFaforiteResponse> {
                override fun onResponse(call: Call<GetFaforiteResponse>, response: Response<GetFaforiteResponse>) {
                    prgBarMovies.visibility = View.GONE
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        movieAdapter.differ.submitList(itData)
                                        //Recycler
                                        rvFavoriteList.apply {
                                            layoutManager = LinearLayoutManager(rvFavoriteList.context)
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

                override fun onFailure(call: Call<GetFaforiteResponse>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                    Log.e("onFailure", "Err : ${t.message}")
                }
            })
        }
    }
}