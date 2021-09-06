## What is permissionsmanager?

It is a library that helps to manage permissions in Android apps. It contains the logic to:
* Check permissions
* Do the specified action when all permissions are granted
* Request permissions if they are not granted
* Take the user to app settings when all the remaining permissions are permanently denied

### Why use permissionsmanager?

To handle permisisons, we need to override the _onRequestPermissionsResult()_ method in our Activity/Fragment class to know which permissions did the user grant from the below dialog. 

<img src="https://github.com/Dehaat/permissionsmanager/blob/master/readmeimages/image1.png" height="400">

Similarly, we also had to override the _onActivityResult()_ method to check whether a user has granted permission after coming back from the settings application as shown below:

<img src="https://github.com/Dehaat/permissionsmanager/blob/master/readmeimages/image2.png" height="400">

All of this hassle is now handled by this permissions library. It internally makes use of the [ActivityResultContract](https://developer.android.com/training/basics/intents/result) API. It creates [ActivityResultLauncher](https://developer.android.com/reference/androidx/activity/result/ActivityResultLauncher) objects for launching the permissions rationale and application settings.

### How to use it in your project?

Add the following depenendency in your build.gradle file

```kotlin
implementation "com.github.Dehaat:permissionsmanager:version_name"
```

In your fragment/activity, inject PermissionsManager

```kotlin
@Inject
lateinit var permissionsManager: PermissionsManager
```

Now, create a launcher object and register it to the fragment/activity

```kotlin
private fun registerForPermissions() {
    val permissions = 
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        
    val permissionsLauncher: IPermissionsLauncher = 
        permissionsManager.getPermissionsLauncher(permissions, getPermissionsCallback())
        
    permissionsLauncher.register(this)
}

private fun getPermissionsCallback() = object : DefaultPermissionsResultCallback {

    override fun onPermissionsGranted() {
        // take picture from camera when every requested permissions is granted
    }

    override fun onPermissionsTemporarilyDenied(
        deniedPermissions: Array<String>,
        requestPermissions: () -> Unit
    ) {
        // show UI(e.g. a snackbar at bottom) which has an actionable clicking which
        // we can request permissions again by calling 'requestPermissions()' function
    }

    override fun onPermissionsPermanentlyDenied(
        deniedPermissions: Array<String>,
        openAppSettings: () -> Unit
    ) {
        // show UI(e.g. a snackbar at bottom) which has an actionable clicking which
        // we can take user to settings by calling 'openAppSettings()' function
    }
}
```

Also, don't forget to unregister it.

```kotlin
override fun onDestroyView() {
    super.onDestroyView()
    permissionsLauncher.unregister()
}
```

If you want to show an informative UI, to inform why you want to access the permissions, before requesting the them, then you should pass in `CustomPermissionsResultCallback` instead of `DefaultPermissionsResultCallback`.

```kotlin
private fun getPermissionsCallback() = object : CustomPermissionsResultCallback {

    override fun onPermissionsGranted() {
        // take picture from camera when every requested permissions is granted
    }

    override fun onPermissionsTemporarilyDenied(
        deniedPermissions: Array<String>,
        requestPermissions: () -> Unit
    ) {
        // show UI(e.g. a snackbar at bottom) which has an actionable clicking which
        // we can request permissions again by calling 'requestPermissions()' function
    }

    override fun onPermissionsPermanentlyDenied(
        deniedPermissions: Array<String>,
        openAppSettings: () -> Unit
    ) {
        // show UI(e.g. a snackbar at bottom) which has an actionable clicking which
        // we can take user to settings by calling 'openAppSettings()' function
    }
    
    override fun showCustomRationale(listener: InitiatePermissionListener) {
        // show a dialog/rationale providing information on why you need the permissions.
        // When the user clicks the button on this dialog/rationale, you can start the
        // permission-check flow again by calling listener.initiatePermissionRationale()
    }
}
```

### What else can we check with PermissionsManager?

We can check if a set of permissions are granted or not with _arePermissionsGranted()_ method<br>
**fun arePermissionsGranted(context: Context, permissions: Array\<String\>): Boolean**<br>
(Returns a boolean - true if all the requested permissions are granted, else false.)

We can also get a list of permissions that are granted out of the requested permissions with _getGrantedPermissions()_ method<br>
**fun getGrantedPermissions(context: Context, permissions: Array\<String\>): Array\<String\>**<br>
