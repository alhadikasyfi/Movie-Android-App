package id.campaign.moviesapp.api

import id.campaign.moviesapp.request.FavoriteRequest
import id.campaign.moviesapp.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

// interface untuk menetapkan perintah-perintah pada API
interface ApiService {
    // disini isi path dari url nya
    @GET("trending/{media_type}/{time_window}")
    fun getMovieTrendingList(@Path("media_type") media_type: String, @Path("time_window") time_window: String) : Call<MovieListResponse>

    //for search
    @GET("search/movie")
    fun searchMovieList(@Query("query") query: String): Call<SearchMovieResponse>

    // untuk detail movie
    @GET("movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id") movie_id: Int) : Call<DetailMovieResponse>

    // untuk aktor
    @GET("movie/{movie_id}/credits")
    fun getAllActorMovie(@Path("movie_id") movie_id: Int) : Call<ActorMovieResponse>

    // untuk favorite movie
    @POST("account/{account_id}/favorite")
    fun createFavoriteMovie(@Path("account_id") account_id : Int, @Query("session_id") session_id:String, @Body req: FavoriteRequest): Call<AddFavoriteResponse>

    @GET("account/{account_id}/favorite/movies")
    fun getAllFavoriteMovies(@Path("account_id") account_id : Int, @Query("session_id") session_id:String) : Call<GetFaforiteResponse>


}