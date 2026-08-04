package com.easy.example.ui.jetpack.package1

import androidx.lifecycle.viewModelScope
import com.easy.core.ui.base.BaseStateViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StateViewModelDemoState(
    val loadCount: Int = 0
)

sealed interface StateViewModelDemoEffect {
    data object Loaded : StateViewModelDemoEffect
}

class StateViewModelDemoViewModel : BaseStateViewModel() {
    private val mutableUiState = MutableStateFlow(StateViewModelDemoState())
    private val mutableUiEffect = Channel<StateViewModelDemoEffect>(Channel.BUFFERED)

    val uiState = mutableUiState.asStateFlow()
    val uiEffect = mutableUiEffect.receiveAsFlow()

    fun load() {
        viewModelScope.launch {
            withLoading {
                delay(300)
                mutableUiState.update { it.copy(loadCount = it.loadCount + 1) }
                mutableUiEffect.send(StateViewModelDemoEffect.Loaded)
            }
        }
    }

    override fun onCleared() {
        mutableUiEffect.close()
        super.onCleared()
    }
}
