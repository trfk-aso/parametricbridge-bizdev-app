package parametricbridge.bizdev.app.di

import parametricbridge.bizdev.app.data.datastore.KBPMHOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { KBPMHOnboardingPrefs(androidContext()) }
}