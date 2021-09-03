package com.dehaat.permissionsmanager.interfaces

interface PermissionsResultCallback {

    /**
     * Performs the actions when the permissions are granted. e.g. take photos, record audio,
     * capture locations, etc.
     * */
    fun onPermissionsGranted()

    /**
     * Shows a UI to nudge the user to enable the permissions, when the permissions are temporarily
     * denied(Never ask again is false). To initiate the permission checks again(e.g., on clicking
     * the ‘Allow’ button on snack bar), call the requestPermissions() function.
     * */
    fun onPermissionsTemporarilyDenied(
        deniedPermissions: Array<String>,
        requestPermissions: () -> Unit
    )

    /**
     * Shows a UI to nudge the user to enable the permissions, when they are permanently
     * denied(Never ask again is true). To enable the permissions(e.g., on clicking the ‘Allow’
     * button on snack bar), call the openAppSettings() function to take the user to the Settings
     * page of our app.
     * */
    fun onPermissionsPermanentlyDenied(
        deniedPermissions: Array<String>,
        openAppSettings: () -> Unit
    )
}