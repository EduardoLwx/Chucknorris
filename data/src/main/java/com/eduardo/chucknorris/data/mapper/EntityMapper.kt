package com.eduardo.chucknorris.data.mapper

interface EntityMapper <E, M> {

    fun fromEntity(e: E): M

    fun toEntity(m: M): E

}