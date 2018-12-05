package com.eduardo.chucknorris.data.mapper

import com.eduardo.chucknorris.data.model.JokeCategoryEntity
import com.eduardo.chucknorris.domain.model.JokeCategoryModel

class JokeCategoryEntityMapper : EntityMapper<JokeCategoryEntity, JokeCategoryModel> {

    override fun fromEntity(e: JokeCategoryEntity): JokeCategoryModel {
        return JokeCategoryModel(e.name)
    }

    override fun toEntity(m: JokeCategoryModel): JokeCategoryEntity {
        return JokeCategoryEntity(m.name)
    }

}