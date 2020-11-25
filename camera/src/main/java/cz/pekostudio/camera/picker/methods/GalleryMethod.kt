package cz.pekostudio.camera.picker.methods

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import cz.pekostudio.camera.picker.AbstractPictureSelect
import cz.pekostudio.camera.picker.FilePictureSelect
import java.io.File
import java.util.ArrayList

/**
 * Created by Lukas Urbanek on 06/05/2020.
 */
class GalleryMethod(
    override var selector: AbstractPictureSelect<*>,
    private val multiple: Boolean
) : PickMethod(selector) {

    override fun onSelected(data: Intent?): ArrayList<File>? {
        val bitmaps = ArrayList<String>()

        val uris = ArrayList<Uri>().apply {
            data?.clipData?.let {
                for (i in 0 until it.itemCount) {
                    add(it.getItemAt(i).uri)
                }
            } ?: run {
                data?.data?.let(::add)
            }
        }

        uris.forEach {
            selector.activity.contentResolver.query(
                it,
                arrayOf(MediaStore.Images.Media.DATA),
                null,
                null,
                null
            )?.apply {
                moveToFirst()
                bitmaps.add(getString(getColumnIndex(MediaStore.Images.Media.DATA)))
                close()
            }
        }

        return ArrayList<File>().apply {
            bitmaps.forEach {
                add(File(it))
            }
        }
    }

    override fun select(): GalleryMethod {
        Intent().run {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multiple)
            action = Intent.ACTION_PICK
            this@GalleryMethod.selector.activity.startActivityForResult(this, AbstractPictureSelect.REQUEST_CODE)
        }
        return this
    }
}