package br.com.softwalter.controller


import br.com.softwalter.ByIdRequest
import br.com.softwalter.ProductsServiceGrpc
import br.com.softwalter.ProductsServiceReply
import br.com.softwalter.ProductsServiceRequest
import br.com.softwalter.ProductsServiceUpdateRequest
import br.com.softwalter.controller.dto.ProductRequest
import br.com.softwalter.controller.dto.ProductUpdateRequest
import br.com.softwalter.domain.excetption.BaseBusinessException
import br.com.softwalter.domain.service.ProductService
import br.com.softwalter.domain.util.ValidationUtil
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductController(private val productService: ProductService) : ProductsServiceGrpc.ProductsServiceImplBase(){

    override fun create(request: ProductsServiceRequest?, responseObserver: StreamObserver<ProductsServiceReply>?) {
        try {
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
        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(
                    ex.statusCode().toStatus()
                            .withDescription(ex.errorMenssage()).asRuntimeException()
            )
        }
    }

    override fun findById(request: ByIdRequest?, responseObserver: StreamObserver<ProductsServiceReply>?) {

        try {
            val productResponse = productService.findById(request!!.productId)
            val productsServiceResponse = ProductsServiceReply.newBuilder()
                    .setProductId(productResponse.productId)
                    .setName(productResponse.name)
                    .setPrice(productResponse.price)
                    .setQuantityInStock(productResponse.quantityInStock)
                    .build()

            responseObserver?.onNext(productsServiceResponse)
            responseObserver?.onCompleted()
        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(
                    ex.statusCode().toStatus()
                            .withDescription(ex.errorMenssage()).asRuntimeException()
            )
        }
    }

    override fun update(request: ProductsServiceUpdateRequest?, responseObserver: StreamObserver<ProductsServiceReply>?) {
        try {
            val validateUpdatePayload = ValidationUtil.validateUpdatePayload(request)
            val productRequest = ProductUpdateRequest(
                    productId = validateUpdatePayload.productId,
                    name = validateUpdatePayload.name,
                    price = validateUpdatePayload.price,
                    quantityInStock = validateUpdatePayload.quantityInStock
            )
            val productResponse = productService.updateProduct(productRequest)

            val productsServiceResponse = ProductsServiceReply.newBuilder()
                    .setProductId(productResponse.productId)
                    .setName(productResponse.name)
                    .setPrice(productResponse.price)
                    .setQuantityInStock(productResponse.quantityInStock)
                    .build()

            responseObserver?.onNext(productsServiceResponse)
            responseObserver?.onCompleted()
        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(
                    ex.statusCode().toStatus()
                            .withDescription(ex.errorMenssage()).asRuntimeException()
            )
        }
    }
}