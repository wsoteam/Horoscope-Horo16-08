package com.wsoteam.horoscopes.utils.convert

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.wsoteam.horoscopes.utils.loger.L
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

object ActivityToBitmap {

    fun convert(activity : AppCompatActivity) : Uri? {
        var view = activity.window.decorView.findViewById<View>(android.R.id.content)
        view.isDrawingCacheEnabled = true
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        view.buildDrawingCache()
        val uri = saveImage(view.drawingCache, activity.applicationContext)
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
}