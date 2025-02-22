package me.androidbox.spendless.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initializeKoin(config: KoinAppDeclaration? = null, vararg platformSpecificModules: Module = emptyArray()) {
    startKoin {
        config?.invoke(this)
        modules(
            spendLessModule,
            *platformSpecificModules
        )
    }
}