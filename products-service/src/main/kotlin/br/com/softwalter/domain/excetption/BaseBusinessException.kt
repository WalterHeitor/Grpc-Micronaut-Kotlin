package br.com.softwalter.domain.excetption

import io.grpc.Status
import java.lang.RuntimeException

abstract class BaseBusinessException : RuntimeException() {

    abstract fun errorMenssage(): String
    abstract fun statusCode(): Status.Code
}