package br.com.softwalter.controller


import br.com.softwalter.ProductsServiceGrpc
import br.com.softwalter.ProductsServiceReply
import br.com.softwalter.ProductsServiceRequest
import br.com.softwalter.controller.dto.ProductRequest
import br.com.softwalter.domain.service.ProductService
import br.com.softwalter.domain.util.ValidationUtil
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductController(private val productService: ProductService) : ProductsServiceGrpc.ProductsServiceImplBase(){

    override fun create(request: ProductsServiceRequest?, responseObserver: StreamObserver<ProductsServiceReply>?) {
        val payload = ValidationUtil.validatePayload(request)
        val productRequest = ProductRequest(payload.name, payload.price, payload.quantityInStock)
        val productResponse = productService.createProduct(productRequest)

        val productsServiceResponse = ProductsServiceReply.newBuilder()
                .setProductId(productResponse.productId)
                .setName(productResponse.name)
                .setPrice(productResponse.price)
                .setQuantityInStock(productResponse.quantityInStock)
                .build()

        responseObserver?.onNext(productsServiceResponse)
        responseObserver?.onCompleted()
    }
}