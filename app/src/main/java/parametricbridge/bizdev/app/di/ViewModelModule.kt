package parametricbridge.bizdev.app.di

import parametricbridge.bizdev.app.ui.viewmodel.BookingViewModel
import parametricbridge.bizdev.app.ui.viewmodel.CheckoutViewModel
import parametricbridge.bizdev.app.ui.viewmodel.KBPMHOnboardingVM
import parametricbridge.bizdev.app.ui.viewmodel.ServiceDetailsViewModel
import parametricbridge.bizdev.app.ui.viewmodel.ServiceViewModel
import parametricbridge.bizdev.app.ui.viewmodel.KBPMHSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        KBPMHSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        KBPMHOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ServiceViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        ServiceDetailsViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        BookingViewModel(
            bookingRepository = get(),
            serviceRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            bookingRepository = get(),
        )
    }
}