package com.eduardo.chucknorris.data.mapper

import com.eduardo.chucknorris.data.model.JokeEntity
import com.eduardo.chucknorris.domain.model.JokeModel

class JokeEntityMapper : EntityMapper<JokeEntity, JokeModel> {

    override fun fromEntity(e: JokeEntity): JokeModel {
        return JokeModel(e.id, e.categories ?: emptyList(), e.iconUrl, e.value, e.url)
    }

    override fun toEntity(m: JokeModel): JokeEntity {
        return JokeEntity(m.id, m.categories, m.iconUrl, m.value, m.url)
    }

}