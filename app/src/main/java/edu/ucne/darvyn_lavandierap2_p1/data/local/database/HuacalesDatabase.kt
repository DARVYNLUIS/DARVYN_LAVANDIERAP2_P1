package edu.ucne.darvyn_lavandierap2_p1.data.local.database


import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.darvyn_lavandierap2_p1.data.local.dao.HuacalesDao
import edu.ucne.darvyn_lavandierap2_p1.data.local.entities.HuacalEntity

@Database(
    entities = [HuacalEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entradasHuacalesDao(): HuacalesDao
}
