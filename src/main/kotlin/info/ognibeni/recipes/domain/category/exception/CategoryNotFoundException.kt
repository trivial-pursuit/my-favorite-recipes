package info.ognibeni.recipes.domain.category.exception

import info.ognibeni.recipes.domain.category.domain.CategoryUuid
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponseException
import java.net.URI

class CategoryNotFoundException(categoryUuid: CategoryUuid, cause: Throwable? = null) : ErrorResponseException(
	HttpStatus.NOT_FOUND,
	ProblemDetail.forStatus(HttpStatus.NOT_FOUND).apply {
		type = URI.create("problem:CATEGORY_NOT_FOUND")
		title = "Category not found"
		detail = "Category with UUID $categoryUuid not found"
	},
	cause
)
