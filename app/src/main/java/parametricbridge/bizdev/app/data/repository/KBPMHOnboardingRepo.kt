package parametricbridge.bizdev.app.data.repository

import parametricbridge.bizdev.app.data.datastore.KBPMHOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class KBPMHOnboardingRepo(
    private val kbpmhOnboardingStoreManager: KBPMHOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return kbpmhOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            kbpmhOnboardingStoreManager.setOnboardedState(state)
        }
    }
}