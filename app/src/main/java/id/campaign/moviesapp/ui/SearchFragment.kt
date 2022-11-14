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
import id.campaign.moviesapp.adapter.HomeMovieAdapter
import id.campaign.moviesapp.adapter.SearchMovieAdapter
import id.campaign.moviesapp.api.ApiClient
import id.campaign.moviesapp.api.ApiService
import id.campaign.moviesapp.databinding.FragmentHomeBinding
import id.campaign.moviesapp.databinding.FragmentSearchBinding
import id.campaign.moviesapp.response.MovieListResponse
import id.campaign.moviesapp.response.SearchMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


// Mengatur tata letak, fungsionalitas dari fragment search
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSearchBinding
    private lateinit var movieAdapter: SearchMovieAdapter
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
        //return inflater.inflate(R.layout.fragment_search, container, false)
        binding =FragmentSearchBinding.inflate(inflater, container, false)
        binding.prgBarMovies.visibility = View.GONE
        movieAdapter = SearchMovieAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        binding.searchIconTap.setOnClickListener{
            if(binding.editTextSearch.text.isNotEmpty()){
                val query = binding.editTextSearch.text.toString()
                searchMovies(query)
            } else{
                Toast.makeText(
                    binding.searchIconTap.context, "Please enter search text",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun searchMovies(yangdicari:String) {
        binding.apply {
            //show loading
            prgBarMovies.visibility = View.VISIBLE
            //Call movies api
            val callMoviesApi = api.searchMovieList(yangdicari)
            callMoviesApi.enqueue(object : Callback<SearchMovieResponse> {
                override fun onResponse(call: Call<SearchMovieResponse>, response: Response<SearchMovieResponse>) {
                    prgBarMovies.visibility = View.GONE
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        movieAdapter.differ.submitList(itData)
                                        //Recycler
                                        rvSearchList.apply {
                                            layoutManager = LinearLayoutManager(rvSearchList.context)
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

                override fun onFailure(call: Call<SearchMovieResponse>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                    Log.e("onFailure", "Err : ${t.message}")
                }
            })
        }
    }
}