package info.ognibeni.recipes.domain.category.rest

import info.ognibeni.recipes.domain.category.api.ApiCategories
import info.ognibeni.recipes.domain.category.api.ApiCategory
import info.ognibeni.recipes.domain.category.api.ApiCategoryCreate
import info.ognibeni.recipes.domain.category.domain.toCategoryUuid
import info.ognibeni.recipes.domain.category.mapping.toApiCategory
import info.ognibeni.recipes.domain.category.mapping.toCategory
import info.ognibeni.recipes.domain.category.usecase.ManageCategoryUseCase
import info.ognibeni.recipes.domain.category.usecase.RetrieveCategoryUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.HttpURLConnection
import java.util.UUID

@Tag(name = "category", description = "All operations regarding categories")
@RestController
@RequestMapping(path = ["/categories"], produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE])
class CategoryController(
	private val retrieveCategoryUseCase: RetrieveCategoryUseCase,
	private val manageCategoryUseCase: ManageCategoryUseCase) {

	@Operation(summary = "Retrieve all available categories")
	@ApiResponses(value = [
		ApiResponse(
			responseCode = HttpURLConnection.HTTP_OK.toString(),
			description = "Categories successfully retrieved",
			content = [Content(
				schema = Schema(implementation = ApiCategories::class),
				mediaType = MediaType.APPLICATION_JSON_VALUE
			)]
		)])
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	fun getAllScores(): ApiCategories =
		retrieveCategoryUseCase
			.getAllCategories()
			.map { it.toApiCategory() }
			.let { ApiCategories(it) }

	@Operation(summary = "Retrieve a specific category")
	@ApiResponses(value = [
		ApiResponse(
			responseCode = HttpURLConnection.HTTP_OK.toString(),
			description = "Category successfully retrieved",
			content = [Content(
				schema = Schema(implementation = ApiCategory::class),
				mediaType = MediaType.APPLICATION_JSON_VALUE
			)]
		),
		ApiResponse(
			responseCode = HttpURLConnection.HTTP_NOT_FOUND.toString(),
			description = "Specific category was not found",
			content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
		)])
	@GetMapping("/{categoryUuid}")
	@ResponseStatus(HttpStatus.OK)
	fun getCategory(
		@PathVariable
		@Parameter(description = "UUID of the category", required = true, example = "00000000-aaaa-4bbb-8ccc-111111111111")
		categoryUuid: UUID): ApiCategory =
		retrieveCategoryUseCase
			.getCategory(categoryUuid.toCategoryUuid())
			.toApiCategory()

	@Operation(summary = "Add a new category")
	@ApiResponses(value = [
		ApiResponse(
			responseCode = HttpURLConnection.HTTP_CREATED.toString(),
			description = "Category successfully created",
			content = [Content(
				schema = Schema(implementation = ApiCategory::class),
				mediaType = MediaType.APPLICATION_JSON_VALUE
			)]
		)])
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun addCategory(
		@RequestBody
		apiCategoryCreate: ApiCategoryCreate): ApiCategory =
		manageCategoryUseCase
			.createCategory(apiCategoryCreate.toCategory())
			.toApiCategory()
}
