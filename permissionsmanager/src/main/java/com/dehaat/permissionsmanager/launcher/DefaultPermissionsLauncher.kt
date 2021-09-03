package com.dehaat.permissionsmanager.launcher

import com.dehaat.permissionsmanager.helper.PermissionHelper
import com.dehaat.permissionsmanager.interfaces.DefaultPermissionsResultCallback

/**
 * Checks if a list of permissions is granted. If they are granted then it performs the action
 * specified on onPermissionsGranted() callback method. If not granted, then it will show a UI
 * nudge to the user to enable permissions(e.g. a snack bar at the bottom of the screen)
 * */
class DefaultPermissionsLauncher(
    permissions: Array<String>,
    callback: DefaultPermissionsResultCallback,
    helper: PermissionHelper
) : PermissionsLauncher(permissions, callback, helper) {

    override fun checkPermissions() =
        if (helper.allPermissionsAreGranted(permissions))
            callback.onPermissionsGranted()
        else
            showSystemRationale()
}