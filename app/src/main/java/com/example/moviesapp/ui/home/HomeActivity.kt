package com.example.moviesapp.ui.home

import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.MyApp
import com.example.moviesapp.adapters.FilmAdapter
import com.example.moviesapp.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val filmApi = MyApp.instance.appComponent.film()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = FilmAdapter()


        /*
        При создании этой Activity будет выполнен запрос для получения списка фильмов.
        */
        CoroutineScope(Dispatchers.IO).launch {
            val response = filmApi.getAllFilms()

            this.launch(Dispatchers.Main) {
                response.body()?.let {
                    binding.circularProgressIndicator.visibility = GONE
                    adapter.setList(it.results)
                    binding.recyclerViewFilms.layoutManager = layoutManager
                    binding.recyclerViewFilms.adapter = adapter
                }
            }
        }
    }
}