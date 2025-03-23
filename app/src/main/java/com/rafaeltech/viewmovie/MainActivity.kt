package com.rafaeltech.viewmovie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.rafaeltech.viewmovie.api.Endpoint
import com.rafaeltech.viewmovie.databinding.ActivityMainBinding
import com.rafaeltech.viewmovie.model.Movie
import com.rafaeltech.viewmovie.recycler.view.ListMovieAdapter
import com.rafaeltech.viewmovie.util.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListMovieAdapter
    //val MOVIE: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observeData()

    }

    fun setupRecyclerView(movies: List<Movie>) {

        adapter = ListMovieAdapter(
            movies = movies,
            onItemClickListener = { movie ->
                val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra("movie", movie)

                startActivity(intent)
            }
        )
        binding.recyclerView.adapter = adapter


        /*
        val retrofitClient = NetworkUtils.getInstance()
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getMovies("665ac3cc29b403addd65ab8d18bce936", "pt-BR", 1).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val movies = mutableListOf<Movie>()
                    val results = response.body()?.getAsJsonArray("results")
                    results?.forEach { movie ->
                        val movieObject = movie.asJsonObject
                        val id = movieObject.get("id").asInt
                        val title = movieObject.get("title").asString
                        //val posterUrl = movieObject.get("poster_url").asString
                        val posterPath = movieObject.get("poster_url").asString
                        val fullPosterUrl = "https://image.tmdb.org/t/p/w500$posterPath"

                        Log.d("Movies", "Full Poster URL: $fullPosterUrl")

                        movies.add(Movie(id, title, posterUrl = fullPosterUrl, poster_path = posterPath ))
                    }

                    val adapter = ListMovieView(
                        movies = movies,
                        onItemClickListener = { movie ->
                            startActivity(Intent(this@MainActivity, MovieDetailsActivity::class.java))
                        }
                    )
                    binding.recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "Erro ao carregar filmes: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha ao carregar os filmes", Toast.LENGTH_SHORT).show()
            }
        })
        */
    }

    fun observeData() {
        //https://api.themoviedb.org/3/?api_key=665ac3cc29b403addd65ab8d18bce936&language=pt-BR&page=1
        //https://api.themoviedb.org/3/movie/popular?api_key=665ac3cc29b403addd65ab8d18bce936&language=pt-BR&page=1
        val retrofitClient = NetworkUtils.getInstance()
        val endpoint = retrofitClient.create(Endpoint::class.java)

        //Coroutines(espera a activity ser iniciada, depois faz a chamada de entrada e saida)
        lifecycleScope.launch{
            val response = endpoint.getMovies("665ac3cc29b403addd65ab8d18bce936", "pt-BR", 1)
            if (response.isSuccessful) {
                val results = response.body()?.results
                setupRecyclerView(results ?: emptyList())
                Log.d("***Movies", "Full Poster URL: $results")

            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Erro ao carregar filmes: ${response.code()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}