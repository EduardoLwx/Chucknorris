package com.eduardo.chucknorris.domain.model

data class JokeModel (var id: String, val categories: List<String>, var iconUrl: String, var value: String,
                      var url: String)
