package com.emamagic.moviestreaming.provider.mapper

import com.emamagic.moviestreaming.ui.base.BaseMapper
import com.emamagic.moviestreaming.data.db.entity.SeasonEntity
import com.emamagic.moviestreaming.data.network.dto.SeasonDto
import javax.inject.Inject

class SeasonMapper @Inject constructor() : BaseMapper<SeasonEntity, SeasonDto> {

    override fun mapFromEntity(entity: SeasonEntity): SeasonDto {
        return SeasonDto(
            id = entity.id,
            itemId = entity.itemId,
            season = entity.season,
            episode = entity.episode,
            imageLink = entity.imageLink
        )
    }

    override fun mapToEntity(domainModel: SeasonDto): SeasonEntity {
        return SeasonEntity(
            id = domainModel.id,
            itemId = domainModel.itemId,
            season = domainModel.season,
            episode = domainModel.episode,
            imageLink = domainModel.imageLink
        )
    }

    override fun mapFromEntityList(entities: List<SeasonEntity>): List<SeasonDto> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<SeasonDto>): List<SeasonEntity> {
        return domains.map { mapToEntity(it) }
    }
}