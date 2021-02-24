package com.mgleez.tradesylistyelptest.utils

/**
 * An interface for mapping from an entity to a domain model and the reverse. Supplies list mapping.
 *
 * Created by Mike Margulies 20210224
 */
interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
    fun mapToEntity(domainModel: DomainModel): Entity
    fun mapFromEntityList(entities: List<Entity>): List<DomainModel> =
        entities.map { mapFromEntity(it) }
    fun mapToEntityList(entities: List<DomainModel>): List<Entity> =
        entities.map { mapToEntity(it) }
}