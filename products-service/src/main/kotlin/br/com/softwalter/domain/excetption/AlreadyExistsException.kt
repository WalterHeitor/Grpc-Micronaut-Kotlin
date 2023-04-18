package br.com.softwalter.domain.excetption

import io.grpc.Status

class AlreadyExistsException(private val productName: String) : BaseBusinessException(){
    override fun errorMenssage(): String {
        return "Produto: $productName já cadastrado no sistema"
    }

    override fun statusCode(): Status.Code {
        return Status.Code.ALREADY_EXISTS
    }
}