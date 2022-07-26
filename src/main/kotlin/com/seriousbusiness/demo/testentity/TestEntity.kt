package com.seriousbusiness.demo.testentity

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "test_entity")
open class TestEntity(

    @Id
    open var id: UUID?,
    open var documentId: UUID?,
    open var documentDate: Instant?,
    open var dictionaryValueId: UUID?,
    open var dictionaryValueName: String?,
    @Enumerated(value = EnumType.STRING)
    open var sortOrder: SortOrder,
    open var testId: UUID?,
    open var testName: String,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TestEntity

        if (id != other.id) return false
        if (documentId != other.documentId) return false
        if (documentDate != other.documentDate) return false
        if (dictionaryValueId != other.dictionaryValueId) return false
        if (dictionaryValueName != other.dictionaryValueName) return false
        if (sortOrder != other.sortOrder) return false
        if (testId != other.testId) return false
        if (testName != other.testName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (documentId?.hashCode() ?: 0)
        result = 31 * result + (documentDate?.hashCode() ?: 0)
        result = 31 * result + (dictionaryValueId?.hashCode() ?: 0)
        result = 31 * result + (dictionaryValueName?.hashCode() ?: 0)
        result = 31 * result + sortOrder.hashCode()
        result = 31 * result + (testId?.hashCode() ?: 0)
        result = 31 * result + testName.hashCode()
        return result
    }
}

enum class SortOrder {
    ASC, DESC, NONE
}