package parametricbridge.bizdev.app.di

import parametricbridge.bizdev.app.data.repository.BookingRepository
import parametricbridge.bizdev.app.data.repository.KBPMHOnboardingRepo
import parametricbridge.bizdev.app.data.repository.ServiceRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        KBPMHOnboardingRepo(
            kbpmhOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ServiceRepository() }

    single{
        BookingRepository(
            bookingDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}