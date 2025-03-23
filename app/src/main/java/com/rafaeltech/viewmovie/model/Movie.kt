package com.rafaeltech.viewmovie.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movie(
    //converte json para dataclass
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    //val posterUrl: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("overview")
    val overView: String,
    @SerializedName("backdrop_path")
    val backDropPath: String
    ): Parcelable

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPage: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
/*
{
    "page": 1,
    "results": [

    {
        "adult": false,
        "backdrop_path": null,
        "genre_ids": [
        99
        ],
        "id": 1434009,
        "original_language": "kk",
        "original_title": "We Live Here",
        "overview": "",
        "popularity": 22.446,
        "poster_path": "/uXhG2CGyP2hIUXoD7Yh0sl5uXft.jpg",
        "release_date": "2025-03-19",
        "title": "We Live Here",
        "video": false,
        "vote_average": 0,
        "vote_count": 0
    }
    ],
    "total_pages": 49290,
    "total_results": 985787
}
*/
