package edu.ucne.darvyn_lavandierap2_p1.data.local.dao

import androidx.room.*
import edu.ucne.darvyn_lavandierap2_p1.data.local.entities.HuacalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HuacalesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entrada: HuacalEntity): Long

    @Update
    suspend fun update(entrada: HuacalEntity)

    @Delete
    suspend fun delete(entrada: HuacalEntity)

    @Query("SELECT * FROM EntradasHuacales WHERE idEntrada = :id LIMIT 1")
    suspend fun findById(id: Int): HuacalEntity?

    @Query("""
        SELECT * FROM EntradasHuacales
        WHERE (:cliente IS NULL OR nombreCliente LIKE '%' || :cliente || '%')
        AND (:fecha IS NULL OR fecha LIKE '%' || :fecha || '%')
    """)
    fun getEntradas(cliente: String? = null, fecha: String? = null): Flow<List<HuacalEntity>>
}
