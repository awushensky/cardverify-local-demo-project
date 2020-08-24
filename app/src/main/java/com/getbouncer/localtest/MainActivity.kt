package com.getbouncer.localtest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.getbouncer.cardscan.ScanActivity
import com.getbouncer.cardverify.ui.local.CardVerifyActivity
import com.getbouncer.cardverify.ui.local.CardVerifyActivityResultHandler
import kotlinx.android.synthetic.main.activity_main.*

private const val API_KEY = "<YOUR_API_KEY_HERE>"

class MainActivity : AppCompatActivity() {

    private var iin: String? = null
    private var lastFour: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CardVerifyActivity.warmUp(this, API_KEY)

        cardscanButton.setOnClickListener { ScanActivity.start(this@MainActivity, API_KEY) }
        cardverifyButton.setOnClickListener { CardVerifyActivity.start(this@MainActivity, iin=iin, lastFour=lastFour, enableExpiryExtraction = true, enableNameExtraction = true) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (ScanActivity.isScanResult(requestCode)) {
            if (resultCode == ScanActivity.RESULT_OK && data != null) {
                val scanResult = ScanActivity.creditCardFromResult(data)
                iin = scanResult?.number?.take(6)
                lastFour = scanResult?.last4()

                AlertDialog.Builder(this@MainActivity).setMessage("pan=${scanResult?.number}, iin=$iin, last4=$lastFour").show()
            } else if (resultCode == ScanActivity.RESULT_CANCELED) {
                val fatalError = data?.getBooleanExtra(ScanActivity.RESULT_FATAL_ERROR, false) ?: false
                if (fatalError) {
                    AlertDialog.Builder(this@MainActivity).setMessage("fatal error").show()
                } else {
                    AlertDialog.Builder(this@MainActivity).setMessage("user canceled").show()
                }
            }
        }

        if (CardVerifyActivity.isVerifyResult(requestCode)) {
            CardVerifyActivity.parseVerifyResult(resultCode, data, object : CardVerifyActivityResultHandler {
                override fun analyzerFailure() {
                    AlertDialog.Builder(this@MainActivity).setMessage("analyzer failure").show()
                }

                override fun cameraError() {
                    AlertDialog.Builder(this@MainActivity).setMessage("camera error").show()
                }

                override fun canceledUnknown() {
                    AlertDialog.Builder(this@MainActivity).setMessage("unknown cancellation").show()
                }

                override fun cardScanned(
                    cardPan: String,
                    cardName: String?,
                    cardExpiryMonth: Int?,
                    cardExpiryYear: Int?,
                    isCardValid: Boolean,
                    cardValidationFailureReason: String?
                ) {
                    AlertDialog.Builder(this@MainActivity).setMessage("pan=$cardPan\nvalid=$isCardValid").show()
                }

                override fun userCanceled() {
                    AlertDialog.Builder(this@MainActivity).setMessage("user canceled").show()
                }

                override fun userMissingCard() {
                    AlertDialog.Builder(this@MainActivity).setMessage("user missing card").show()
                }

            })
        }
    }
}