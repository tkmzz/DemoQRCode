package br.com.luiz.demoqrcode

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_qrcode_reader.*
import me.dm7.barcodescanner.zxing.ZXingScannerView



class QRCodeReaderActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private val REQUEST_CAMERA = 212

    override fun handleResult(rawResult: Result?) {
        val codigoVideoNoYoutube = rawResult?.text.toString()
        mScannerView.resumeCameraPreview(this)
        val intent = Intent(this, VideoActivity::class.java)
        intent.putExtra("YOUTUBE_CODE", codigoVideoNoYoutube)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_reader)

        checkPermission()
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            mScannerView.setResultHandler(this)
            mScannerView.startCamera()
        } else {
            checkPermission()
        }

    }

    private fun checkPermission() {
        val permission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    )
            ) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Necessária a permissao para Camera")
                        .setTitle("Permissao Requerida")
                builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    requestPermission()
                })
                val dialog = builder.create()
                dialog.show()
            } else {
                requestPermission()
            }
        }

    }

    protected fun requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA
        )
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (grantResults.size == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissao negada", Toast.LENGTH_SHORT).show()
                } else {
                }
                return
            }
        }
    }
}
