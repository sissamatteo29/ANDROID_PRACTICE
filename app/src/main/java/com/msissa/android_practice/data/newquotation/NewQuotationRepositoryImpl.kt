package com.msissa.android_practice.data.newquotation

import com.msissa.android_practice.data.settings.SettingsRepository
import com.msissa.android_practice.data.utils.NoInternetException
import com.msissa.android_practice.domain.model.Quotation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import com.msissa.android_practice.data.newquotation.model.toDomain
import javax.inject.Inject

class NewQuotationRepositoryImpl
@Inject constructor(
    private val newQuotationDataSource : NewQuotationDataSource,
    private val connectivityChecker : ConnectivityChecker,
    private val settingsRepository: SettingsRepository
) : NewQuotationRepository {

    private lateinit var selectedLanguage : String

    init {
        // Observe the selected language from the stored settings
        CoroutineScope(SupervisorJob()).launch {
            settingsRepository.getLanguage().collect { languageCode ->
                selectedLanguage = languageCode.ifEmpty{ "en" }
            }
        }
    }

    override suspend fun getNewQuotation(): Result<Quotation> {
        if(!connectivityChecker.isConnectionAvailable()) {
            return Result.failure(NoInternetException())
        }

        return newQuotationDataSource.getQuotation(selectedLanguage).toDomain()

    }

}