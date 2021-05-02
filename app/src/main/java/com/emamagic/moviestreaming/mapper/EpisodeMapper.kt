package com.emamagic.moviestreaming.mapper

import com.emamagic.moviestreaming.base.BaseMapper
import com.emamagic.moviestreaming.db.entity.EpisodeEntity
import com.emamagic.moviestreaming.network.dto.EpisodeDto
import javax.inject.Inject

class EpisodeMapper @Inject constructor() : BaseMapper<EpisodeDto, EpisodeEntity> {

    override fun mapFromEntity(entity: EpisodeDto): EpisodeEntity {
        return EpisodeEntity(
            id = entity.id,
            seasonId = entity.seasonId,
            name = entity.name,
            imageLink = entity.imageLink,
            detail = entity.detail,
            time = entity.time,
            videoLink = entity.videoLink
        )
    }

    override fun mapToEntity(domainModel: EpisodeEntity): EpisodeDto {
        return EpisodeDto(
            id = domainModel.id,
            seasonId = domainModel.seasonId,
            name = domainModel.name,
            imageLink = domainModel.imageLink,
            detail = domainModel.detail,
            time = domainModel.time,
            videoLink = domainModel.videoLink
        )
    }

    override fun mapFromEntityList(entities: List<EpisodeDto>): List<EpisodeEntity> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<EpisodeEntity>): List<EpisodeDto> {
        return domains.map { mapToEntity(it) }
    }
}
