package lab2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductSelectionTest {

    @Test
    fun selectsAvailableProductsInRecommendationOrder() {
        val keyboard = Product(1, "Keyboard", true)
        val mouse = Product(2, "Mouse", false)
        val monitor = Product(3, "Monitor", true)
        val webcam = Product(4, "Webcam", true)
        val products = listOf(keyboard, mouse, monitor, webcam)

        val result = selectProducts(
            products,
            listOf(3L, 2L, 3L, 99L, 1L, 4L),
            listOf(1L)
        )

        assertEquals(listOf(monitor, webcam), result)
    }

    @Test
    fun returnsEmptyListWhenThereAreNoRecommendations() {
        val products = listOf(Product(1, "Keyboard", true))

        val result = selectProducts(
            products,
            emptyList(),
            emptyList()
        )

        assertEquals(emptyList<Product>(), result)
    }

    @Test
    fun preservesRecommendationOrder() {
        val keyboard = Product(1L, "Keyboard", true)
        val monitor = Product(3L, "Monitor", true)
        val webcam = Product(4L, "Webcam", true)

        val result = selectProducts(
            products = listOf(webcam, keyboard, monitor),
            recommendedIds = listOf(3L, 4L, 1L),
            excludedIds = emptyList()
        )

        assertEquals(listOf(monitor, webcam, keyboard), result)
    }

    @Test
    fun handlesRepeatedRecommendationsAndExclusions() {
        val keyboard = Product(1L, "Keyboard", true)
        val monitor = Product(3L, "Monitor", true)
        val webcam = Product(4L, "Webcam", true)

        val result = selectProducts(
            products = listOf(keyboard, monitor, webcam),
            recommendedIds = listOf(3L, 1L, 3L, 4L, 1L, 4L),
            excludedIds = listOf(1L, 1L, 99L, 99L)
        )

        assertEquals(listOf(monitor, webcam), result)
    }

    @Test
    fun returnsEmptyListWhenCatalogIsEmpty() {
        val result = selectProducts(
            products = emptyList(),
            recommendedIds = listOf(1L, 2L),
            excludedIds = emptyList()
        )

        assertEquals(emptyList<Product>(), result)
    }

    @Test
    fun selectsProductsWhenExclusionsAreEmpty() {
        val keyboard = Product(1L, "Keyboard", true)
        val monitor = Product(3L, "Monitor", true)

        val result = selectProducts(
            products = listOf(keyboard, monitor),
            recommendedIds = listOf(3L, 1L),
            excludedIds = emptyList()
        )

        assertEquals(listOf(monitor, keyboard), result)
    }

    @Test
    fun returnsEmptyListWhenNoProductsQualify() {
        val unavailable = Product(1L, "Keyboard", false)
        val excluded = Product(2L, "Monitor", true)

        val result = selectProducts(
            products = listOf(unavailable, excluded),
            recommendedIds = listOf(1L, 2L, 99L),
            excludedIds = listOf(2L)
        )

        assertEquals(emptyList<Product>(), result)
    }

    @Test
    fun doesNotModifyInputs() {
        val keyboard = Product(1L, "Keyboard", true)
        val monitor = Product(3L, "Monitor", true)

        val products = mutableListOf(keyboard, monitor)
        val recommendedIds = mutableListOf(3L, 1L, 3L, 99L)
        val excludedIds = mutableListOf(1L, 1L)

        val productsBefore = products.toList()
        val recommendationsBefore = recommendedIds.toList()
        val exclusionsBefore = excludedIds.toList()

        val result = selectProducts(
            products,
            recommendedIds,
            excludedIds
        )

        assertEquals(listOf(monitor), result)
        assertEquals(productsBefore, products)
        assertEquals(recommendationsBefore, recommendedIds)
        assertEquals(exclusionsBefore, excludedIds)
    }

    @Test
    fun repeatedCallsAreIndependent() {
        val keyboard = Product(1L, "Keyboard", true)
        val monitor = Product(3L, "Monitor", true)
        val products = listOf(keyboard, monitor)

        val first = selectProducts(
            products,
            listOf(1L, 3L),
            listOf(1L)
        )

        val second = selectProducts(
            products,
            listOf(1L, 3L),
            emptyList()
        )

        val third = selectProducts(
            products,
            listOf(3L),
            emptyList()
        )

        assertEquals(listOf(monitor), first)
        assertEquals(listOf(keyboard, monitor), second)
        assertEquals(listOf(monitor), third)
    }
}