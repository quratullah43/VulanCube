package com.vulancube.lqxgb.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vulancube.lqxgb.util.DeviceInfo
import com.vulancube.lqxgb.util.NetworkClient
import com.vulancube.lqxgb.util.StorageManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AppState {
    data object Loading : AppState()
    data class Remote(val link: String) : AppState()
    data object Game : AppState()
}

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val storage = StorageManager(application)
    private val deviceInfo = DeviceInfo(application)
    private val networkClient = NetworkClient()

    private val _appState = MutableStateFlow<AppState>(AppState.Loading)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _balance = MutableStateFlow(10000)
    val balance: StateFlow<Int> = _balance.asStateFlow()

    init {
        checkInitialState()
    }

    private fun checkInitialState() {
        viewModelScope.launch {
            if (storage.hasToken()) {
                val link = storage.getLink()
                if (!link.isNullOrEmpty()) {
                    _appState.value = AppState.Remote(link)
                    return@launch
                }
            }
            fetchServerData()
        }
    }

    private suspend fun fetchServerData() {
        val endpoint = buildEndpoint()
        val response = networkClient.fetchData(endpoint)

        if (response != null) {
            if (response.contains("#")) {
                val parts = response.split("#", limit = 2)
                if (parts.size == 2) {
                    storage.saveToken(parts[0])
                    storage.saveLink(parts[1])
                    _appState.value = AppState.Remote(parts[1])
                    return
                }
            }
            storage.savePolicyLink(response)
        }
        _appState.value = AppState.Game
    }

    private fun buildEndpoint(): String {
        val base = "https://aprulestext.site/a-vulan-cube/server.php"
        val params = StringBuilder("?p=Jh675eYuunk85")
        params.append("&os=${deviceInfo.getOsVersion()}")
        params.append("&lng=${deviceInfo.getLanguage()}")
        params.append("&loc=${deviceInfo.getRegion()}")
        params.append("&devicemodel=${deviceInfo.getDeviceModel()}")
        params.append("&bs=${deviceInfo.getBatteryStatus()}")
        params.append("&bl=${deviceInfo.getBatteryLevel()}")
        params.append("&nc=${deviceInfo.getNetworkCountry()}")
        params.append("&sm=${deviceInfo.getSimState()}")
        return base + params.toString()
    }

    fun getPolicyLink(): String? = storage.getPolicyLink()

    fun updateBalance(amount: Int) {
        _balance.value = (_balance.value + amount).coerceAtLeast(0)
    }

    fun getBalance(): Int = _balance.value
}
