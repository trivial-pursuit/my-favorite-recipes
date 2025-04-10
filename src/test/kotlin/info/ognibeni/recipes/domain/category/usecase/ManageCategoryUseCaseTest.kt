package info.ognibeni.recipes.domain.category.usecase

import info.ognibeni.recipes.domain.category.Fixtures.exampleCategory
import info.ognibeni.recipes.domain.category.persistence.CategoryRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ManageCategoryUseCaseTest {

	private val categoryRepository: CategoryRepository = mockk()

	private val sut = ManageCategoryUseCase(categoryRepository)

	@Test
	fun `creating category succeeds`() {
		val expectedCategory = exampleCategory()

		every { categoryRepository.save(any()) } returns expectedCategory

		val category = sut.createCategory(expectedCategory)

		verify(exactly = 1) { categoryRepository.save(any()) }
		assertThat(category).isEqualTo(expectedCategory)
	}

	@Test
	fun `updating category succeeds`() {
		val expectedCategory = exampleCategory()

		every { categoryRepository.save(any()) } returns expectedCategory

		val category = sut.updateCategory(expectedCategory)

		verify(exactly = 1) { categoryRepository.save(any()) }
		assertThat(category).isEqualTo(expectedCategory)
	}
}
