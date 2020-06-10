package com.fiserv.fplogintest

import android.app.Activity
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val github = "https://andyrohan-fiserv.github.io"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWebView()

        val jsToRun = "javascript:FA.login('lorum', 'ipsum');"

        loadBtn.setOnClickListener {
            mainWebview.loadUrl(jsToRun)

            toast("Submitted FP data via loadURL method", Toast.LENGTH_LONG)
        }

        evalBtn.setOnClickListener {
            mainWebview.evaluateJavascript(jsToRun, null)

            toast("Submitted FP data via evaluateJavascript method", Toast.LENGTH_LONG)
        }

        website1.setOnClickListener {
            mainWebview.loadUrl(github)

            toast("Loading $github", Toast.LENGTH_SHORT)
        }

        versionTv.text = mainWebview.settings.userAgentString
    }

    private fun toast(message: String, length: Int) {
        Toast.makeText(applicationContext, message, length).show()
    }

    private fun initWebView() {
        mainWebview.webViewClient = MyWebViewClient(this)
        mainWebview.settings.cacheMode = WebSettings.LOAD_NO_CACHE

        CookieManager.getInstance().setAcceptThirdPartyCookies(mainWebview, true)

        WebView.setWebContentsDebuggingEnabled(true)

        mainWebview.settings.setGeolocationEnabled(true)
        mainWebview.settings.setJavaScriptEnabled(true)

        mainWebview.loadUrl(github)
    }

    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = request?.url

            view?.loadUrl(url.toString())
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }
}
