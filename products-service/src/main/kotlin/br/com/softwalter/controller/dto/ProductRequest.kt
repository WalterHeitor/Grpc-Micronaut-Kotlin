package br.com.softwalter.controller.dto

data class ProductRequest(
        val name: String,
        val price: Double,
        val quantityInStock: Int,
)
