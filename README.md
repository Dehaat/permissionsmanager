## How does permissionsmanager work? How you can use it in your Android project?

This library contains the logic to:
* Check permissions
* Do the specified action when all permissions are granted
* Request permissions if they are not granted
* Take the user to app settings when all the remaining permissions are permanently denied

### How does the library work internally?

Earlier, we used to override the onRequestPermissionsResult() method in our Activity/Fragment class to know which permissions did the user grant from the below dialog. 

<img src="https://github.com/Dehaat/permissionsmanager/blob/master/readmeimages/image1.png" height="400">

Similarly, we also had to override the onActivityResult() method to check whether a user has granted permission after coming back from the settings application as shown below:

<img src="https://github.com/Dehaat/permissionsmanager/blob/master/readmeimages/image2.png" height="400">

All of this hassle is now handled by this permissions library. It internally makes use of the [ActivityResultContract](https://developer.android.com/training/basics/intents/result) API. It creates [ActivityResultLauncher](https://developer.android.com/reference/androidx/activity/result/ActivityResultLauncher) objects for launching the permissions rationale and application settings.

### How to create these launchers with the help of the library?

<img src="https://github.com/Dehaat/permissionsmanager/blob/master/readmeimages/image3.png" height="300">

There are 2 types of launchers available in the module:

1. **DefaultPermissionsLauncher**- Use this when you just want to check if a list of permissions is granted. If granted then what action will you perform? If not granted, then what UI will you show to nudge the user to enable permissions(e.g. a snack bar at the bottom of the screen)

2. **CustomPermissionsLauncher**- This has one additional functionality over the DefaultPermissionsLauncher. With this, you can also specify your own informative UI(to tell why you want to access the permissions) before requesting the permissions.

### How to use a launcher?

The launcher will help us check the permissions, perform our task once the permissions are granted, and show a UI for enabling permissions in case they are not granted.

First, you create a launcher object with the help of PermissionsManager. Then, you register it. You can register it from an Activity or a Fragment. For Activities, be careful that you register it before onStart(), otherwise, it will throw the following error:

> java.lang.IllegalStateException: LifecycleOwner is attempting to register while the current state is STARTED. LifecycleOwners must call register before they are STARTED.

Now, call checkPermissions() at runtime, i.e., on button click, or any other user action.

In the end, don’t forget to unregister it to prevent memory leaks.

### Functions in IPermissionsLauncher:

1.  **fun register(fragment: Fragment)**<br>Registers a launcher to a Fragment.

2.  **fun register(activity: ComponentActivity)**<br>Registers a launcher to an Activity.

3.  **fun unregister()**<br>Unregisters a launcher from an Activity/Fragment.

4.  **fun checkPermissions()**<br>Initiates permission checks.(e.g. call this method on click of a button that takes pictures, records audio, etc.)

### How to integrate it with your code?

All the functionality of the library is exposed through the **PermissionsManager** class. You have to inject this in the class where you want to check permissions.

<img src="https://github.com/Dehaat/permissionsmanager/blob/master/readmeimages/image4.png" height="200">

This class provides the following functionalities:

1.  **fun getPermissionsLauncher(permissions: Array\<String\>, callback: DefaultPermissionsResultCallback): DefaultPermissionsLauncher**<br>
Returns a DefaultPermissionslauncher object. We need to pass an Array of permissions that we want to check. Use this function when you don’t want to show an informative UI(why grant the permissions) before requesting the permissions from the user.

2.  **fun getPermissionsLauncher(permissions: Array\<String\>, callback: CustomPermissionsResultCallback): CustomPermissionsLauncher**<br>
Returns a CustomPermissionslauncher object. Use this function when you want to show an informative UI(why grant the permissions) before requesting the permissions from the user.

3.  **fun arePermissionsGranted(context: Context, permissions: Array\<String\>): Boolean**<br>
Returns a boolean - true if all the requested permissions are granted, else false.

4.  **fun getGrantedPermissions(context: Context, permissions: Array\<String\>): Array\<String\>**<br>
Returns the list of permissions that are granted out of the requested permissions.

### PermissionsResultCallback

<img src="https://github.com/Dehaat/permissionsmanager/blob/master/readmeimages/image5.png" height="220">

While creating a launcher(DefaultPermissionsLauncher or CustomPermissionslauncher), you have to pass a callback object(refer to the function signatures above). There are 2 types of callback for 2 types of Launchers: 

1. **DefaultPermissionsResultCallback** for DefaultPermissionsLauncher 
2. **CustomPermissionsResultCallback** for CustomPermissionsLauncher 

### You have to implement the following functions for DefaultPermissionsResultCallback:

1. **fun onPermissionsGranted()**<br>
Perform the actions here when the permissions are granted. e.g. take photos, record audio, capture locations. 

2.  **fun onPermissionsTemporarilyDenied(deniedPermissions: Array\<String\>, requestPermissions: () -> Unit)**<br>
When the permissions are temporarily denied(Never ask again is false), show a UI to nudge the user to enable the permissions. To reinitiate the permission checks(e.g., click the ‘Allow’ button on snack bar), call the requestPermissions() function. 

3.  **fun onPermissionsPermanentlyDenied(deniedPermissions: Array\<String\>, openAppSettings: () -> Unit)**<br>
When the permissions are permanently denied(Never ask again is true), show a UI to nudge the user to enable the permissions. To enable the permission(e.g., click the ‘Allow’ button on snack bar), call the openAppSettings() function to take the user to the Settings page of our app.

4.  **fun onUnregister()**<br>
Here you can do cleanup when the launchers are unregistering. e.g. You can dismiss dialogs, snack bars, etc. anything dependent on permission checks.

### For CustomPermissionsResultCallback, you have to implement the above functions plus one more mentioned below:

**fun showCustomRationale(listener: InitiatePermissionListener)**<br>
Shows your informative UI giving a reason why you require the permissions. If your informative UI has an ‘OK’ button or something similar, then call initiatePermissionRationale() method of the listener provided here, and the library will take care of the rest of the flow.
