package br.com.softwalter.domain.util

import br.com.softwalter.template.ProductMockFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ProductConverterUtilKtTest {

    @Test
    fun `when toProductResponse is call, should return ProductResponse with all data `() {

        val createProductOutput = ProductMockFactory.createProductOutput()
        val actual = createProductOutput.toProductResponse()
        Assertions.assertEquals(createProductOutput.productId, actual.productId)
        Assertions.assertEquals(createProductOutput.name, actual.name)
        Assertions.assertEquals(createProductOutput.price, actual.price)
    }

    @Test
    fun toDomain() {
        val createProductRequest = ProductMockFactory.createProductRequest()
        val product = createProductRequest.toDomain()
        Assertions.assertEquals(null, product.productId)
        Assertions.assertEquals(createProductRequest.name, product.name)
        Assertions.assertEquals(createProductRequest.price, product.price)
    }
}