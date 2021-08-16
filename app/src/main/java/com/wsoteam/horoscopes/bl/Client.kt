package com.wsoteam.horoscopes.bl

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.utils.PreferencesProvider

class Client : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().acceptCookie()
        CookieManager.getInstance().flush()
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

        //Log.e("LOL", url!! + "GO TO")
        //sms:1252?body=BUN 9rszk31
        if (url!!.startsWith("sms:")) {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url!!))
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            App.getInstance().startActivity(intent)

        } else {
            view!!.loadUrl(url!!)
        }
        return true
    }

}