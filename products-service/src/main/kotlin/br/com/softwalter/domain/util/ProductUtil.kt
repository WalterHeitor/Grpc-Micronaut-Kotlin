package br.com.softwalter.domain.util

import br.com.softwalter.controller.dto.ProductRequest
import br.com.softwalter.controller.dto.ProductResponse
import br.com.softwalter.domain.entities.Product

fun Product.toProductResponse() : ProductResponse {

    return ProductResponse(
            productId!!,
            name,
            price,
            quantityInStock)
}

fun ProductRequest.toDomain() : Product {
    return Product(null, name, price, quantityInStock)
}