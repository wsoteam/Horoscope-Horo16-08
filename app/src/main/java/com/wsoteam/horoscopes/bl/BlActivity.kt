package com.wsoteam.horoscopes.bl

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import com.google.android.gms.common.api.Api
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.activity_bl.*

class BlActivity : AppCompatActivity(R.layout.activity_bl) {

    lateinit var webBlack: WebView

    var counterBack = 0
    val MAX_BEFORE_SKIP = 2

    private var mUploadMessage: ValueCallback<Array<Uri>>? = null

    private val IMG_PICK = 1

    private val URLL = "https://www.youtube.com"  //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createUI()
       // NotifManager.setAlarm(this)

        webBlack.settings.allowFileAccess = true
        webBlack.settings.allowFileAccess = true
        webBlack.settings.allowContentAccess = true
        webBlack.settings.supportZoom()
        webBlack.settings.useWideViewPort = true

        webBlack.settings.javaScriptEnabled = true
        webBlack.settings.domStorageEnabled = true
        webBlack.settings.userAgentString =
            webBlack.settings.userAgentString + "MobileAppClient/Android/0.9"
        webBlack.webViewClient = Client()


        if (savedInstanceState == null) {
            if (PreferencesProvider.lastUrl == "") {
                var url = PreferencesProvider.url
                webBlack.loadUrl(url)//URLL//url
               // Log.e("LOOL", "url:  $url")
            } else {
                var url = PreferencesProvider.lastUrl
                webBlack.loadUrl(url)//URLL//url
            }
        }

        webBlack.webChromeClient = object : WebChromeClient() {

            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                mUploadMessage = filePathCallback
                val pickIntent = Intent()
                pickIntent.type = "image/*"
                pickIntent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), IMG_PICK)
                return true
            }
        }
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        var results = arrayOf(Uri.parse(data!!.dataString))
        mUploadMessage!!.onReceiveValue(results)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createUI() {
        webBlack = WebView(this)

        webBlack.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        fl_black_activity.addView(webBlack)
    }
    override fun onBackPressed() {
        if (webBlack.canGoBack()) {
            webBlack.goBack()
        } else {
            counterBack ++
            if (counterBack >= MAX_BEFORE_SKIP) {
                counterBack = 0
                var url = PreferencesProvider.startUrl
                webBlack.loadUrl(url)
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webBlack.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webBlack.restoreState(savedInstanceState)
    }
}
