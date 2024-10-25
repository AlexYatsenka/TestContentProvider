package com.alexyatsenka.testcontentprovider.di.modules

import android.content.Context
import androidx.room.Room
import com.alexyatsenka.testcontentprovider.data.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun databaseProvides(context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database"
    ).build()


    @Provides
    fun noteDaoProvides(database : AppDatabase) = database.getNoteDao()
}