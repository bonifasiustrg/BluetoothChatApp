package com.takasima.bluetoothchatapp.data

sealed interface ConnectionResult {
    object ConnectionEstablished: ConnectionResult

    data class Error(val message: String): ConnectionResult
}