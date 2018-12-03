package com.eduardo.chucknorris.domain.test

import com.eduardo.chucknorris.domain.model.JokeCategoryModel
import com.eduardo.chucknorris.domain.model.JokeModel
import java.util.*
import kotlin.random.Random

object MockData {

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

    private fun randomString() = UUID.randomUUID().toString()
}