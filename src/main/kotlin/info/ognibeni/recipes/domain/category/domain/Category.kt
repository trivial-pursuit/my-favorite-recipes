package info.ognibeni.recipes.domain.category.domain

import info.ognibeni.recipes.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Transient

@Entity
@Table(name = "categories")
class Category(
	@Column(nullable = false, unique = true)
	val title: CategoryTitle,

	@Column
	val description: CategoryDescription?,

	@Transient
	val uuid: CategoryUuid = CategoryUuid(NEW_ENTITY_ID),

	) : BaseEntity(uuid.value) {

	@Override
	override fun toString(): String =
		toString("title", "description")
}
