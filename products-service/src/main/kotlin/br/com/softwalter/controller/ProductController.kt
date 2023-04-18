package br.com.softwalter.controller


import br.com.softwalter.ByIdRequest
import br.com.softwalter.Empty
import br.com.softwalter.ProductsList
import br.com.softwalter.ProductsServiceGrpc
import br.com.softwalter.ProductsServiceReply
import br.com.softwalter.ProductsServiceRequest
import br.com.softwalter.ProductsServiceUpdateRequest
import br.com.softwalter.controller.dto.ProductRequest
import br.com.softwalter.controller.dto.ProductUpdateRequest
import br.com.softwalter.domain.excetption.BaseBusinessException
import br.com.softwalter.domain.service.ProductService
import br.com.softwalter.domain.util.ValidationUtil
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductController(private val productService: ProductService) : ProductsServiceGrpc.ProductsServiceImplBase() {

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
        } catch (ex: Throwable) {
            responseObserver?.onError(Status.UNKNOWN.code.toStatus()
                    .withDescription("Internal server error").asException())
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
        } catch (ex: Throwable) {
            responseObserver?.onError(Status.UNKNOWN.code.toStatus()
                    .withDescription("Internal server error").asException())
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
        } catch (ex: Throwable) {
            responseObserver?.onError(Status.UNKNOWN.code.toStatus()
                    .withDescription("Internal server error").asException())
        }
    }

    override fun delete(request: ByIdRequest?, responseObserver: StreamObserver<Empty>?) {
        try {
            productService.deleteProductById(request!!.productId)

            responseObserver?.onNext(Empty.newBuilder().build())
            responseObserver?.onCompleted()
        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(
                    ex.statusCode().toStatus()
                            .withDescription(ex.errorMenssage()).asRuntimeException()
            )
        } catch (ex: Throwable) {
            responseObserver?.onError(Status.UNKNOWN.code.toStatus()
                    .withDescription("Internal server error").asException())
        }
    }

    override fun findAll(request: Empty?, responseObserver: StreamObserver<ProductsList>?) {
        try {
            val productResponseList = productService.findAll()

            val serviceReplyList = productResponseList.map {
                ProductsServiceReply.newBuilder()
                        .setProductId(it.productId)
                        .setName(it.name)
                        .setPrice(it.price)
                        .setQuantityInStock(it.quantityInStock)
                        .build()
            }
            val productsList = ProductsList.newBuilder().addAllProducts(serviceReplyList).build()
            responseObserver?.onNext(productsList)
            responseObserver?.onCompleted()
        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(
                    ex.statusCode().toStatus()
                            .withDescription(ex.errorMenssage()).asRuntimeException()
            )
        } catch (ex: Throwable) {
            responseObserver?.onError(Status.UNKNOWN.code.toStatus()
                    .withDescription("Internal server error").asException())
        }
    }
}