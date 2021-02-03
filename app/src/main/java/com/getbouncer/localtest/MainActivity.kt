package com.getbouncer.localtest

import android.content.Intent
//import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
//import com.getbouncer.cardscan.ScanActivity
import com.getbouncer.cardscan.ui.local.CardScanActivity
import com.getbouncer.cardscan.ui.local.CardScanActivityResult
import com.getbouncer.cardscan.ui.local.CardScanActivityResultHandler
//import com.getbouncer.liveness.ui.LivenessActivity
//import com.getbouncer.liveness.ui.LivenessActivityResultHandler
//import com.getbouncer.liveness.ui.card.CardSide
//import com.getbouncer.liveness.ui.card.FRONT
//import com.getbouncer.cardverify.ui.network.CardVerifyActivity
//import com.getbouncer.cardverify.ui.network.CardVerifyActivityResult
//import com.getbouncer.cardverify.ui.network.CardVerifyActivityResultHandler
import com.getbouncer.localtest.databinding.ActivityMainBinding
import com.getbouncer.scan.framework.Config

private const val API_KEY = "pprgvfjEcoWWNprYpvForyd0qGZG0_g8"

class MainActivity : AppCompatActivity() {

    private var iin: String? = null
    private var lastFour: String? = null

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CardVerifyActivity.warmUp(this, API_KEY, true)

        Config.isDebug = true

//        binding.cardscanButton.setOnClickListener { ScanActivity.start(this@MainActivity, API_KEY) }
        binding.cardscanButton.setOnClickListener { CardScanActivity.start(this@MainActivity, API_KEY) }
//        binding.cardverifyButtonLocal.setOnClickListener { com.getbouncer.cardverify.ui.local.CardVerifyActivity.start(this@MainActivity, iin = iin, lastFour = lastFour, enableEnterCardManually = true, enableMissingCard = true, enableExpiryExtraction = true, enableNameExtraction = true) }
//        binding.cardverifyButtonNetwork.setOnClickListener { CardVerifyActivity.start(activity = this@MainActivity, apiKey = API_KEY, iin = iin, lastFour = lastFour, enableEnterCardManually = true, enableMissingCard = true, enableNameExtraction = true, enableExpiryExtraction = true) }
//        binding.cardverifyButtonZeroFraud.setOnClickListener { com.getbouncer.cardverify.ui.zerofraud.CardVerifyActivity.start(activity = this@MainActivity, apiKey = API_KEY, userId = "user_id", enableEnterCardManually = true, enableNameExtraction = true, enableExpiryExtraction = true) }
//        binding.livenessButton.setOnClickListener { LivenessActivity.start(this@MainActivity, API_KEY, side = FRONT, cardIin = iin ?: "", cardLastFour = lastFour ?: "") }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (CardScanActivity.isScanResult(requestCode)) {
            CardScanActivity.parseScanResult(resultCode, data, object : CardScanActivityResultHandler {
                override fun analyzerFailure(scanId: String?) {
                    AlertDialog.Builder(this@MainActivity).setMessage("analyzer failure").show()
                }

                override fun cameraError(scanId: String?) {
                    AlertDialog.Builder(this@MainActivity).setMessage("camera error").show()
                }

                override fun canceledUnknown(scanId: String?) {
                    AlertDialog.Builder(this@MainActivity).setMessage("unknown cancellation").show()
                }

                override fun cardScanned(scanId: String?, scanResult: CardScanActivityResult) {
                    AlertDialog.Builder(this@MainActivity).setMessage("pan=${scanResult.pan}\nnetwork=${scanResult.networkName}").show()
                }

                override fun enterManually(scanId: String?) {
                    AlertDialog.Builder(this@MainActivity).setMessage("enter manually").show()
                }

                override fun userCanceled(scanId: String?) {
                    AlertDialog.Builder(this@MainActivity).setMessage("user canceled").show()
                }

            })
        }

//        if (ScanActivity.isScanResult(requestCode)) {
//            if (resultCode == ScanActivity.RESULT_OK && data != null) {
//                val scanResult = ScanActivity.creditCardFromResult(data)
//                iin = scanResult?.number?.take(6)
//                lastFour = scanResult?.last4()
//
//                AlertDialog.Builder(this@MainActivity).setMessage("pan=${scanResult?.number}, iin=$iin, last4=$lastFour").show()
//            } else if (resultCode == ScanActivity.RESULT_CANCELED) {
//                val fatalError = data?.getBooleanExtra(ScanActivity.RESULT_FATAL_ERROR, false) ?: false
//                if (fatalError) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("fatal error").show()
//                } else {
//                    AlertDialog.Builder(this@MainActivity).setMessage("user canceled").show()
//                }
//            }
//        }

//        if (com.getbouncer.cardverify.ui.local.CardVerifyActivity.isVerifyResult(requestCode)) {
//            com.getbouncer.cardverify.ui.local.CardVerifyActivity.parseVerifyResult(resultCode, data, object : com.getbouncer.cardverify.ui.local.CardVerifyActivityResultHandler {
//                override fun analyzerFailure() {
//                    AlertDialog.Builder(this@MainActivity).setMessage("analyzer failure").show()
//                }
//
//                override fun cameraError() {
//                    AlertDialog.Builder(this@MainActivity).setMessage("camera error").show()
//                }
//
//                override fun canceledUnknown() {
//                    AlertDialog.Builder(this@MainActivity).setMessage("unknown cancellation").show()
//                }
//
//                override fun cardScanned(
//                    cardPan: String,
//                    cardName: String?,
//                    cardExpiryMonth: String?,
//                    cardExpiryYear: String?,
//                    isCardValid: Boolean,
//                    cardValidationFailureReason: String?,
//                    cardValidationDetails: String?,
//                ) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("pan=$cardPan\nexpriy=$cardExpiryMonth/$cardExpiryYear\nname=$cardName\nvalid=$isCardValid\n\ndetails=$cardValidationDetails").show()
//                }
//
//                override fun enterManually() {
//                    AlertDialog.Builder(this@MainActivity).setMessage("enter manually").show()
//                }
//
//                override fun userCanceled() {
//                    AlertDialog.Builder(this@MainActivity).setMessage("user canceled").show()
//                }
//
//                override fun userMissingCard() {
//                    AlertDialog.Builder(this@MainActivity).setMessage("user missing card").show()
//                }
//            })
//        }
//
//        if (CardVerifyActivity.isVerifyResult(requestCode)) {
//            CardVerifyActivity.parseVerifyResult(resultCode, data, object : CardVerifyActivityResultHandler {
//                override fun analyzerFailure(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("analyzer failure").show()
//                }
//
//                override fun cameraError(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("camera error").show()
//                }
//
//                override fun canceledUnknown(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("unknown cancellation").show()
//                }
//
//                override fun cardScanned(
//                    scanId: String?,
//                    instanceId: String?,
//                    scanResult: CardVerifyActivityResult,
//                    payloadVersion: Int,
//                    encryptedPayload: String
//                ) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("pan=${scanResult.pan}\nexpriy=${scanResult.expiryMonth}/${scanResult.expiryYear}\nname=${scanResult.legalName}").show()
//                }
//
//                override fun enterManually(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("enter manually").show()
//                }
//
//                override fun userCanceled(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("user canceled").show()
//                }
//
//                override fun userMissingCard(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("user missing card").show()
//                }
//            })
//        }
//
//        if (com.getbouncer.cardverify.ui.zerofraud.CardVerifyActivity.isVerifyResult(requestCode)) {
//            com.getbouncer.cardverify.ui.zerofraud.CardVerifyActivity.parseVerifyResult(resultCode, data, object : com.getbouncer.cardverify.ui.zerofraud.CardVerifyActivityResultHandler {
//                override fun analyzerFailure(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("analyzer failure").show()
//                }
//
//                override fun cameraError(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("camera error").show()
//                }
//
//                override fun canceledUnknown(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("unknown cancellation").show()
//                }
//
//                override fun cardScanned(
//                    scanId: String?,
//                    instanceId: String?,
//                    scanResult: com.getbouncer.cardverify.ui.zerofraud.CardVerifyActivityResult
//                ) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("pan=${scanResult.pan}\nexpriy=${scanResult.expiryMonth}/${scanResult.expiryYear}\nname=${scanResult.legalName}").show()
//                }
//
//                override fun enterManually(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("enter manually").show()
//                }
//
//                override fun userCanceled(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("user canceled").show()
//                }
//            })
//        }

//        if (LivenessActivity.isLivenessResult(requestCode)) {
//            LivenessActivity.parseLivenessResult(resultCode, data, object : LivenessActivityResultHandler {
//                override fun analyzerFailure(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("analyzer failure").show()
//                }
//
//                override fun cameraError(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("camera error").show()
//                }
//
//                override fun canceledUnknown(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("unknown cancellation").show()
//                }
//
//                override fun cardScanned(
//                    instanceId: String?,
//                    scanId: String?,
//                    payloadVersion: Int,
//                    encryptedPayload: String,
//                    cardPan: String?,
//                    @CardSide cardSide: Int,
//                    cardImage: Bitmap?
//                ) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("pan=$cardPan\nside=$cardSide").show()
//                }
//
//                override fun userCanceled(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("user canceled").show()
//                }
//
//                override fun missingCard(scanId: String?) {
//                    AlertDialog.Builder(this@MainActivity).setMessage("user missing card").show()
//                }
//            })
//        }
    }
}