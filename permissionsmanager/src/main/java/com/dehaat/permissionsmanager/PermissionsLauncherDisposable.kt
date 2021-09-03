package com.dehaat.permissionsmanager

import com.dehaat.permissionsmanager.launcher.IPermissionsLauncher
import java.util.*
import javax.inject.Inject

class PermissionsLauncherDisposable @Inject constructor() {
    private val launchers = Stack<IPermissionsLauncher>()

    fun unregisterAll() {
        while (!launchers.empty()) {
            val launcher = launchers.pop()
            launcher.unregister()
        }
    }

    fun addLauncher(launcher: IPermissionsLauncher) {
        launchers.push(launcher)
    }
}