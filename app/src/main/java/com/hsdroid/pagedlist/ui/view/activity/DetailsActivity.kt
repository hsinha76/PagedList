package com.hsdroid.pagedlist.ui.view.activity

import android.os.Build
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import com.hsdroid.pagedlist.R
import com.hsdroid.pagedlist.data.models.ServerResponse
import com.hsdroid.pagedlist.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Details"
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
        }

        //Data from home activity here
        val receivedData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("details", ServerResponse::class.java)
        } else {
            intent.getParcelableExtra("details")
        }

        initUi(receivedData)
    }

    private fun initUi(receivedData: ServerResponse?) {
        with(binding) {

            toolbar.setNavigationOnClickListener {
                finish()
            }

            receivedData?.let {
                tvTitle.text = it.title
                tvBody.text = it.body
                tvIds.text = buildSpannedString {
                    append("#" + it.id)
                    append(" | ")

                    inSpans(
                        ForegroundColorSpan(
                            ContextCompat.getColor(
                                this@DetailsActivity,
                                R.color.colorAccent
                            )
                        )
                    ) {
                        append("UID ")
                    }

                    append(it.userId.toString())
                }
            }
        }
    }
}