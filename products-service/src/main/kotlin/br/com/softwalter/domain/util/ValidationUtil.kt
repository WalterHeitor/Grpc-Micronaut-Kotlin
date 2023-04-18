package br.com.softwalter.domain.util

import br.com.softwalter.ProductsServiceRequest
import br.com.softwalter.ProductsServiceUpdateRequest
import br.com.softwalter.domain.excetption.InvalidArgumentExceptionException

class ValidationUtil {
    companion object {

        fun validatePayload(payload: ProductsServiceRequest?) : ProductsServiceRequest {

            payload?.let {
                if (it.name.isNullOrBlank())
                    throw InvalidArgumentExceptionException("nome")

                if (it.price.isNaN() || it.price < 0)
                    throw InvalidArgumentExceptionException("preço")

                return it
            }
            throw InvalidArgumentExceptionException("payload")
        }

        fun validateUpdatePayload(payload: ProductsServiceUpdateRequest?) : ProductsServiceUpdateRequest {

            payload?.let {
                if (it.productId <= 0)
                    throw InvalidArgumentExceptionException("productId")

                if (it.name.isNullOrBlank())
                    throw InvalidArgumentExceptionException("nome")

                if (it.price.isNaN() || it.price < 0)
                    throw InvalidArgumentExceptionException("preço")

                return it
            }
            throw InvalidArgumentExceptionException("payload")
        }
    }
}