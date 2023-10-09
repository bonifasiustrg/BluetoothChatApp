package com.takasima.bluetoothchatapp.domain

import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val scannerdDevices: StateFlow<List<BluetoothDeviceDomain>>
    val pairedDevices: StateFlow<List<BluetoothDeviceDomain>>

    fun startDiscovery()
    fun stopDiscovery()
    fun release()
}