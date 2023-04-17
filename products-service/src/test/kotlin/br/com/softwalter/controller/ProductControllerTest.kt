package br.com.softwalter.controller

import br.com.softwalter.ProductsServiceGrpc.ProductsServiceBlockingStub
import br.com.softwalter.ProductsServiceRequest
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@MicronautTest
internal class ProductControllerTest(
        private val productsServiceBlockingStub: ProductsServiceBlockingStub
) {

    @Test
    fun `when ProductsServiceGrpc create method is call with valid data a success is returned`() {

        val productsServiceRequest = ProductsServiceRequest.newBuilder()
                .setName("product name")
                .setPrice(12.97)
                .setQuantityInStock(10)
                .build()
        val productsServiceReply =
                productsServiceBlockingStub.create(productsServiceRequest)

        assertEquals(1, productsServiceReply.productId)
        assertEquals("product name", productsServiceReply.name)
    }
}