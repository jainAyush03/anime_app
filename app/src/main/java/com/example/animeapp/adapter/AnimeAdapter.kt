package com.example.animeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeapp.Anime
import com.example.animeapp.R

class AnimeAdapter(
    private val animeList: List<Anime>,
    private val onAnimeClick: (Anime) -> Unit
) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val posterImageView: ImageView = view.findViewById(R.id.posterImageView)
        private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        private val episodesTextView: TextView = view.findViewById(R.id.episodesTextView)
        private val ratingTextView: TextView = view.findViewById(R.id.ratingTextView)

        fun bind(anime: Anime) {
            titleTextView.text = anime.title
            episodesTextView.text = "Episodes: ${anime.episodes}"
            ratingTextView.text = "Rating: ${anime.rating}"
            Glide.with(posterImageView.context)
                .load(anime.posterUrl)
                .into(posterImageView)

            itemView.setOnClickListener { onAnimeClick(anime) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    override fun getItemCount(): Int = animeList.size
}
