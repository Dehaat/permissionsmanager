package com.dehaat.permissionsmanager

import android.content.Context
import com.dehaat.permissionsmanager.helper.PermissionHelper
import com.dehaat.permissionsmanager.interfaces.CustomPermissionsResultCallback
import com.dehaat.permissionsmanager.interfaces.DefaultPermissionsResultCallback
import com.dehaat.permissionsmanager.launcher.CustomPermissionsLauncher
import com.dehaat.permissionsmanager.launcher.DefaultPermissionsLauncher
import javax.inject.Inject

class PermissionsManagerImpl @Inject constructor(
    private val helper: PermissionHelper
) : PermissionsManager {

    override fun getPermissionsLauncher(
        permissions: Array<String>,
        callback: DefaultPermissionsResultCallback
    ) = DefaultPermissionsLauncher(permissions, callback, helper)

    override fun getPermissionsLauncher(
        permissions: Array<String>,
        callback: CustomPermissionsResultCallback
    ) = CustomPermissionsLauncher(permissions, callback, helper)

    override fun getGrantedPermissions(context: Context, permissions: Array<String>) =
        helper.getGrantedPermissions(permissions)

    override fun arePermissionsGranted(context: Context, permissions: Array<String>) =
        helper.arePermissionsGranted(permissions)
}