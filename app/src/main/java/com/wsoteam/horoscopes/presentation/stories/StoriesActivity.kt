package com.wsoteam.horoscopes.presentation.stories

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.Today
import com.wsoteam.horoscopes.utils.convert.ActivityToBitmap
import com.wsoteam.horoscopes.utils.loger.L
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.stories_activity.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

class StoriesActivity : AppCompatActivity(R.layout.stories_activity) {

    var today : Today? = null
    var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = intent.getIntExtra(Config.ID_PRICE, -1)
        today = intent.getSerializableExtra(Config.SIGN_DATA) as Today

        ivSignStories.setImageResource(
            resources.obtainTypedArray(R.array.imgs_signs)
                .getResourceId(index, -1)
        )

        tvTopTitleStories.text = resources.getStringArray(R.array.names_signs)[index]
        tvTitleStories.text = "${getString(R.string.my_horoscope_on)} ${Calendar.getInstance().get(Calendar.DAY_OF_MONTH)} ${Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) }"
        //tvTextStories.text = getCutText(today!!.text)

    }


    private fun share(uri : Uri){
        var intent = Intent("com.instagram.share.ADD_TO_STORY")
        intent.putExtra("com.facebook.platform.extra.APPLICATION_ID", getString(R.string.facebook_app_id))
        intent.putExtra("content_url", "https://play.google.com/store/apps/details?id=com.vsoteam.movies")
        //intent.setDataAndType(uri, "MEDIA_TYPE_JPEG")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION


        val activity: Activity = this
        activity.startActivityForResult(intent, 0)
    }

    private fun saveScreenAndSend() {
        //shareImage(ActivityToBitmap.convert(this)!!)
        shareImage(convertToPng()!!)
    }

    private fun convertToPng() : Uri?{
        var view = findViewById<View>(R.id.llParentLayout)
        view.isDrawingCacheEnabled = true
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        view.buildDrawingCache()
        val uri = saveImage(view.drawingCache, this)
        view.isDrawingCacheEnabled = false
        return uri
    }

    private fun saveImage(bitmap: Bitmap, context: Context) : Uri?{
        var imagesFolder = File(context.cacheDir, "images")
        var uri : Uri? = null

        try {
            imagesFolder.mkdirs()
            var file = File(imagesFolder, "shared_image.png")

            var outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            uri = FileProvider.getUriForFile(context, "com.mydomain.fileprovider", file)
        }catch (ex : Exception){
            L.log("save error")
        }
        return uri
    }

    private fun getCutText(text: String): String {
        var array = text.split(" ")
        var cutString = ""
        for(i in 0..30){
            cutString = "$cutString${array[i]} "
        }
        cutString = "$cutString ..."
        return cutString
    }

    private fun shareImage(uri : Uri){
        var intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        startActivity(intent)
    }
}