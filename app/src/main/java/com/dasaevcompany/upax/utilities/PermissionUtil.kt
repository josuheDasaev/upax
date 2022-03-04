package com.dasaevcompany.upax.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dasaevcompany.upax.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PermissionUtil {

    private val deny = "DENY"
    private val requestDeny = "REQUEST_DENY"

    fun requestPermission(
        activity : Activity,
        request : ActivityResultLauncher<String>,
        permission : String ): Boolean {

        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:" + activity.packageName)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    return true
                }

                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    permission
                ) -> {
                    request.launch(permission)
                    pushPermissionKey(activity,permission, requestDeny)
                    return false
                }
                else -> {
                    return if (getPermissionKey(activity,permission) == requestDeny){

                        MaterialAlertDialogBuilder(activity)
                            .setIcon(R.drawable.ic_network)
                            .setTitle(activity.getString(R.string.permission_alert_config_title))
                            .setMessage(activity.getString(R.string.permission_alert_config_message))
                            .setPositiveButton(activity.getString(R.string.permission_alert_config_positive)) { _, _ ->
                                activity.startActivity(intent)
                                pushPermissionKey(activity,permission, requestDeny)
                            }.setNegativeButton(activity.getString(R.string.permission_alert_config_negative),null)
                            .show().setCancelable(false)
                          false
                    }else{
                        request.launch(permission)
                        pushPermissionKey(activity,permission, deny)
                        false
                    }
                }
            }
        } else {
            return if(ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) == PackageManager.PERMISSION_GRANTED){
                true
            } else {
                activity.startActivity(intent)
                false
            }
        }
    }

    fun pushPermissionKey(
        activity: Activity,
        permission: String,
        key: String ){

        val sharedPreference =  activity.getSharedPreferences(
            activity.getString(R.string.Permission_key),
            Context.MODE_PRIVATE)

        with(sharedPreference.edit()){
            putString(permission, key)
            apply()
        }
    }

    private fun getPermissionKey(
        activity: Activity,
        permission: String ): String{

        val sharedPreference =  activity.getSharedPreferences(
            activity.getString(R.string.Permission_key),
            Context.MODE_PRIVATE)

        return sharedPreference.getString(permission,deny).toString()
    }
}