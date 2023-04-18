package br.com.softwalter.controller

import br.com.softwalter.FindByIdServiceRequest
import br.com.softwalter.ProductsServiceGrpc.ProductsServiceBlockingStub
import br.com.softwalter.ProductsServiceRequest
import br.com.softwalter.domain.excetption.ProductNotFoundException
import io.grpc.Status
import io.grpc.StatusRuntimeException
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
                .setName("Coca Cola")
                .setPrice(12.97)
                .setQuantityInStock(10)
                .build()
        val productsServiceReply =
                productsServiceBlockingStub.create(productsServiceRequest)

//        assertEquals(2, productsServiceReply.productId)
//        assertEquals("Coca Cola", productsServiceReply.name)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with valid id a success is returned`() {

        val productsServiceRequest = FindByIdServiceRequest.newBuilder()
                .setProductId(1L)
                .build()
        val productsServiceReply =
                productsServiceBlockingStub.findById(productsServiceRequest)

        assertEquals(1, productsServiceReply.productId)
        assertEquals("product name", productsServiceReply.name)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with invalid id a throw ProductNotFoundException`() {

        val productsServiceRequest = FindByIdServiceRequest.newBuilder()
                .setProductId(10L)
                .build()
        val description = "Produto com indentificação: ${productsServiceRequest.productId} não encontrado no sistema"
        val assertThrowsExactly = assertThrowsExactly(StatusRuntimeException::class.java) {
                    productsServiceBlockingStub.findById(productsServiceRequest)
        }
        assertEquals(Status.NOT_FOUND.code, assertThrowsExactly.status.code)
        assertEquals(description, assertThrowsExactly.status.description)
    }
    @Test
    fun `when ProductsServiceGrpc create method is call with invalid data a throw AlreadyExistsException`() {

        val productsServiceRequest = ProductsServiceRequest.newBuilder()
                .setName("product name")
                .setPrice(12.97)
                .setQuantityInStock(10)
                .build()
        val description = "Produto: ${productsServiceRequest.name} já cadastrado no sistema"
        val assertThrowsExactly = assertThrowsExactly(StatusRuntimeException::class.java) {
                    productsServiceBlockingStub.create(productsServiceRequest)
        }
        assertEquals(Status.ALREADY_EXISTS.code, assertThrowsExactly.status.code)
        assertEquals(description, assertThrowsExactly.status.description)
    }
}