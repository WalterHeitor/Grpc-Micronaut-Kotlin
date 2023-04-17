package br.com.softwalter.template

import br.com.softwalter.ProductsServiceRequest
import br.com.softwalter.controller.dto.ProductRequest
import br.com.softwalter.domain.entities.Product

object ProductMockFactory {

    fun createProductInput() : Product {
        return Product(null, "Coca cola", 12.00, 30 )
    }

    fun createProductOutput(): Product {
        return Product(1, "Coca cola", 12.00, 30 )
    }

    fun createProductRequest(): ProductRequest {
        return ProductRequest("Coca cola", 12.00, 30)
    }

    fun createProductServiceRequest(): ProductsServiceRequest? {
        return ProductsServiceRequest.newBuilder()
                .setName("Coca cola")
                .setPrice(12.97)
                .setQuantityInStock(14)
                .build()
    }
}