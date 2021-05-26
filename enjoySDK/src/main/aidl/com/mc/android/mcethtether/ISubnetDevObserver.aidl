package com.mc.android.mcethtether;

import com.mc.android.mcethtether.McEthTetherSubDev;

interface ISubnetDevObserver {
    void onDeviceAdded(in McEthTetherSubDev device);
    void onDeviceRemoved(in McEthTetherSubDev device);
    void onDeviceStateChanged(in McEthTetherSubDev device);
}	

