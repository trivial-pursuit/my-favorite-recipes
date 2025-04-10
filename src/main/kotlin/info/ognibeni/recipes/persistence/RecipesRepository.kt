package info.ognibeni.recipes.persistence

import info.ognibeni.recipes.persistence.BaseEntity.Companion.NEW_ENTITY_ID
import jakarta.persistence.EntityManager
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.transaction.annotation.Transactional

class RecipesRepository<E : BaseEntity, ID>(
	val entityInformation: JpaEntityInformation<E, ID>,
	val entityManager: EntityManager
) : SimpleJpaRepository<E, ID>(entityInformation, entityManager) {

	@Transactional
	override fun <S : E> save(entity: S): S {
		if (entityInformation.isNew(entity)) {
			require(entity.id == NEW_ENTITY_ID) { "Entity ID must be empty." }
			entityManager.persist(entity)
			return entity
		} else {
			require(entity.id != NEW_ENTITY_ID) { "Entity ID must not be empty." }
			return entityManager.merge(entity)
		}
	}
}
