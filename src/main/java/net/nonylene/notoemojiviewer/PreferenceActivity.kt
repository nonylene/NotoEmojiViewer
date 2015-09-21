package net.nonylene.notoemojiviewer

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceFragment
import android.view.View
import android.widget.TextView
import android.widget.Toast

public class PreferenceActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }

    public class SettingsFragment : PreferenceFragment() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.settings)

            findPreference("about_app").setOnPreferenceClickListener {
                AboutDialogFragment().show(fragmentManager, "about")
                false
            }
        }

        public class AboutDialogFragment : DialogFragment() {
            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                val builder = AlertDialog.Builder(activity)
                try {
                    val view = View.inflate(activity, R.layout.about_app, null)
                    val textView = view.findViewById(R.id.about_version) as TextView
                    val info = activity.packageManager.getPackageInfo(activity.packageName, 0)
                    textView.append(info.versionName)
                    builder.setView(view)
                            .setTitle(getString(R.string.about_app_dialogtitle))
                            .setPositiveButton(getString(android.R.string.ok), null)

                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Error occured", Toast.LENGTH_LONG).show()
                }

                return builder.create()
            }
        }
    }
}
