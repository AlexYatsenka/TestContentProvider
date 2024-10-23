package com.alexyatsenka.userprovider.di

import android.content.Context

object Dagger {

    private var mAppComponent : AppComponent? = null
    val appComponent get() = mAppComponent!!

    fun buildAppComponent(context : Context) : AppComponent {
        if(mAppComponent == null) {
            synchronized(this) {
                if(mAppComponent == null) {
                    mAppComponent = DaggerAppComponent.factory()
                        .create(context)
                }
            }
        }

        return appComponent
    }

}