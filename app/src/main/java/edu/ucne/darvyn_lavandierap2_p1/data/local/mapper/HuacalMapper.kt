package edu.ucne.darvyn_lavandierap2_p1.data.local.mapper

import edu.ucne.darvyn_lavandierap2_p1.data.local.entities.HuacalEntity
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.model.EntradaHuacal

object HuacalMapper {

    fun toDomain(entity: HuacalEntity): EntradaHuacal =
        EntradaHuacal(
            idEntrada = entity.idEntrada,
            fecha = entity.fecha,
            nombreCliente = entity.nombreCliente,
            cantidad = entity.cantidad,
            precio = entity.precio
        )

    fun toEntity(domain: EntradaHuacal): HuacalEntity =
        HuacalEntity(
            idEntrada = domain.idEntrada,
            fecha = domain.fecha,
            nombreCliente = domain.nombreCliente,
            cantidad = domain.cantidad,
            precio = domain.precio
        )
}
