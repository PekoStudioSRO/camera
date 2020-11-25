package cz.pekostudio.camera.picker.methods

import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import cz.pekostudio.camera.picker.AbstractPictureSelect
import cz.pekostudio.camera.picker.FilePictureSelect
import cz.pekostudio.camera.picker.utils.hash
import java.io.File

/**
 * Created by Lukas Urbanek on 06/05/2020.
 */
class CameraMethod(override var selector: AbstractPictureSelect<*>) : PickMethod(selector) {

    private val instanceId: String = System.currentTimeMillis().toString().hash()
    private val imageFile = File(selector.activity.externalCacheDir, "camerapicture_${instanceId}.jpg")
    

    override fun onSelected(data: Intent?): ArrayList<File>? {
        imageFile.let { file ->
            return arrayListOf(file)
        }
    }

    override fun select(): CameraMethod {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .also { takePictureIntent ->
                takePictureIntent.resolveActivity(selector.activity.packageManager)?.also {
                    val photoURI: android.net.Uri = FileProvider.getUriForFile(
                        selector.activity,
                        selector.fileProvider,
                        imageFile
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
}