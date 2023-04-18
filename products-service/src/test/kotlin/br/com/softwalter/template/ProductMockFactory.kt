package br.com.softwalter.template

import br.com.softwalter.ProductsServiceRequest
import br.com.softwalter.ProductsServiceUpdateRequest
import br.com.softwalter.controller.dto.ProductRequest
import br.com.softwalter.controller.dto.ProductUpdateRequest
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

    fun createProductServiceUpdateRequest(): ProductsServiceUpdateRequest? {
        return ProductsServiceUpdateRequest.newBuilder()
                .setProductId(1L)
                .setName("Fanta Laranja")
                .setPrice(18.93)
                .setQuantityInStock(25)
                .build()
    }

    fun createProductUpdateRequest(): ProductUpdateRequest {
        return ProductUpdateRequest(
                productId = 1L,
                name = "Coca cola",
                price = 18.93,
                quantityInStock = 23
        )
    }

    fun updateProductInput(): Product {
        return Product(1, "Update-Coca cola", 12.00, 30 )
    }

    fun updateProductOutput(): Product? {
        return Product(1, "Update-Coca cola", 12.00, 30 )
    }

    fun updateProductUpdateRequest(): ProductUpdateRequest {
        return ProductUpdateRequest(1, "Update-Coca cola", 12.00, 30 )
    }

    fun findAll(): MutableList<Product>? {
        val product = Product(1, "Coca cola", 12.00, 30)
        val product2 = Product(2, "Fanta", 12.00, 30)
        val product3 = Product(3, "Pepis", 12.00, 30)

        return mutableListOf(product, product2, product3)
    }
}