package info.ognibeni.recipes.domain.category.usecase

import info.ognibeni.recipes.domain.category.domain.Category
import info.ognibeni.recipes.domain.category.domain.CategoryUuid
import info.ognibeni.recipes.domain.category.exception.CategoryNotFoundException
import info.ognibeni.recipes.domain.category.persistence.CategoryRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class RetrieveCategoryUseCase(private val categoryRepository: CategoryRepository) {

	fun getAllCategories(): List<Category> =
		categoryRepository.findAll()

	fun getCategory(categoryUuid: CategoryUuid): Category =
		try {
			categoryRepository.getReferenceById(categoryUuid.value)
		} catch (ex: EmptyResultDataAccessException) {
			throw CategoryNotFoundException(categoryUuid, ex)
		}
}
