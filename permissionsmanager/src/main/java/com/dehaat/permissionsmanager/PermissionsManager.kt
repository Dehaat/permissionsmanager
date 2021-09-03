package com.dehaat.permissionsmanager

import android.content.Context
import com.dehaat.permissionsmanager.interfaces.CustomPermissionsResultCallback
import com.dehaat.permissionsmanager.interfaces.DefaultPermissionsResultCallback
import com.dehaat.permissionsmanager.launcher.CustomPermissionsLauncher
import com.dehaat.permissionsmanager.launcher.DefaultPermissionsLauncher

interface PermissionsManager {

    /**
     * Returns a DefaultPermissionsLauncher object. Use this function when you don’t want to show
     * an informative UI(why grant the permissions) before requesting the permissions from the OS.
     * */
    fun getPermissionsLauncher(
        permissions: Array<String>,
        callback: DefaultPermissionsResultCallback
    ): DefaultPermissionsLauncher

    /**
     * Returns a CustomPermissionsLauncher object. Use this function when you want to show an
     * informative UI(why grant the permissions) before requesting the permissions from the OS.
     * */
    fun getPermissionsLauncher(
        permissions: Array<String>,
        callback: CustomPermissionsResultCallback
    ): CustomPermissionsLauncher

    /**
     * Returns a boolean- true if all the requested permissions are granted, else false.
     * */
    fun getGrantedPermissions(context: Context, permissions: Array<String>): ArrayList<String>

    /**
     * Returns the list of permissions that are granted out of the requested permissions.
     * */
    fun arePermissionsGranted(context: Context, permissions: Array<String>): Boolean
}