package br.com.softwalter.domain.service.impl

import br.com.softwalter.controller.dto.ProductRequest
import br.com.softwalter.controller.dto.ProductResponse
import br.com.softwalter.controller.dto.ProductUpdateRequest
import br.com.softwalter.domain.excetption.AlreadyExistsException
import br.com.softwalter.domain.excetption.ProductNotFoundException
import br.com.softwalter.domain.repositories.ProductRepository
import br.com.softwalter.domain.service.ProductService
import br.com.softwalter.domain.util.toDomain
import br.com.softwalter.domain.util.toProductResponse
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun createProduct(request: ProductRequest): ProductResponse {

        logger.info(" valid nome Product")
        verifiName(request.name)
        logger.info("Creating Product in repository")
        val product = productRepository
                .save(request.toDomain())

        logger.info("Product saved, return object response")
        return product
                .toProductResponse()
    }

    override fun findById(productId: Long): ProductResponse {
        val findById = productRepository.findById(productId)
        findById.orElseThrow { ProductNotFoundException(productId) }
        return findById.get().toProductResponse()
    }

    override fun updateProduct(requestUpdate: ProductUpdateRequest): ProductResponse {
        verifiName(requestUpdate.name)
        val product = productRepository.findById(requestUpdate.productId)
                .orElseThrow { ProductNotFoundException(requestUpdate.productId) }
        val copy = product.copy(name = requestUpdate.name,
                price = requestUpdate.price,
                quantityInStock = requestUpdate.quantityInStock)
        return productRepository.update(copy).toProductResponse()
    }

    override fun deleteProductById(byIdRequest: Long) {
        val product = productRepository.findById(byIdRequest)
                .orElseThrow { ProductNotFoundException(byIdRequest) }
        return productRepository.delete(product)
    }

    override fun findAll(): List<ProductResponse> {
        val products = productRepository.findAll()

        return products.map {
            it.toProductResponse()
        }
    }

    private fun verifiName(name: String) {
        productRepository.findByNameIgnoreCase(name)?.let {
            throw AlreadyExistsException(name)
        }
    }
}