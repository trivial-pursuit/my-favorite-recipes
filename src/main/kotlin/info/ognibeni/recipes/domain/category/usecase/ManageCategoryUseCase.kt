package info.ognibeni.recipes.domain.category.usecase

import info.ognibeni.recipes.domain.category.domain.Category
import info.ognibeni.recipes.domain.category.persistence.CategoryRepository
import org.springframework.stereotype.Service

@Service
class ManageCategoryUseCase(private val categoryRepository: CategoryRepository) {

	fun createCategory(category: Category): Category =
		categoryRepository.save(category)

	fun updateCategory(category: Category): Category =
		categoryRepository.save(category)
}
