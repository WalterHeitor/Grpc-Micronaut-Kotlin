package br.com.softwalter.controller.dto

class ProductUpdateRequest(
        val productId: Long,
        val name: String,
        val price: Double,
        val quantityInStock: Int,
)
