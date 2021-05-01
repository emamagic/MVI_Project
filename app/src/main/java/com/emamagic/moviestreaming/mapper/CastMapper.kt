package com.emamagic.moviestreaming.mapper

import com.emamagic.moviestreaming.base.BaseMapper
import com.emamagic.moviestreaming.db.entity.CastEntity
import com.emamagic.moviestreaming.network.dto.CastDto
import javax.inject.Inject

class CastMapper @Inject constructor(): BaseMapper<CastDto ,CastEntity> {

    override fun mapFromEntity(entity: CastDto): CastEntity {
        return CastEntity(
            id = entity.id,
            itemId = entity.itemId,
            name = entity.name,
            imageLink = entity.imageLink
        )
    }

    override fun mapToEntity(domainModel: CastEntity): CastDto {
        return CastDto(
            id = domainModel.id,
            itemId = domainModel.itemId,
            name = domainModel.name,
            imageLink = domainModel.imageLink
        )
    }

    override fun mapFromEntityList(entities: List<CastDto>): List<CastEntity> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<CastEntity>): List<CastDto> {
        return domains.map { mapToEntity(it) }
    }
}