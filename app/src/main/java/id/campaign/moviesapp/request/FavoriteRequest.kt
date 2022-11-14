package id.campaign.moviesapp.request


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

// untuk meminta request agar menambah list favorite movies
@Keep
data class FavoriteRequest(
    @SerializedName("favorite")
    val favorite: Boolean,
    @SerializedName("media_id")
    val mediaId: Int,
    @SerializedName("media_type")
    val mediaType: String
)