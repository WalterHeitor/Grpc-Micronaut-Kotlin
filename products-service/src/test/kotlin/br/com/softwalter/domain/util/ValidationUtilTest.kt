package br.com.softwalter.domain.util

import br.com.softwalter.template.ProductMockFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class ValidationUtilTest {

    @Test
    fun `when validatePayload is call with valid data, should not throw exception`() {

        Assertions.assertDoesNotThrow {
            ValidationUtil.validatePayload(ProductMockFactory.createProductServiceRequest())
        }
    }
    @Test
    fun `when validatePayload is call with invalid product name, should throw exception`() {

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            var payload = ProductMockFactory.createProductServiceRequest()
            val payloadError = payload!!.newBuilderForType().mergeFrom(payload).setName("").build()
            ValidationUtil.validatePayload(payloadError)
        }
    }

    @Test
    fun `when validatePayload is call with invalid product price, should throw exception`() {

        Assertions.assertThrowsExactly(IllegalArgumentException::class.java) {
            var payload = ProductMockFactory.createProductServiceRequest()
            val payloadError = payload!!.newBuilderForType().mergeFrom(payload).setPrice(-1.0).build()
            ValidationUtil.validatePayload(payloadError)
        }
    }

    @Test
    fun `when validatePayload is call with null payload, should throw exception`() {

        Assertions.assertThrowsExactly(IllegalArgumentException::class.java) {
            ValidationUtil.validatePayload(null)
        }
    }
}