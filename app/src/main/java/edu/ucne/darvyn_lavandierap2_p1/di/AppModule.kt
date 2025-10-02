package edu.ucne.darvyn_lavandierap2_p1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.ucne.darvyn_lavandierap2_p1.data.local.dao.HuacalesDao
import edu.ucne.darvyn_lavandierap2_p1.data.local.database.AppDatabase
import edu.ucne.darvyn_lavandierap2_p1.data.repository.HuacalRepositoryImpl
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.repository.HuacalRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "huacales_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHuacalesDao(db: AppDatabase): HuacalesDao = db.entradasHuacalesDao()

    @Provides
    @Singleton
    fun provideHuacalRepository(dao: HuacalesDao): HuacalRepository {
        return HuacalRepositoryImpl(dao)
    }
}
