package id.campaign.moviesapp.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
// untuk menerima response API untuk daftar favorite
@Keep
data class AddFavoriteResponse(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String
)