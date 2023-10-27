package com.selpar.selparbulut.ui

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import android.print.pdf.PrintedPdfDocument
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import android.graphics.pdf.PdfDocument.PageInfo
import android.graphics.pdf.PdfDocument
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.selpar.selparbulut.R
import com.selpar.selparbulut.data.Alert
import java.io.BufferedOutputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Random

class FaturaPdfGosterActivity : AppCompatActivity() {
    lateinit var firma_id: String
    lateinit var pdfViewer: PDFView
    lateinit var imageyazdir: ImageView
    lateinit var btn_mail_gonder: ImageView
    lateinit var btn_telegram_gonder: ImageView
    lateinit var btn_whatsapp: ImageView
    lateinit var ivBack: ImageView
    lateinit var progressBar: ProgressBar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fatura_pdf_goster)
        onBaslat()
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        progressBar = findViewById(R.id.progressBar)
        startLoadingAnimation()
        Toast.makeText(this,getString(R.string.suanentegratorebaglaniyor),Toast.LENGTH_LONG).show()
        /*
        mBack.setOnClickListener {
            val i= Intent(this,KesilenFaturalarActivity::class.java)
            i.putExtra("dilsecimi",intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra("firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("gorev",intent.getStringExtra("gorev"))
            //startActivity(i)
        }*/
        Log.d("FATURA: ", intent.getStringExtra("path").toString())
        val address = Uri.parse(
            "https://pratikhasar.com/netting/e_giden_pdf.php?firma_id=" + "1" + "&InvoiceUUID=4B478099-3F5E-044F-9D75-EC8803FD9C90"
        )
        //https://pratikhasar.com/netting/e_gelen_pdf.php?firma_id=1&InvoiceUUID=ecb17311-6757-4e8e-b747-89c608554554&id=16270048154
        var pdfUrl = intent.getStringExtra("path").toString()
       // var pdfUrl=    "https://pratikhasar.com/netting/e_gelen_pdf.php?firma_id=1&InvoiceUUID=ecb17311-6757-4e8e-b747-89c608554554&id=AEE2023000013280"
        RetrievePDFFromURL(pdfViewer).execute(pdfUrl)
        imageyazdir.setOnClickListener {
            imageyazdir.isEnabled=false
            val alert= Alert()
            alert.showAlert(this,getString(R.string.uyari),getString(R.string.faturaindiriliyor))

            /* val printmanager: PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

            val printDocumentAdapter= PdfDocumentAdapter()
            printmanager.print(pdfViewer.toString(),printDocumentAdapter, PrintAttributes.Builder().build())*/
            val url = intent.getStringExtra("path").toString()
            //printUrl(this, url)
            Log.d("URL", url)
            main(url)

        }

    }
    private fun startLoadingAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        progressBar.setVisibility(View.VISIBLE)
        progressBar.startAnimation(animation)

        // Simulate loading delay
        // Remove this code in your actual implementation
        Handler().postDelayed(Runnable {
            progressBar.clearAnimation()
            progressBar.setVisibility(View.GONE)
        }, 5000) // Replace 3000 with your desired loading time
    }
    fun main(path: String) {
        val url = path // Replace with the URL of your PDF file
        val random =
            Random(System.currentTimeMillis()) // Rastgelelik için bir tohum (seed) belirtiyoruz

        val randomNumber = random.nextInt()
        val fileName = "e_fatura_" + randomNumber // Replace with the desired file name

        val context = /* Provide your Android Context object here */
            downloadPdf(this, url, fileName)
    }

    fun downloadPdf(context: Context, url: String, fileName: String) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "$fileName.pdf"
            )

        downloadManager.enqueue(request)
    }


    private fun onBaslat() {
        ivBack = findViewById(R.id.ivBack)
        pdfViewer = findViewById(R.id.pdf_web_fatura)
        imageyazdir = findViewById(R.id.imageyazdir)
        firma_id = intent.getStringExtra("firma_id").toString()
    }
}

class RetrievePDFFromURL(pdfView: PDFView) :
    AsyncTask<String, Void, InputStream>() {
    // on below line we are creating a variable for our pdf view.
    val mypdfView: PDFView = pdfView
    // on below line we are calling our do in background method.
    override fun doInBackground(vararg params: String?): InputStream? {
        // on below line we are creating a variable for our input stream.
        var inputStream: InputStream? = null
        try {
            // on below line we are creating an url
            // for our url which we are passing as a string.
            val url = URL(params.get(0))

            // on below line we are creating our http url connection.
            val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection

            // on below line we are checking if the response
            // is successful with the help of response code
            // 200 response code means response is successful
            if (urlConnection.responseCode == 200) {
                // on below line we are initializing our input stream
                // if the response is successful.
                inputStream = BufferedInputStream(urlConnection.inputStream)
            }
        }
        // on below line we are adding catch block to handle exception
        catch (e: Exception) {
            // on below line we are simply printing
            // our exception and returning null
            e.printStackTrace()
            return null;
        }
        // on below line we are returning input stream.
        return inputStream;
    }

    // on below line we are calling on post execute
    // method to load the url in our pdf view.
    override fun onPostExecute(result: InputStream?) {
        // on below line we are loading url within our
        // pdf view on below line using input stream.
        mypdfView.fromStream(result).load()

    }
}