package com.eduardo.chucknorris.mapper

interface ModelMapper <M, V> {

    fun fromModel(m: M): V

    fun toModel(v: V): M

}