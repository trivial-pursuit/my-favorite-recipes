package info.ognibeni.recipes.persistence

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Version
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private val id: UUID
) : Persistable<UUID> {

	/** Creation timestamp, set automatically by [AuditingEntityListener]. */
	@CreatedDate
	@Column(nullable = false, updatable = false)
	var createdAt: LocalDateTime? = null

	/** Timestamp of last modification, set automatically by [AuditingEntityListener]. */
	@LastModifiedDate
	@Column(nullable = false)
	var modifiedAt: LocalDateTime? = null

	@Version
	@Column(nullable = false)
	private val version: Int? = null

	override fun getId(): UUID = id

	override fun isNew(): Boolean =
		version == null

	// See details here https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (!javaClass.isInstance(other)) return false
		other as BaseEntity

		return id == other.id
	}

	override fun hashCode(): Int = javaClass.hashCode()

	// TODO: Crazy reflection magic setting accessibility always to true: should be refactored
	fun toString(vararg properties: String): String {
		val propertyPairs = this::class.declaredMemberProperties
			.filter { properties.contains(it.name) }
			.onEach { it.isAccessible = true }
			.joinToString { it.name + " = " + it.getter.call(this).toString() }
		return this::class.simpleName + "(id = $id, $propertyPairs)"
	}

	companion object {
		val NEW_ENTITY_ID: UUID = UUID.fromString("0000000-0000-0000-0000-000000000000")
	}
}
