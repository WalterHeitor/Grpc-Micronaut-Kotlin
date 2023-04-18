package br.com.softwalter.domain.service.impl

import br.com.softwalter.domain.excetption.AlreadyExistsException
import br.com.softwalter.domain.excetption.ProductNotFoundException
import br.com.softwalter.domain.repositories.ProductRepository
import br.com.softwalter.template.ProductMockFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*

internal class ProductServiceImplTest {

    private val productRepository = Mockito.mock(ProductRepository::class.java)
    private val productService = ProductServiceImpl(productRepository)

    @Test
    fun `when create method is call with valid data a ProductResponse is returned` (){

        val request = ProductMockFactory.createProductRequest()
        val input = ProductMockFactory.createProductInput()
        val output = ProductMockFactory.createProductOutput()

        Mockito.`when`(productRepository.save(input))
                .thenReturn(output)
        val actual = productService.createProduct(request)

        Assertions.assertEquals(input.name, actual.name)
    }

    @Test
    fun `when create method is call with duplicated product-name, throw AlreadyExistsException` (){

        val request = ProductMockFactory.createProductRequest()
        val input = ProductMockFactory.createProductInput()
        val output = ProductMockFactory.createProductOutput()

        Mockito.`when`(productRepository.findByNameIgnoreCase(input.name))
                .thenReturn(output)

        Assertions.assertThrowsExactly(AlreadyExistsException::class.java) {
            productService.createProduct(request)
        }
    }

    @Test
    fun `when findById method is call with valid id a ProductResponse is returned` (){

        val input = 1L
        val output = ProductMockFactory.createProductOutput()

        Mockito.`when`(productRepository.findById(input))
                .thenReturn(Optional.of(output))
        val actual = productService.findById(input)

        Assertions.assertEquals(input, actual.productId)
        Assertions.assertEquals(output.name, actual.name)
    }

    @Test
    fun `when findById method is a call with id invalid throws ProductNotFoundException`() {
        val id = 1L
        Assertions.assertThrowsExactly(ProductNotFoundException::class.java) {productService.findById(id)}
    }
}