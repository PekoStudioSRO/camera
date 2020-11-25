package cz.pekostudio.camera.picker.ui

import android.Manifest
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import cz.pekostudio.camera.R
import cz.pekostudio.camera.picker.AbstractPictureSelect
import cz.pekostudio.camera.picker.methods.CameraMethod
import cz.pekostudio.camera.picker.methods.GalleryMethod
import cz.pekostudio.camera.picker.methods.PickMethod
import cz.pekostudio.camera.picker.utils.requiredPermissions


class PickerDialog(
    private val selector: AbstractPictureSelect<*>,
    private val config: Config,
    private val callback: (PickMethod) -> Unit,
    private val multiple: Boolean = false
) : Dialog(selector.activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(config.layout)
        config.background?.let {
            window?.setBackgroundDrawableResource(it)
        }
        findViewById<View>(R.id.camera_button).setOnClickListener {
            selector.activity.requiredPermissions(
                config.doNotCheckPermissions,
                config.permissionDeniedErrorMessage,
                Manifest.permission.CAMERA
            ) {
                callback(CameraMethod(selector))
                dismiss()
            }?.let {
                selector.permissionRequest = it
            }
            dismiss()
        }
        findViewById<View>(R.id.gallery_button).setOnClickListener {
            selector.activity.requiredPermissions(
                config.doNotCheckPermissions,
                config.permissionDeniedErrorMessage,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) {
                callback(GalleryMethod(selector, multiple))
                dismiss()
            }?.let {
                selector.permissionRequest = it
            }
            dismiss()
        }
    }

    data class Config(
        val layout: Int = R.layout.dialog_picker,
        val background: Int?= R.drawable.bg_dialog,
        val permissionDeniedErrorMessage: String = "Aplikace nemá oprávnění",
        val doNotCheckPermissions: Boolean = false
    )

}