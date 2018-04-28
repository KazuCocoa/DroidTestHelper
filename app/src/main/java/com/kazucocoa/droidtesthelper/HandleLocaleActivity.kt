package com.kazucocoa.droidtesthelper

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.kazucocoa.droidtesthelperlib.HandleLocale

import java.util.Locale

class HandleLocaleActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handle_locale)

        val handleLocale = HandleLocale()

        val textView = findViewById<View>(R.id.set_for_change_locale_text) as TextView

        if (hasExtraRegardingLocal(this.intent)) {
            val lang = this.intent.getStringExtra(languageExt)
            val coun = this.intent.getStringExtra(countryExt)

            textView.text = "set with: ${lang}_$coun"
            Locale(lang, coun).let { handleLocale.setLocale(it) }
        } else {
            textView.text = "set with: en_US(Default)"
            Locale("en", "US").let { handleLocale.setLocale(it) }
        }
    }

    companion object {

        private val TAG = HandleLocaleActivity::class.java.simpleName

        private const val languageExt = "LANG"

        private const val countryExt = "COUNTRY"

        fun hasExtraRegardingLocal(intent: Intent): Boolean {
            return intent.hasExtra(languageExt) && intent.hasExtra(countryExt)
        }

        fun buildLaunchHandleLocalActivityIntent(context: Context, intent: Intent): Intent {
            val returnIntent = Intent(context, HandleLocaleActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                action = Intent.ACTION_VIEW
            }

            if (!hasExtraRegardingLocal(intent)) {
                return returnIntent.apply {
                    putExtra(languageExt, "en")
                    putExtra(countryExt, "US")
                }
            }

            val language = intent.getStringExtra(languageExt)
            val country = intent.getStringExtra(countryExt)

            return returnIntent.apply {
                putExtra(languageExt, language)
                putExtra(countryExt, country)
            }
        }
    }
}
