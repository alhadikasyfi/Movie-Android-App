package id.campaign.moviesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.campaign.moviesapp.R
import id.campaign.moviesapp.databinding.ActivityLoginBinding

// Mengatur tata letak, fungsionalitas dari Login activity
class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    fun selesai(){
        finish()
    }
}