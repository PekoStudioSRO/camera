package cz.pekostudio.camera.picker.methods

import android.content.Intent
import android.provider.MediaStore
import cz.pekostudio.camera.picker.AbstractPictureSelect
import cz.pekostudio.camera.picker.FilePictureSelect
import java.io.File
import java.util.ArrayList

/**
 * Created by Lukas Urbanek on 06/05/2020.
 */
class GalleryMethod(override var selector: AbstractPictureSelect<*>) : PickMethod(selector) {

    override fun onSelected(data: Intent?): File? {
        val bitmaps = ArrayList<String>()

        val clipData = data?.clipData ?: return null

        for (i in 0 until clipData.itemCount) {
            selector.activity.contentResolver.query(
                clipData.getItemAt(i).uri,
                arrayOf(MediaStore.Images.Media.DATA),
                null,
                null,
                null
            )?.apply {
                moveToFirst()
                bitmaps.add(getString(getColumnIndex(MediaStore.Images.Media.DATA)))
                close()
            } ?: continue
        }

        bitmaps.firstOrNull()?.let {
            return File(it)
        }
        return null
    }

    override fun select(): GalleryMethod {
        Intent().run {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            action = Intent.ACTION_PICK
            this@GalleryMethod.selector.activity.startActivityForResult(this, AbstractPictureSelect.REQUEST_CODE)
        }
        return this
    }
}