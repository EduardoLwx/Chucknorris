package com.eduardo.chucknorris.mapper

import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.model.JokeCategory

class JokeCategoryModelMapper : ModelMapper<JokeCategoryModel, JokeCategory> {

    override fun fromModel(m: JokeCategoryModel): JokeCategory {
        return JokeCategory(m.name.capitalize())
    }

    override fun toModel(v: JokeCategory): JokeCategoryModel {
        return JokeCategoryModel(v.name.toLowerCase())
    }
}