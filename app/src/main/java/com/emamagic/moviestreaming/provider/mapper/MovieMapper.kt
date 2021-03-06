package com.emamagic.moviestreaming.provider.mapper

import com.emamagic.moviestreaming.ui.base.BaseMapper
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.data.network.dto.MovieDto
import javax.inject.Inject

class MovieMapper @Inject constructor():
    BaseMapper<MovieDto, MovieEntity> {

    override fun mapFromEntity(entity: MovieDto): MovieEntity {
        return MovieEntity(
            id = entity.id,
            name = entity.name,
            time = entity.time,
            published = entity.published,
            imageLink = entity.imageLink,
            director = entity.director,
            rank = entity.rank,
            imdbRate = entity.imdbRate,
            categoryName = entity.categoryName,
            episode = entity.episode,
            imageAddress = "",
            genreName = entity.genreName
        )
    }

    override fun mapToEntity(domainModel: MovieEntity): MovieDto {
        return MovieDto(
            id = domainModel.id,
            name = domainModel.name,
            imageLink = domainModel.imageLink ?: "",
            time = domainModel.time,
            published = domainModel.published,
            director = domainModel.director,
            categoryName = domainModel.categoryName,
            rank = domainModel.rank,
            episode = domainModel.episode,
            imdbRate = domainModel.imdbRate,
            genreName = domainModel.genreName
        )
    }

    override fun mapFromEntityList(entities: List<MovieDto>): List<MovieEntity> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<MovieEntity>): List<MovieDto> {
        return domains.map { mapToEntity(it) }
    }
}