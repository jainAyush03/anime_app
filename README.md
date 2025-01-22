This is a simple Android app that leverages the Jikan API to fetch and display popular or top-rated anime series. The app provides a user-friendly interface to explore anime details, including trailers, posters, ratings, and more. It allows users to view a list of anime series on the Home screen and click on any series to get more in-depth information on a separate detail page.
<img width="321" alt="Screenshot 2025-01-23 at 2 38 12 AM" src="https://github.com/user-attachments/assets/1d74c1ef-af05-4d2d-aae4-d7cc3e882a01" />
<img width="325" alt="Screenshot 2025-01-23 at 2 39 06 AM" src="https://github.com/user-attachments/assets/a54f961c-e269-451a-a670-0b30f9f1e338" />

Features:
Anime List Page:

Displays a list of top-rated or popular anime series.
Shows essential information including:
Anime Title
Number of Episodes
Rating (MyAnimeList score)
Poster Image
The list is fetched from the Jikan API's Top Anime endpoint: https://api.jikan.moe/v4/top/anime.
Anime Detail Page:

When an anime is selected, users are taken to a detail page containing:
Video Player to watch the trailer (if available). If no trailer is available, the anime poster image will be displayed instead.
Title
Plot/Synopsis
Genre(s)
Main Cast
Number of Episodes
Rating
This information is fetched from the Jikan API's Anime Details endpoint: https://api.jikan.moe/v4/anime/{anime_id}.
Technologies Used:
Android: Native Android development using Java/Kotlin.
API Integration: Jikan API for fetching anime data.
UI: Modern Android UI components (RecyclerView, ImageView, VideoView).
Asynchronous Data Fetching: Retrofit for handling API calls and displaying data asynchronously.

Installation Instructions:
Clone the repository:
git clone https://github.com/yourusername/anime-viewer-app.git

Open the project in Android Studio.
Build and run the app on an emulator or real device.
Enjoy exploring the world of anime with the Anime Viewer App!
