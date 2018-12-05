package com.eduardo.chucknorris.mapper

import com.eduardo.chucknorris.domain.model.JokeModel
import com.eduardo.chucknorris.model.Joke

class JokeModelMapper : ModelMapper<JokeModel, Joke> {
    override fun fromModel(m: JokeModel): Joke {
        return Joke(m.id, m.categories, m.iconUrl, m.value, m.url)
    }

    override fun toModel(v: Joke): JokeModel {
        return  JokeModel(v.id, v.categories, v.iconUrl, v.value, v.url)
    }
}
