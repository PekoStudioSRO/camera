package cz.pekostudio.camera.picker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Lukas Urbanek on 05/05/2020.
 */
class BitmapPictureSelect(override val activity: AppCompatActivity, override val fileProvider: String) : AbstractPictureSelect<Bitmap>(activity, fileProvider) {

    override fun onResult(file: File): Bitmap = rotatedBitmapFromFile(file)

    private fun getRotationFromExif(rotation: Int): Int {
        return when (rotation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
    }

    private fun rotatedBitmapFromFile(file: File): Bitmap {
        val sourceBitmap = BitmapFactory.decodeFile(file.path)

        val rotation = getRotationFromExif(
            ExifInterface(file.path).getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
        )

        Matrix().let { rotationMatrix ->
            rotationMatrix.postRotate(rotation.toFloat())
            Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.width, sourceBitmap.height, rotationMatrix, true).let { rotatedBitmap ->
                return rotatedBitmap
            }
        }
    }

}