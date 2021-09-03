package com.dehaat.permissionsmanager.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import javax.inject.Inject

class PermissionHelper @Inject constructor(private val context: Context) {

    fun arePermissionsPermanentlyDenied(
        permissions: Array<String>,
        activity: Activity
    ): Boolean {
        for (permission in permissions) {
            if (!isGranted(permission) && !isPermanentlyDenied(permission, activity))
                return false
        }
        return true
    }

    fun allPermissionsAreGranted(permissions: Array<String>): Boolean {
        permissions.forEach { permission ->
            if (!isGranted(permission))
                return false
        }
        return true
    }

    private fun isGranted(permission: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        else
            true

    private fun isPermanentlyDenied(permission: String, activity: Activity) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            !activity.shouldShowRequestPermissionRationale(permission)
        else
            false

    fun getPendingPermissions(permissions: Array<String>) =
        mutableListOf<String>()
            .apply {
                for (permission in permissions) {
                    if (!isGranted(permission)) add(permission)
                }
            }
            .toTypedArray()

    fun getGrantedPermissions(permissions: Array<String>): ArrayList<String> =
        ArrayList<String>().apply {
            permissions.forEach {
                if (isGranted(it))
                    add(it.split(".")[2])
            }
        }

    fun arePermissionsGranted(permissions: Array<String>): Boolean {
        permissions.forEach {
            if (!isGranted(it))
                return false
        }
        return true
    }

    fun getAppSettingsIntent() =
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
}