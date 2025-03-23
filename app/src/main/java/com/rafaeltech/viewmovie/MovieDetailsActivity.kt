package com.rafaeltech.viewmovie

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.rafaeltech.viewmovie.databinding.ActivityMovieDetailsBinding
import com.rafaeltech.viewmovie.model.Movie
class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    //private lateinit var filme: Movie
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bundle = intent.extras
        if (bundle != null) {

           val IMG_BACK_URL = "https://image.tmdb.org/t/p/w500"
           //val filme =  bundle.getString("movie") as Movie

           val filme = (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("movie", Movie::class.java)
            } else {
                bundle.getParcelable("movie") as? Movie//deprecate version
            })!!

            Glide.with(binding.imgDetalhes.context).load(IMG_BACK_URL + filme.backDropPath).into(binding.imgDetalhes)
            binding.titleMovie.text = filme.title
            binding.textmovie.text = filme.overView


            Log.d("***img", filme.backDropPath)

            /*
            filme?.let {

            } ?: run {
                binding.textmovie.text = "Filme não encontrado"
            }
            */

        }



        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}