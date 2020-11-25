package cz.pekostudio.camera.picker.utils

import android.content.Context
import java.io.File

/**
 * Created by Lukas Urbanek on 22/09/2020.
 */

public fun clearImageCache(context: Context) {
    context.externalCacheDir?.deleteRecursively()
}