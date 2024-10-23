package com.alexyatsenka.userprovider.di

import android.content.Context
import com.alexyatsenka.userprovider.di.modules.RepositoryModule
import com.alexyatsenka.userprovider.di.modules.UseCaseModule
import com.alexyatsenka.userprovider.presentation.main.MainActivity
import com.alexyatsenka.userprovider.presentation.main.MainViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)

    fun getMainViewModel() : MainViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context : Context) : AppComponent
    }
}