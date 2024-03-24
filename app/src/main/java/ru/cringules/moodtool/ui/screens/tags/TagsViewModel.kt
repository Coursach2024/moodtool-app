package ru.cringules.moodtool.ui.screens.tags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cringules.moodtool.data.model.Correlations
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.AnalyticsRepository
import javax.inject.Inject

@HiltViewModel
class TagsViewModel @Inject constructor(
    private val analyticsRepository: AnalyticsRepository
) : ViewModel() {
    var tagsCorrelationsState: RepositoryResponse<Map<String, Correlations>> by mutableStateOf(
        RepositoryResponse.Loading
    )
        private set

    init {
        viewModelScope.launch {
            tagsCorrelationsState = analyticsRepository.getDependencies()
        }
    }
}