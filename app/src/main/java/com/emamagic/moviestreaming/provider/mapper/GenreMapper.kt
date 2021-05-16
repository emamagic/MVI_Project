package com.emamagic.moviestreaming.provider.mapper

import com.emamagic.moviestreaming.ui.base.BaseMapper
import com.emamagic.moviestreaming.data.db.entity.GenreEntity
import com.emamagic.moviestreaming.data.network.dto.GenreDto
import javax.inject.Inject

class GenreMapper @Inject constructor():
    BaseMapper<GenreDto, GenreEntity> {

    override fun mapFromEntity(entity: GenreDto): GenreEntity {
        return GenreEntity(
            id = entity.id,
            name = entity.name,
            imageLing = entity.imageLink,
            imageAddress = ""
        )
    }

    override fun mapToEntity(domainModel: GenreEntity): GenreDto {
        return GenreDto(
            id = domainModel.id,
            name = domainModel.name,
            imageLink = domainModel.imageLing,
        )
    }

    override fun mapFromEntityList(entities: List<GenreDto>): List<GenreEntity> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<GenreEntity>): List<GenreDto> {
        return domains.map { mapToEntity(it) }
    }
}