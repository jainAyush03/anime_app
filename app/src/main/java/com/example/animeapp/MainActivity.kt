package com.example.animeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.adapter.AnimeAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
data class Anime(
    val id: Int,
    val title: String,
    val episodes: Int,
    val rating: Double,
    val posterUrl: String
)

class MainActivity : AppCompatActivity() {

    private val animeList = mutableListOf<Anime>()
    private lateinit var animeAdapter: AnimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        animeAdapter = AnimeAdapter(animeList) { anime ->
            AnimeDetailActivity.start(this, anime.id)
        }
        recyclerView.adapter = animeAdapter

        fetchTopAnime()
    }

    private fun fetchTopAnime() {
        lifecycleScope.launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.jikan.moe/v4/top/anime")
                .build()

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        response.body?.string()?.let { responseBody ->
                            val json = JSONObject(responseBody)
                            val data = json.getJSONArray("data")

                            for (i in 0 until data.length()) {
                                val animeJson = data.getJSONObject(i)
                                val anime = Anime(
                                    id = animeJson.getInt("mal_id"),
                                    title = animeJson.getString("title"),
                                    episodes = animeJson.optInt("episodes", 0),
                                    rating = animeJson.optDouble("score", 0.0),
                                    posterUrl = animeJson.getJSONObject("images").getJSONObject("jpg").getString("image_url")
                                )
                                animeList.add(anime)
                            }
                        }
                    }
                }
            }
            animeAdapter.notifyDataSetChanged()
        }
    }



}