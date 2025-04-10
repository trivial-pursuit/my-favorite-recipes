package info.ognibeni.recipes.domain.category.domain

import info.ognibeni.recipes.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "categories")
class Category(
	@Column(nullable = false, unique = true)
	val title: CategoryTitle,

	@Column
	val description: CategoryDescription?,

	) : BaseEntity() {

	@Override
	override fun toString(): String =
		toString("id", "title", "description")
}
