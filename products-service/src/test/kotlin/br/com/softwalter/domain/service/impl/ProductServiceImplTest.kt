package br.com.softwalter.domain.service.impl

import br.com.softwalter.domain.repositories.ProductRepository
import br.com.softwalter.template.ProductMockFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

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
}