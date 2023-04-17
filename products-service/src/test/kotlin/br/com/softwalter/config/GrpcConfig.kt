package br.com.softwalter.config

import br.com.softwalter.ProductsServiceGrpc
import br.com.softwalter.ProductsServiceGrpc.ProductsServiceBlockingStub
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel

@Factory
class GrpcConfig {

    @Bean
    fun productServerBean(
            @GrpcChannel("productservice")
            channel: ManagedChannel
    ): ProductsServiceBlockingStub {
        return ProductsServiceGrpc.newBlockingStub(channel)
    }
}