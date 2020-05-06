package cz.pekostudio.camera.picker.ui

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


class PickerBottomSheet(private val selector: AbstractPictureSelect<*>, private val config: Config, private val callback: (PickMethod) -> Unit) : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config.style?.let {
            setStyle(STYLE_NORMAL, it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(config.layout, container, false).apply {
            findViewById<View>(R.id.camera_button).setOnClickListener {
                callback(CameraMethod(selector))
                dismiss()
            }
            findViewById<View>(R.id.gallery_button).setOnClickListener {
                callback(GalleryMethod(selector))
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.findViewById<View>(com.google.android.material.R.id.container)?.run {
            fitsSystemWindows = false
            ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
                ((this as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(0).setPadding(0, 0, 0, insets.systemWindowInsetBottom)
                insets.consumeSystemWindowInsets()
            }
            ViewCompat.requestApplyInsets(this)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog?.window?.decorView?.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    data class Config(val layout: Int = R.layout.dialog_picker, val style: Int? = R.style.DefaultBottomSheetTheme)

}