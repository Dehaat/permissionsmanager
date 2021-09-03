package com.dehaat.permissionsmanager.interfaces

interface CustomPermissionsResultCallback : PermissionsResultCallback {

    /**
     * Shows your informative UI giving a reason why you require the permissions. If your
     * informative UI has an ‘OK’ button or something similar, then call
     * initiatePermissionRationale() method of the listener provided here, and the module will take
     * care of the rest of the flow.
     * */
    fun showCustomRationale(listener: InitiatePermissionListener)
}