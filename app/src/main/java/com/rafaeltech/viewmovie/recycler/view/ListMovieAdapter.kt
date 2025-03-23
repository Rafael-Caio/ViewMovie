package com.rafaeltech.viewmovie.recycler.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafaeltech.viewmovie.databinding.RecyclerItemviewBinding
import com.rafaeltech.viewmovie.model.Movie

class ListMovieAdapter(
    private val movies: List<Movie>,
    private val onItemClickListener: (Movie) -> Unit
) : RecyclerView.Adapter<ListMovieAdapter.ViewHolder>() {

    private val IMG_BASE_URL = "https://image.tmdb.org/t/p/w200"

    inner class ViewHolder(private val binding: RecyclerItemviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var movie: Movie? = null

        init {
            binding.imgView.setOnClickListener {
                movie?.let { onItemClickListener(it) }
            }
        }

        fun bind(movie: Movie) {
            //adicionei para o clique funcionar
            //atribui o objeto movie a variável de instancia
            this.movie = movie
            binding.tvTitle.text = movie.title

            Glide.with(binding.imgView.context)
                .load(IMG_BASE_URL + movie.posterPath)
                .into(binding.imgView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            RecyclerItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
