package lab2

fun selectProducts(
    products: List<Product>,
    recommendedIds: List<Long>,
    excludedIds: List<Long>
): List<Product> {
    val productsById = products.associateBy { it.id }
    val excluded = excludedIds.toSet()
    val addedIds = mutableSetOf<Long>()
    val result = mutableListOf<Product>()

    for (id in recommendedIds) {
        if (id in excluded) continue
        if (id in addedIds) continue

        val p = productsById[id] ?: continue
        if (!p.available) continue
        result.add(p)
        addedIds.add(id)
    }
    return result
}
