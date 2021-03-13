package com.sofits.proyectofinal.Modelos

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class GeneroLiterario (var nombre:String,
                       @Id @GeneratedValue val id:UUID?=null){
    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other === null || other !is GeneroLiterario)
            return false
        if (this::class != other::class)
            return false
        return id == other.id
    }

    override fun hashCode(): Int {
        if (id == null)
            return super.hashCode()
        return id.hashCode()
    }
}

interface GeneroLiterarioRepository : JpaRepository<GeneroLiterario,UUID>