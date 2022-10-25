package com.irfan.auto1.manufactureres.domain.mapper

interface IMapper<in I, out O> {
    fun map(input: I): O
}