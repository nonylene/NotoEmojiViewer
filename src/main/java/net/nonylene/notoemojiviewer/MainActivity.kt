package net.nonylene.notoemojiviewer

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.annotations.Nullable

public class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Intent.ACTION_SEND == intent.action && "text/plain" == intent.type) {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                showToast(it)
                finish()
                return
            }
        }

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("clip_mode", false)) {
            readFromClipBoard()?.let {
                showToast(it)
                finish()
                return
            }
        }

        startActivity(Intent(this, PreferenceActivity::class.java))
        finish()
    }

    private fun showToast(text : String) {
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_LONG)
        val textView = (toast.view as LinearLayout).getChildAt(0) as TextView
        try {
            textView.typeface = Typeface.createFromAsset(assets, "NotoColorEmoji.ttf")
        } catch (e: RuntimeException) {
            textView.typeface = Typeface.createFromAsset(assets, "NotoEmoji-Regular.ttf")
        }
        toast.show()

    }

    @Nullable
    private fun readFromClipBoard() : String? {
        var clipText : String? = null
        (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip?.let{
            val item = it.getItemAt(0)
            item.text?.let { text -> clipText = text.toString() }
            item.uri?.let { uri -> clipText = uri.toString() }
        }
        return clipText
    }
}
