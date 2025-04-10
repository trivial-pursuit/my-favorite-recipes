package info.ognibeni.recipes.domain.category.persistence

import info.ognibeni.recipes.domain.category.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CategoryRepository : JpaRepository<Category, UUID>
