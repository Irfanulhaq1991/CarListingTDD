package com.irfan.auto1

interface IMapper<in I, out O> {
    fun map(input: I): O
}