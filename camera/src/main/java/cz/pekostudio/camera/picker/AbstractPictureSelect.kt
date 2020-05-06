package cz.pekostudio.camera.picker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import cz.pekostudio.camera.picker.methods.PickMethod
import cz.pekostudio.camera.picker.ui.PickerBottomSheet
import cz.pekostudio.camera.picker.ui.PickerDialog
import java.io.File

/**
 * Created by Lukas Urbanek on 05/05/2020.
 */
abstract class AbstractPictureSelect<R>(open val activity: AppCompatActivity, open val fileProvider: String) {

    companion object {
        const val REQUEST_CODE = 828
    }

    private var pickMethod: PickMethod? = null
    private var callback: ((file: R) -> Unit)? = null

    private val onMethodSelected: (method: PickMethod) -> Unit = { pickMethod = it.select() }

    fun pickImage(fragmentManager: FragmentManager = activity.supportFragmentManager, config: PickerBottomSheet.Config = PickerBottomSheet.Config(), function: (result: R) -> Unit) {
        callback = function
        PickerBottomSheet(this, config, onMethodSelected).show(fragmentManager, "")
    }

    fun pickImageViaDialog(config: PickerDialog.Config = PickerDialog.Config(), function: (result: R) -> Unit) {
        callback = function
        PickerDialog(this, config, onMethodSelected).show()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE -> {
                    pickMethod?.onSelected(data)?.let {
                        callback?.invoke(onResult(it))
                    }
                }
            }
        }
    }

    abstract fun onResult(file: File): R

}