package br.com.softwalter.domain.service.impl

import br.com.softwalter.Empty
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

    @Test
    fun `when update method is call with duplicated product-name, throw AlreadyExistsException` (){

        val request = ProductMockFactory.createProductUpdateRequest()
        val input = ProductMockFactory.createProductInput()
        val output = ProductMockFactory.createProductOutput()

        Mockito.`when`(productRepository.findByNameIgnoreCase(input.name))
                .thenReturn(output)

        Assertions.assertThrowsExactly(AlreadyExistsException::class.java) {
            productService.updateProduct(request)
        }
    }

    @Test
    fun `when upadate method is a call with id invalid throws ProductNotFoundException`() {
        Assertions.assertThrowsExactly(ProductNotFoundException::class.java) {
            productService.updateProduct(ProductMockFactory.createProductUpdateRequest())}
    }

    @Test
    fun `when update method is call with valid data a ProductResponse is returned` (){

        val request = ProductMockFactory.updateProductUpdateRequest()
        val input = ProductMockFactory.updateProductInput()
        val findByIdoutput = ProductMockFactory.createProductOutput()
        val outputUpdate = ProductMockFactory.updateProductOutput()

        Mockito.`when`(input.productId?.let { productRepository.findById(it) })
                .thenReturn(Optional.of(findByIdoutput))
        Mockito.`when`(productRepository.update(input))
                .thenReturn(outputUpdate)
        val actual = productService.updateProduct(request)

        Assertions.assertEquals(input.name, actual.name)
    }

    @Test
    fun `when delete method is call with valid id the product deleted` (){

        val input = 1L
        val output = ProductMockFactory.createProductOutput()

        Mockito.`when`(productRepository.findById(input))
                .thenReturn(Optional.of(output))

        Assertions.assertDoesNotThrow{
            productService.deleteProductById(input)
        }
    }

    @Test
    fun `when delete method is a call, with id invalid throws ProductNotFoundException`() {
        Assertions.assertThrowsExactly(ProductNotFoundException::class.java) {
            productService.deleteProductById(1000L)}
    }

    @Test
    fun `when findAll method is call, a ProductResponse is returned` (){

        val outputUpdate = ProductMockFactory.findAll()

        Mockito.`when`(productRepository.findAll())
                .thenReturn(outputUpdate)
        val actual = productService.findAll()

        Assertions.assertEquals(outputUpdate!![0].name, actual[0].name)
    }


}