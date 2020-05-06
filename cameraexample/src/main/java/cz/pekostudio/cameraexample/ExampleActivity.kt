package cz.pekostudio.cameraexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import cz.pekostudio.camera.picker.BitmapPictureSelect

class ExampleActivity : AppCompatActivity() {

    private lateinit var pictureSelect: BitmapPictureSelect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        pictureSelect = BitmapPictureSelect(
            this,
            "cz.pekostudio.cameraexample.fileprovider"
        )

        val image = findViewById<ImageView>(R.id.image)

        findViewById<View>(R.id.select).setOnClickListener {
            pictureSelect.pickImage {
                image.setImageBitmap(it)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        pictureSelect.onActivityResult(requestCode, resultCode, data)
    }
}
