package id.campaign.moviesapp.model

// UserModel digunakan untuk model database
// database yang digunakan adalah realtime database firebase
data class UserModel(
    var userId: String? = null,
    var name: String? = null,
    var email: String? = null,
    var userName: String? = null,
    var password: String? = null
)
