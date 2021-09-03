package com.dehaat.permissionsmanager.launcher

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.dehaat.permissionsmanager.helper.PermissionHelper
import com.dehaat.permissionsmanager.interfaces.PermissionsResultCallback

abstract class PermissionsLauncher(
    protected val permissions: Array<String>,
    protected val callback: PermissionsResultCallback,
    protected val helper: PermissionHelper
) : IPermissionsLauncher {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var settingsResultLauncher: ActivityResultLauncher<Intent>

    override fun register(fragment: Fragment) {
        setPermissionsLauncher(fragment)
        setResultLauncher(fragment)
    }

    override fun register(activity: ComponentActivity) {
        setPermissionsLauncher(activity)
        setResultLauncher(activity)
    }

    override fun unregister() {
        if (this::permissionLauncher.isInitialized)
            permissionLauncher.unregister()
        if (this::settingsResultLauncher.isInitialized)
            settingsResultLauncher.unregister()
    }

    protected fun showSystemRationale() = permissionLauncher.launch(permissions)

    private fun setPermissionsLauncher(fragment: Fragment) {
        permissionLauncher =
            fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                onPermissionsAsked(fragment.requireActivity())
            }
    }

    private fun setPermissionsLauncher(activity: ComponentActivity) {
        permissionLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                onPermissionsAsked(activity)
            }
    }

    private fun setResultLauncher(fragment: Fragment) {
        settingsResultLauncher =
            fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                onActivityResult()
            }
    }

    private fun setResultLauncher(activity: ComponentActivity) {
        settingsResultLauncher =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                onActivityResult()
            }
    }

    private fun onActivityResult() =
        if (helper.allPermissionsAreGranted(permissions))
            callback.onPermissionsGranted()
        else {
            val pendingPermissions = helper.getPendingPermissions(permissions)
            callback.onPermissionsPermanentlyDenied(pendingPermissions) {
                settingsResultLauncher.launch(helper.getAppSettingsIntent())
            }
        }

    private fun onPermissionsAsked(activity: Activity) {
        val pendingPermissions = helper.getPendingPermissions(permissions)

        when {
            pendingPermissions.isEmpty() ->
                callback.onPermissionsGranted()

            helper.arePermissionsPermanentlyDenied(permissions, activity) ->
                callback.onPermissionsPermanentlyDenied(pendingPermissions) {
                    settingsResultLauncher.launch(helper.getAppSettingsIntent())
                }

            else ->
                callback.onPermissionsTemporarilyDenied(pendingPermissions) {
                    permissionLauncher.launch(permissions)
                }
        }
    }
}