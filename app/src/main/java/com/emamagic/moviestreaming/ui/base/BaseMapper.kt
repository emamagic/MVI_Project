package com.emamagic.moviestreaming.ui.base

interface BaseMapper<A,B> {

    fun mapFromEntity(entity: A): B
    fun mapToEntity(domainModel: B): A
    fun mapFromEntityList(entities: List<A>): List<B>
    fun mapToEntityList(domains: List<B>): List<A>
}