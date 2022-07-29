package com.example.moviesapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesapp.MyApp
import com.example.moviesapp.R
import com.example.moviesapp.network.models.FilmDC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmAdapter : RecyclerView.Adapter<FilmAdapter.MainHolder>() {

    // этот лист содержит список фильмов.
    private lateinit var mListMovies: List<FilmDC>

    class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moviePoster: ImageView = view.findViewById(R.id.imageViewFilm)
        val movieTitle: TextView = view.findViewById(R.id.textViewTitle)
        val movieDescription: TextView = view.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_film_recycler, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.moviePoster.load(mListMovies[position].multimedia.src)
        holder.movieTitle.text = mListMovies[position].display_title
        holder.movieDescription.text = mListMovies[position].summary_short

        /*
        * Если при скроле элемент, который отрисовывается заранее будет равен последнему, тогда выполнится запрос на получение новых фильмов.
        */
        if (position == mListMovies.size - 1){
            CoroutineScope(Dispatchers.IO).launch {
                val response = MyApp.instance.appComponent.film().getOffsetFilms(offset = mListMovies.size)

                this.launch(Dispatchers.Main){
                    response.body()?.let {
                        mListMovies += it.results
                        notifyDataSetChanged()
                        Toast.makeText(MyApp.instance.applicationContext, "Еще 20 фильмов подгружено", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = mListMovies.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<FilmDC>) {
        mListMovies = list
        notifyDataSetChanged()
    }
}