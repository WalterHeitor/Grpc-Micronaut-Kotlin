package br.com.softwalter.domain.repositories

import br.com.softwalter.domain.entities.Product
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
}