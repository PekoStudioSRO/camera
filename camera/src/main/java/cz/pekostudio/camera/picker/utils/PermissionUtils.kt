package cz.pekostudio.camera.picker.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.random.Random

/**
 * Created by Lukas Urbanek on 02/09/2020.
 */

private fun Context.isGranted(permissions: Array<out String>): Boolean {
    permissions.forEach {
        if (ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

private fun Activity.request(permissions: Array<out String>, requestCode: Int) {
    ActivityCompat.requestPermissions(this, permissions, requestCode)
}

internal data class PermissionRequest(
    val requestCode: Int,
    val block: () -> Unit,
    val errorText: String
)

internal fun Activity.requiredPermissions(
    doNotCheck: Boolean = false,
    errorText: String,
    vararg permissions: String,
    block: () -> Unit
): PermissionRequest? {
    return if (doNotCheck || isGranted(permissions)) {
        block()
        null
    } else {
        PermissionRequest(
            requestCode = Random.nextInt(256, 512),
            block = block,
            errorText = errorText
        ).apply {
            request(permissions, requestCode)
        }
    }
}