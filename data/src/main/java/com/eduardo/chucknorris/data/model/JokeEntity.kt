package com.eduardo.chucknorris.data.model

import com.google.gson.annotations.SerializedName

data class JokeEntity (
    @SerializedName("id")
    val id: String,

    @SerializedName("category")
    val categories: List<String>?,

    @SerializedName("icon_url")
    val iconUrl: String,

    @SerializedName("value")
    val value: String,

    @SerializedName("url")
    val url: String
)