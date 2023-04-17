package br.com.softwalter.domain.util

import br.com.softwalter.ProductsServiceRequest

class ValidationUtil {
    companion object {

        fun validatePayload(payload: ProductsServiceRequest?) : ProductsServiceRequest {

            payload?.let {
                if (it.name.isNullOrBlank()) throw IllegalArgumentException("nome nao pode ser branco ou nulo")

                if (it.price.isNaN() || it.price < 0) throw IllegalArgumentException("preço prescisa ser valor valido")

                return it
            }
            throw IllegalArgumentException("payload nulo")
        }
    }
}