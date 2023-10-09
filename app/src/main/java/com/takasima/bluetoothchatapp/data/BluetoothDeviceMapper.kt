package com.takasima.bluetoothchatapp.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.takasima.bluetoothchatapp.domain.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name, //permission check have addes in controller
        address = address
    )
}