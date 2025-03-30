package me.androidbox.spendless

import android.app.Application
import io.kotzilla.sdk.analytics.koin.analytics
import io.kotzilla.sdk.android.security.apiKey
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level


class SpendLessApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeKoin(
            config = {
                androidContext(this@SpendLessApplication)
                androidLogger(Level.DEBUG)
                analytics { apiKey() }
            },
            androidSpecificModule
        )
    }
}