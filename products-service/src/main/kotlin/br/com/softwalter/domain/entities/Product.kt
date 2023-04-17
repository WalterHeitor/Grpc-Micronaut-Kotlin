package br.com.softwalter.domain.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Product(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val productId: Long?,
        val name: String,
        val price: Double,
        val quantityInStock: Int,
)
