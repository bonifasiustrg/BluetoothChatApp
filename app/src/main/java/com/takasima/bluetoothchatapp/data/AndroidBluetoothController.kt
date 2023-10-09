package com.takasima.bluetoothchatapp.data

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import com.takasima.bluetoothchatapp.domain.BluetoothController
import com.takasima.bluetoothchatapp.domain.BluetoothDeviceDomain
import com.takasima.bluetoothchatapp.domain.toBluetoothDeviceDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("MissingPermission")
class AndroidBluetoothController(
    private val context: Context
): BluetoothController {
    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())

    //  provice bluetooth from  android system
    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
//    hardware modul
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val foundDeviceReceiver = FoundDeviceReceiver {device->
        _scannedDevices.update {devices->
            val newDevice = device.toBluetoothDeviceDomain()

            if (newDevice in devices) devices else devices + newDevice
        }

    }

    init {
        updatePairedDevices()
    }

    override val scannerdDevices: StateFlow<List<BluetoothDeviceDomain>>
        get() = _scannedDevices.asStateFlow()
    override val pairedDevices: StateFlow<List<BluetoothDeviceDomain>>
        get() = _pairedDevices.asStateFlow()

    override fun startDiscovery() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }
        updatePairedDevices()
        bluetoothAdapter?.startDiscovery()

        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)
        )
    }

    override fun stopDiscovery() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        bluetoothAdapter?.cancelDiscovery()
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
    }


    private fun updatePairedDevices() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)){
            return
        }
        bluetoothAdapter
            ?.bondedDevices
            ?.map { it.toBluetoothDeviceDomain()}
            ?.also { devices->
                _pairedDevices.update { devices  }
            }
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

}