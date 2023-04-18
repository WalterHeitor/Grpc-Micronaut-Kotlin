package br.com.softwalter.domain.service

import br.com.softwalter.controller.dto.ProductRequest
import br.com.softwalter.controller.dto.ProductResponse
import br.com.softwalter.controller.dto.ProductUpdateRequest

interface ProductService {

    fun createProduct(request: ProductRequest): ProductResponse
    fun findById(productId: Long): ProductResponse
    fun updateProduct(requestUpdate: ProductUpdateRequest): ProductResponse
    fun deleteProductById(byIdRequest: Long)
    fun findAll(): List<ProductResponse>
}
