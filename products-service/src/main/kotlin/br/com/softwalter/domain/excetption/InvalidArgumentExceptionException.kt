package br.com.softwalter.domain.excetption

import io.grpc.Status

class InvalidArgumentExceptionException(private val argumentName: String) : BaseBusinessException(){
    override fun errorMenssage(): String {
        return "Argumento: $argumentName inválido"
    }

    override fun statusCode(): Status.Code {
        return Status.Code.INVALID_ARGUMENT
    }
}