package com.example.animeapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class AnimeDetailActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    companion object {
        fun start(context: Context, animeId: Int) {
            val intent = Intent(context, AnimeDetailActivity::class.java)
            intent.putExtra("ANIME_ID", animeId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_detail)

        webView = findViewById(R.id.webView)
        setupWebView()

        val animeId = intent.getIntExtra("ANIME_ID", 52991)
        fetchAnimeDetails(animeId)
    }

    private fun setupWebView() {
        webView.webViewClient = WebViewClient()
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
    }

    private fun fetchAnimeDetails(animeId: Int) {
        lifecycleScope.launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.jikan.moe/v4/anime/$animeId")
                .build()

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        response.body?.string()?.let { responseBody ->
                            val json = JSONObject(responseBody)
                            val anime = json.getJSONObject("data")

                            val title = anime.getString("title")
                            val synopsis = anime.getString("synopsis")
                            val genres = anime.getJSONArray("genres")
                            val genreList = (0 until genres.length()).joinToString { genres.getJSONObject(it).getString("name") }
                            val episodes = anime.optInt("episodes", 0)
                            val rating = anime.optDouble("score", 0.0)
                            val trailerUrl = anime.getJSONObject("trailer").optString("embed_url", "https://www.youtube.com/embed/ZEkwCGJ3o7M?enablejsapi=1&wmode=opaque&autoplay=1")
                            val posterUrl = anime.getJSONObject("images").getJSONObject("jpg").getString("image_url")

                            withContext(Dispatchers.Main) {
                                findViewById<TextView>(R.id.titleTextView).text = title
                                findViewById<TextView>(R.id.synopsisTextView).text = synopsis
                                findViewById<TextView>(R.id.genresTextView).text = genreList
                                findViewById<TextView>(R.id.episodesTextView).text = "Episodes: $episodes"
                                findViewById<TextView>(R.id.ratingTextView).text = "Rating: $rating"

                                val posterImageView: ImageView = findViewById(R.id.posterImageView)
                                Glide.with(this@AnimeDetailActivity).load(posterUrl).into(posterImageView)

                                if (trailerUrl != null) {
                                    findViewById<ImageView>(R.id.posterImageView).visibility = ImageView.GONE
                                    webView.visibility = View.VISIBLE
                                    webView.loadUrl(trailerUrl)
                                } else {
                                    findViewById<ImageView>(R.id.posterImageView).visibility = ImageView.VISIBLE
                                    webView.visibility = View.GONE
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}