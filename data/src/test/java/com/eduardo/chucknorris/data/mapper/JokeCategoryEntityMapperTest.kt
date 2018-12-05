package com.eduardo.chucknorris.data.mapper

import com.eduardo.chucknorris.data.model.JokeCategoryEntity
import com.eduardo.chucknorris.data.test.MockData
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class JokeCategoryEntityMapperTest {

    private lateinit var jokeCategoryEntityMapper: JokeCategoryEntityMapper

    @Before
    fun setUp() {
        jokeCategoryEntityMapper = JokeCategoryEntityMapper()
    }

    @Test
    fun fromEntity_return() {
        val jokeCategoryEntity = MockData.randomJokeCategoryEntity()
        val jokeCategoryModel = jokeCategoryEntityMapper.fromEntity(jokeCategoryEntity)
        compare(jokeCategoryModel, jokeCategoryEntity)
    }

    @Test
    fun toEntity_return() {
        val jokeCategoryModel = MockData.randomJokeCategoryModel()
        val jokeCategoryEntity = jokeCategoryEntityMapper.toEntity(jokeCategoryModel)
        compare(jokeCategoryModel, jokeCategoryEntity)
    }

    private inline fun compare(jokeCategoryModel: JokeCategoryModel, jokeCategoryEntity: JokeCategoryEntity) {
        assertEquals(jokeCategoryModel.name, jokeCategoryEntity.name)
    }
}