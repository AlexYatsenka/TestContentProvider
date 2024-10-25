package com.alexyatsenka.testcontentprovider.di

import android.content.Context
import com.alexyatsenka.testcontentprovider.data.DataProvider
import com.alexyatsenka.testcontentprovider.di.modules.DatabaseModule
import com.alexyatsenka.testcontentprovider.di.modules.RepositoryModule
import com.alexyatsenka.testcontentprovider.di.modules.UseCaseModule
import com.alexyatsenka.testcontentprovider.presentation.MainActivity
import com.alexyatsenka.testcontentprovider.presentation.MainViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DatabaseModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(activity : MainActivity)
    fun inject(provider : DataProvider)

    fun getMainViewModel() : MainViewModel

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context : Context
        ) : AppComponent
    }
}