package id.campaign.moviesapp.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
// untuk menerima response API untuk daftar actor
@Keep
data class ActorMovieResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
) {
    @Keep
    data class Cast(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("cast_id")
        val castId: Int,
        @SerializedName("character")
        val character: String,
        @SerializedName("credit_id")
        val creditId: String,
        @SerializedName("gender")
        val gender: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("known_for_department")
        val knownForDepartment: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("order")
        val order: Int,
        @SerializedName("original_name")
        val originalName: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("profile_path")
        val profilePath: String
    )

    @Keep
    data class Crew(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("credit_id")
        val creditId: String,
        @SerializedName("department")
        val department: String,
        @SerializedName("gender")
        val gender: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("job")
        val job: String,
        @SerializedName("known_for_department")
        val knownForDepartment: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("original_name")
        val originalName: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("profile_path")
        val profilePath: String
    )
}