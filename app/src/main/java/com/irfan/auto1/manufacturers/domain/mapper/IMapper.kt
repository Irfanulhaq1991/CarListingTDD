package com.irfan.auto1.manufacturers.domain.mapper

interface IMapper<in I, out O> {
    fun map(input: I): O
}