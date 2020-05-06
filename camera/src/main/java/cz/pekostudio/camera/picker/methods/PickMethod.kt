package cz.pekostudio.camera.picker.methods

import android.content.Intent
import cz.pekostudio.camera.picker.AbstractPictureSelect
import cz.pekostudio.camera.picker.FilePictureSelect
import java.io.File

/**
 * Created by Lukas Urbanek on 05/05/2020.
 */
abstract class PickMethod(open val selector: AbstractPictureSelect<*>) {

    abstract fun onSelected(data: Intent?): File?

    abstract fun select(): PickMethod

}