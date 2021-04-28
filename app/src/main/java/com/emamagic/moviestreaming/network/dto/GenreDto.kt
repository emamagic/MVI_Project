package com.emamagic.moviestreaming.network.dto

import com.emamagic.moviestreaming.base.BaseMapper
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.network.response.GenreResponse
import com.emamagic.moviestreaming.network.response.MovieResponse
import com.emamagic.moviestreaming.network.response.SliderListResponse
import com.emamagic.moviestreaming.network.response.SliderResponse
import javax.inject.Inject

class GenreDto @Inject constructor():
    BaseMapper<GenreResponse,GenreEntity>{

    override fun mapFromEntity(entity: GenreResponse): GenreEntity {
        return GenreEntity(
            id = entity.id,
            name = entity.name,
            link_img = entity.link_img,
            imgAddress = ""
        )
    }

    override fun mapToEntity(domainModel: GenreEntity): GenreResponse {
        return GenreResponse(
            id = domainModel.id,
            name = domainModel.name,
            link_img = domainModel.link_img,
        )
    }

    override fun mapFromEntityList(entities: List<GenreResponse>): List<GenreEntity> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<GenreEntity>): List<GenreResponse> {
        return domains.map { mapToEntity(it) }
    }
}