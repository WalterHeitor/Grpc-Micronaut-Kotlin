package br.com.softwalter.controller.dto

data class ProductResponse(
        val productId: Long,
        val name: String,
        val price: Double,
        val quantityInStock: Int,
)
