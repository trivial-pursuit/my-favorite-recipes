package info.ognibeni.recipes.domain.category.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import info.ognibeni.recipes.domain.category.Fixtures.EXAMPLE_CATEGORY_UUID
import info.ognibeni.recipes.domain.category.Fixtures.exampleApiCategoryCreate
import info.ognibeni.recipes.domain.category.Fixtures.exampleCategory
import info.ognibeni.recipes.domain.category.domain.Category
import info.ognibeni.recipes.domain.category.domain.CategoryUuid
import info.ognibeni.recipes.domain.category.exception.CategoryNotFoundException
import info.ognibeni.recipes.domain.category.mapping.toApiCategory
import info.ognibeni.recipes.domain.category.rest.CategoryController
import info.ognibeni.recipes.domain.category.usecase.ManageCategoryUseCase
import info.ognibeni.recipes.domain.category.usecase.RetrieveCategoryUseCase
import io.mockk.every
import io.mockk.verify
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(CategoryController::class)
class CategoryControllerTest(@Autowired private val mockMvc: MockMvc,
                             @Autowired private val objectMapper: ObjectMapper) {

	@MockkBean
	lateinit var retrieveCategoryUseCase: RetrieveCategoryUseCase

	@MockkBean
	lateinit var manageCategoryUseCase: ManageCategoryUseCase

	@Nested
	inner class retrieve {
		@Test
		fun `multiple categories succeeds`() {
			val exampleCategories = listOf(
				exampleCategory(),
				exampleCategory(title = "another title", description = null)
			)
			val expectedApiCategories =
				ApiCategories(exampleCategories.map { it.toApiCategory() })

			every { retrieveCategoryUseCase.getAllCategories() } returns exampleCategories

			mockMvc.getAllCategories()
				.andExpect { successfulResponse(expectedApiCategories) }
		}

		@Test
		fun `empty category list succeeds`() {
			val exampleCategories = emptyList<Category>()
			val expectedApiCategories =
				ApiCategories(exampleCategories.map { it.toApiCategory() })

			every { retrieveCategoryUseCase.getAllCategories() } returns exampleCategories

			mockMvc.getAllCategories()
				.andExpect { successfulResponse(expectedApiCategories) }
		}

		@Test
		fun `existing category succeeds`() {
			val categoryUuid = EXAMPLE_CATEGORY_UUID
			val exampleCategory = exampleCategory()
			val expectedApiCategory = exampleCategory.toApiCategory()

			every { retrieveCategoryUseCase.getCategory(any()) } returns exampleCategory

			mockMvc.performGetCategory(categoryUuid)
				.andExpect { successfulResponse(expectedApiCategory) }

			verify(exactly = 1) { retrieveCategoryUseCase.getCategory(categoryUuid) }
		}

		@Test
		fun `non-existing category fails`() {
			val categoryUuid = EXAMPLE_CATEGORY_UUID
			every { retrieveCategoryUseCase.getCategory(any()) } throws CategoryNotFoundException(categoryUuid)

			mockMvc.performGetCategory(categoryUuid)
				.andExpect {
					status { isNotFound() }
					content { contentType(MediaType.APPLICATION_PROBLEM_JSON) }
					jsonPath("$.type") { value("problem:CATEGORY_NOT_FOUND") }
					jsonPath("$.detail") { value(containsString(categoryUuid.value.toString())) }
				}

			verify(exactly = 1) { retrieveCategoryUseCase.getCategory(categoryUuid) }
		}
	}

	@Nested
	inner class create {

		@Test
		fun `category succeeds`() {
			val exampleApiCategoryCreate = exampleApiCategoryCreate()
			val expectedCategory = exampleCategory()
			val expectedApiCategory = expectedCategory.toApiCategory()

			every { manageCategoryUseCase.createCategory(any()) } answers { firstArg<Category>() }

			mockMvc.createCategory(exampleApiCategoryCreate)
				.andExpect {
					status { isCreated() }
					content { contentType(MediaType.APPLICATION_JSON) }
					content { json(objectMapper.writeValueAsString(expectedApiCategory)) }
				}

			verify(exactly = 1) { manageCategoryUseCase.createCategory(expectedCategory) }
		}
	}

	private fun MockMvc.getAllCategories() =
		this.get("/categories")

	private fun MockMvc.performGetCategory(categoryUuid: CategoryUuid) =
		this.get("/categories/${categoryUuid.value}")

	private fun MockMvc.createCategory(category: ApiCategoryCreate) =
		this.post("/categories") {
			contentType = MediaType.APPLICATION_JSON
			content = objectMapper.writeValueAsString(category)
		}

	private fun MockMvcResultMatchersDsl.successfulResponse(expected: Any) {
		status { isOk() }
		content { contentType(MediaType.APPLICATION_JSON) }
		content { json(objectMapper.writeValueAsString(expected)) }
	}
}
