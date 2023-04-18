package br.com.softwalter.domain.service

import br.com.softwalter.controller.dto.ProductRequest
import br.com.softwalter.controller.dto.ProductResponse

interface ProductService {

    fun createProduct(request: ProductRequest): ProductResponse
    fun findById(productId: Long): ProductResponse
}