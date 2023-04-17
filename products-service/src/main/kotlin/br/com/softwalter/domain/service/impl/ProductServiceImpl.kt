package br.com.softwalter.domain.service.impl

import br.com.softwalter.controller.dto.ProductRequest
import br.com.softwalter.controller.dto.ProductResponse
import br.com.softwalter.domain.repositories.ProductRepository
import br.com.softwalter.domain.service.ProductService
import br.com.softwalter.domain.util.toDomain
import br.com.softwalter.domain.util.toProductResponse
import io.micronaut.data.annotation.Repository
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {

//    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun createProduct(request: ProductRequest): ProductResponse {

//        logger.info("Creating Product in repository")
        val product = productRepository
                .save(request.toDomain())
//        logger.info("Product saved, return object response")
        return product
                 .toProductResponse()
    }
}