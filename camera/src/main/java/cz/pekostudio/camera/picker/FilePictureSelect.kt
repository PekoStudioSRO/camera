package cz.pekostudio.camera.picker

import androidx.appcompat.app.AppCompatActivity
import java.io.File

/**
 * Created by Lukas Urbanek on 05/05/2020.
 */
class FilePictureSelect(
    override val activity: AppCompatActivity,
    override val fileProvider: String
) : AbstractPictureSelect<File>(activity, fileProvider) {

    override fun onResult(files: ArrayList<File>): ArrayList<File> = files

}