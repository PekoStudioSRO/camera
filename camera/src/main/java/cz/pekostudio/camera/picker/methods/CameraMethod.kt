package cz.pekostudio.camera.picker.methods

import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import cz.pekostudio.camera.picker.AbstractPictureSelect
import cz.pekostudio.camera.picker.FilePictureSelect
import java.io.File

/**
 * Created by Lukas Urbanek on 06/05/2020.
 */
class CameraMethod(override var selector: AbstractPictureSelect<*>) : PickMethod(selector) {

    override fun onSelected(data: Intent?): File? {
        File(
            selector.activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "last.jpg"
        ).let { file ->
            return file
        }
    }

    override fun select(): CameraMethod {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .also { takePictureIntent ->
                takePictureIntent.resolveActivity(selector.activity.packageManager)?.also {
                    val photoURI: android.net.Uri = FileProvider.getUriForFile(
                        selector.activity,
                        selector.fileProvider,
                        createImageFile()
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    androidx.core.app.ActivityCompat.startActivityForResult(
                        selector.activity,
                        takePictureIntent,
                        AbstractPictureSelect.REQUEST_CODE,
                        null
                    )
                }
            }
        return this
    }


    private fun createImageFile(): File {
        return File(selector.activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "last.jpg")
    }
}