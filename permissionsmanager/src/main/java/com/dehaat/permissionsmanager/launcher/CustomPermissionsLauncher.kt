package com.dehaat.permissionsmanager.launcher

import com.dehaat.permissionsmanager.helper.PermissionHelper
import com.dehaat.permissionsmanager.interfaces.CustomPermissionsResultCallback
import com.dehaat.permissionsmanager.interfaces.InitiatePermissionListener

/**
 * Has additional functionality over the DefaultPermissionsLauncher.
 * With this, you can also specify your own informative UI(to tell why you want to access the
 * permissions) before requesting the permissions.
 * */
class CustomPermissionsLauncher(
    permissions: Array<String>,
    callback: CustomPermissionsResultCallback,
    helper: PermissionHelper
) : PermissionsLauncher(permissions, callback, helper) {

    override fun checkPermissions() =
        if (helper.allPermissionsAreGranted(permissions))
            callback.onPermissionsGranted()
        else
            (callback as CustomPermissionsResultCallback)
                .showCustomRationale(getInitiatePermissionListener())

    private fun getInitiatePermissionListener() =
        InitiatePermissionListener { showSystemRationale() }
}