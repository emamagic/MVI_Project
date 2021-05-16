package com.emamagic.moviestreaming.provider.mapper

import com.emamagic.moviestreaming.ui.base.BaseMapper
import com.emamagic.moviestreaming.data.db.entity.SliderEntity
import com.emamagic.moviestreaming.data.network.dto.SliderDto
import javax.inject.Inject

class SliderMapper @Inject constructor():
    BaseMapper<SliderDto, SliderEntity> {

    override fun mapFromEntity(entity: SliderDto): SliderEntity {
        return SliderEntity(
            id = entity.id,
            name = entity.name,
            time = entity.time,
            published = entity.published,
            imageLink = entity.imageLink,
            imageAddress = null
        )
    }

    override fun mapToEntity(domainModel: SliderEntity): SliderDto {
        return SliderDto(
            id = domainModel.id,
            name = domainModel.name,
            imageLink = domainModel.imageLink,
            time = domainModel.time,
            published = domainModel.published
        )
    }

    override fun mapFromEntityList(entities: List<SliderDto>): List<SliderEntity> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<SliderEntity>): List<SliderDto> {
        return domains.map { mapToEntity(it) }
    }
}