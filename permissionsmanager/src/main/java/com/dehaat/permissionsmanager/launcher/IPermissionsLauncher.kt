package com.dehaat.permissionsmanager.launcher

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment

interface IPermissionsLauncher {

    /**
     * Registers a launcher from a Fragment.
     * */
    fun register(fragment: Fragment)

    /**
     * Registers a launcher from an Activity.
     * */
    fun register(activity: ComponentActivity)

    /**
     * Unregisters a launcher from an Activity/Fragment.
     * */
    fun unregister()

    /**
     * Initiates permission checks through the launcher.
     * */
    fun checkPermissions()
}