package com.eduardo.chucknorris.data.mapper

import com.eduardo.chucknorris.data.model.JokeEntity
import com.eduardo.chucknorris.data.test.MockData
import com.eduardo.chucknorris.domain.model.JokeModel
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class JokeEntityMapperTest {

    private lateinit var jokeEntityMapper: JokeEntityMapper

    @Before
    fun setUp() {
        jokeEntityMapper = JokeEntityMapper()
    }

    @Test
    fun fromEntity_return() {
        val jokeEntity = MockData.randomJokeEntity()
        val jokeModel = jokeEntityMapper.fromEntity(jokeEntity)
        compare(jokeModel, jokeEntity)
    }

    @Test
    fun toEntity_return() {
        val jokeModel = MockData.randomJokeModel()
        val jokeEntity = jokeEntityMapper.toEntity(jokeModel)
        compare(jokeModel, jokeEntity)
    }

    private inline fun compare(jokeModel: JokeModel, jokeEntity: JokeEntity){
        assertEquals(jokeModel.categories, jokeEntity.categories)
        assertEquals(jokeModel.iconUrl, jokeEntity.iconUrl)
        assertEquals(jokeModel.id, jokeEntity.id)
        assertEquals(jokeModel.url, jokeEntity.url)
        assertEquals(jokeModel.value, jokeEntity.value)
    }
}