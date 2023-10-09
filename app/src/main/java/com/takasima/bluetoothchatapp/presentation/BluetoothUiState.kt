package com.takasima.bluetoothchatapp.presentation

import com.takasima.bluetoothchatapp.domain.BluetoothDevice
import com.takasima.bluetoothchatapp.domain.BluetoothDeviceDomain

data class BluetoothUiState(
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
)
