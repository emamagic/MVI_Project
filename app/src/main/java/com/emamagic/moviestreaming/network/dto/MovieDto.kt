package com.emamagic.moviestreaming.network.dto

import com.emamagic.moviestreaming.base.BaseMapper
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.network.response.MovieResponse
import com.emamagic.moviestreaming.network.response.SliderListResponse
import com.emamagic.moviestreaming.network.response.SliderResponse
import javax.inject.Inject

class MovieDto @Inject constructor():
    BaseMapper<MovieResponse,MovieEntity>{

    override fun mapFromEntity(entity: MovieResponse): MovieEntity {
        return MovieEntity(
            id = entity.id,
            name = entity.name,
            time = entity.time,
            published = entity.published,
            link_img = entity.link_img,
            director = entity.director,
            rank = entity.rank,
            rate_imdb = entity.rate_imdb,
            category_name = entity.category_name,
            imgAddress = ""
        )
    }

    override fun mapToEntity(domainModel: MovieEntity): MovieResponse {
        return MovieResponse(
            id = domainModel.id,
            name = domainModel.name,
            link_img = domainModel.link_img,
            time = domainModel.time,
            published = domainModel.published,
            director = domainModel.director,
            category_name = domainModel.category_name,
            rank = domainModel.rank,
            rate_imdb = domainModel.rate_imdb
        )
    }

    override fun mapFromEntityList(entities: List<MovieResponse>): List<MovieEntity> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<MovieEntity>): List<MovieResponse> {
        return domains.map { mapToEntity(it) }
    }
}