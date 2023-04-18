package br.com.softwalter.domain.excetption

import io.grpc.Status

class ProductNotFoundException(private val productId: Long) : BaseBusinessException(){
    override fun errorMenssage(): String {
        return "Produto com indentificação: $productId não encontrado no sistema"
    }

    override fun statusCode(): Status.Code {
        return Status.Code.NOT_FOUND
    }
}