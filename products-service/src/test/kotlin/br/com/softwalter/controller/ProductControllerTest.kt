package br.com.softwalter.controller

import br.com.softwalter.ByIdRequest
import br.com.softwalter.ProductsServiceGrpc.ProductsServiceBlockingStub
import br.com.softwalter.ProductsServiceRequest
import br.com.softwalter.ProductsServiceUpdateRequest
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@MicronautTest
internal class ProductControllerTest(
        private val flyway: Flyway,
        private val productsServiceBlockingStub: ProductsServiceBlockingStub
) {

    @BeforeEach
    fun setup() {
        flyway.clean()
        flyway.migrate()
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with valid data a success is returned`() {

        val productsServiceRequest = ProductsServiceRequest.newBuilder()
                .setName("Pepis")
                .setPrice(12.97)
                .setQuantityInStock(10)
                .build()
        val productsServiceReply =
                productsServiceBlockingStub.create(productsServiceRequest)

        assertEquals(3, productsServiceReply.productId)
        assertEquals("Pepis", productsServiceReply.name)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with valid id a success is returned`() {

        val productsServiceRequest = ByIdRequest.newBuilder()
                .setProductId(1L)
                .build()
        val productsServiceReply =
                productsServiceBlockingStub.findById(productsServiceRequest)

        assertEquals(1, productsServiceReply.productId)
        assertEquals("product name", productsServiceReply.name)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with valid data a success is returned`() {

        val productsServiceRequest = ProductsServiceUpdateRequest.newBuilder()
                .setProductId(2L)
                .setName("update-Coca Cola")
                .setPrice(12.97)
                .setQuantityInStock(10)
                .build()
        val productsServiceReply =
                productsServiceBlockingStub.update(productsServiceRequest)

        assertEquals(2, productsServiceReply.productId)
        assertEquals("update-Coca Cola", productsServiceReply.name)
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

    @Test
    fun `when ProductsServiceGrpc findById method is call with invalid id a throw ProductNotFoundException`() {

        val productsServiceRequest = ByIdRequest.newBuilder()
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
    fun `when ProductsServiceGrpc update method is call with invalid data a throw AlreadyExistsException`() {

        val productsServiceRequest = ProductsServiceUpdateRequest.newBuilder()
                .setProductId(1L)
                .setName("product name")
                .setPrice(12.97)
                .setQuantityInStock(10)
                .build()
        val description = "Produto: ${productsServiceRequest.name} já cadastrado no sistema"
        val assertThrowsExactly = assertThrowsExactly(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.update(productsServiceRequest)
        }
        assertEquals(Status.ALREADY_EXISTS.code, assertThrowsExactly.status.code)
        assertEquals(description, assertThrowsExactly.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with invalid data a throw ProductNotFoundException`() {

        val productsServiceRequest = ProductsServiceUpdateRequest.newBuilder()
                .setProductId(100000L)
                .setName("Ringo cola")
                .setPrice(12.97)
                .setQuantityInStock(10)
                .build()

        val description = "Produto com indentificação: ${productsServiceRequest.productId} não encontrado no sistema"
        val assertThrowsExactly = assertThrowsExactly(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.update(productsServiceRequest)
        }
        assertEquals(Status.NOT_FOUND.code, assertThrowsExactly.status.code)
        assertEquals(description, assertThrowsExactly.status.description)
    }

}