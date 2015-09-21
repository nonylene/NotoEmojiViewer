package net.nonylene.notoemojiviewer

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

public class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Intent.ACTION_SEND == intent.action && "text/plain" == intent.type) {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                val toast = Toast.makeText(applicationContext, it, Toast.LENGTH_LONG)
                val textView = (toast.view as LinearLayout).getChildAt(0) as TextView
                textView.typeface = Typeface.createFromAsset(assets, "NotoColorEmoji.ttf")
                toast.show()
            }
        }
        finish()
    }
}
