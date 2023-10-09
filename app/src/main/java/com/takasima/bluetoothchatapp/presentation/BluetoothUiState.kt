package com.takasima.bluetoothchatapp.presentation

import com.takasima.bluetoothchatapp.domain.BluetoothDeviceDomain

data class BluetoothUiState(
    val scannedDevices: List<BluetoothDeviceDomain> = emptyList(),
    val pairedDevices: List<BluetoothDeviceDomain> = emptyList()


)
