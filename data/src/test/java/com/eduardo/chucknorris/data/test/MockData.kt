package com.eduardo.chucknorris.data.test

import com.eduardo.chucknorris.data.model.JokeCategoryEntity
import com.eduardo.chucknorris.data.model.JokeEntity
import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.model.JokeModel
import java.util.*
import kotlin.random.Random

object MockData {

    fun randomJokeEntity(): JokeEntity {
        val numOfCategories = Random.nextInt(5) + 1
        val categories = List(numOfCategories){
            randomString()
        }

        return JokeEntity(randomString(), categories, randomString(), randomString(), randomString())
    }

    fun randomJokeCategoryEntity(): JokeCategoryEntity {
        return JokeCategoryEntity(UUID.randomUUID().toString())
    }

    fun randomJokeCategoryEntityList(listSize: Int): List<JokeCategoryEntity>{
        return List(listSize){
            randomJokeCategoryEntity()
        }
    }

    fun randomJokeModel(): JokeModel {
        val numOfCategories = Random.nextInt(5) + 1
        val categories = List(numOfCategories){
            randomString()
        }

        return JokeModel(randomString(), categories, randomString(), randomString(), randomString())
    }

    fun randomJokeCategoryModel(): JokeCategoryModel {
        return JokeCategoryModel(UUID.randomUUID().toString())
    }

    fun randomString() = UUID.randomUUID().toString()

}