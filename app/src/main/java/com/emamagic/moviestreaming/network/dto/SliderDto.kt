package com.emamagic.moviestreaming.network.dto

import com.emamagic.moviestreaming.base.BaseMapper
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.network.response.SliderListResponse
import com.emamagic.moviestreaming.network.response.SliderResponse
import javax.inject.Inject

class SliderDto @Inject constructor():
    BaseMapper<SliderResponse,SliderEntity>{

    override fun mapFromEntity(entity: SliderResponse): SliderEntity {
        return SliderEntity(
            id = entity.id,
            name = entity.name,
            imgAddress = null,
            time = entity.time,
            published = entity.published
        )
    }

    override fun mapToEntity(domainModel: SliderEntity): SliderResponse {
        return SliderResponse(
            id = domainModel.id,
            name = domainModel.name,
            link_img = null,
            time = domainModel.time,
            published = domainModel.published
        )
    }

    override fun mapFromEntityList(entities: List<SliderResponse>): List<SliderEntity> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<SliderEntity>): List<SliderResponse> {
        return domains.map { mapToEntity(it) }
    }
}