package parametricbridge.bizdev.app

import android.app.Application
import parametricbridge.bizdev.app.di.dataModule
import parametricbridge.bizdev.app.di.dispatcherModule
import parametricbridge.bizdev.app.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ServiceApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModules = dataModule + viewModule + dispatcherModule

        startKoin {
            androidLogger()
            androidContext(this@ServiceApplication)
            modules(appModules)
        }
    }
}