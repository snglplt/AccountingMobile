package com.selpar.selparbulut.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.storage.StorageManager
import android.print.PrintAttributes
import android.print.PrintManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.FileProvider
import com.selpar.selparbulut.network.BuildConfig
import com.github.barteksc.pdfviewer.PDFView
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.PdfCariAdapter
import java.io.File
import java.net.URL

class CariPdfEkstreActivity : AppCompatActivity() {
    lateinit var btn_whatsapp:ImageView
    lateinit var imageyazdir:ImageView
    lateinit var btn_mail_gonder:ImageView
    lateinit var ivBack:ImageView
    lateinit var pdfviewer: PDFView
    var pageHeight = 1120
    var pageWidth = 792
    var PERMISSION_CODE = 1
    lateinit var progressBar: ProgressBar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_pdf_ekstre)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)
        ivBack=findViewById(R.id.ivBack)
        progressBar=findViewById(R.id.progressBar)
        ivBack.setOnClickListener(View.OnClickListener { finish() })
        Toast.makeText(this,getString(R.string.caripdflereksterelisteleniyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        onBaslat()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(
                    android.Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission.MANAGE_DOCUMENTS
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission.SEND_SMS
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission_group.STORAGE
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.os.storage.StorageManager.ACTION_MANAGE_STORAGE
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                val permission = arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.MANAGE_DOCUMENTS,
                    android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    android.Manifest.permission.SEND_SMS,
                    android.Manifest.permission.CALL_PHONE,
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    StorageManager.ACTION_MANAGE_STORAGE,
                    android.Manifest.permission_group.STORAGE,
                    Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                )

                requestPermissions(permission, PERMISSION_CODE)
            } else {
                // openCamera()
            }

        } else {
            //api<23
            // openCamera()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
            && !Environment.isExternalStorageManager()
        ) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        generatePDF()
        btn_mail_gonder.setOnClickListener {
            val path= Environment.getExternalStorageDirectory().path+"/Cari.pdf"
            // val path= "backup_rules.xml"
            //  val path= getExternalFilesDir(null)!!.absolutePath.toString()+"/GFG.pdf",
            val file = File(Environment.getExternalStorageDirectory(), "CariHesapDokumu.pdf")
            val dosyaYolu = File("/storage/emulated/0/CariHesapDokumu.pdf")
            val dosyaUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", dosyaYolu)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, dosyaUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // intent.setPackage("com.whatsapp")

            startActivity(intent)
        }
        btn_whatsapp.setOnClickListener {
            val path= Environment.getExternalStorageDirectory().path+"/CariHesapDokumu.pdf"
            // val path= "backup_rules.xml"
            //  val path= getExternalFilesDir(null)!!.absolutePath.toString()+"/GFG.pdf",
            val file = File(Environment.getExternalStorageDirectory(), "Cari.pdf")
            val dosyaYolu = File("/storage/emulated/0/CariHesapDokumu.pdf")
            val dosyaUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", dosyaYolu)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, dosyaUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setPackage("com.whatsapp")

            startActivity(intent)

        }
        imageyazdir.setOnClickListener {
            val printmanager: PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

            val printDocumentAdapter= PdfCariAdapter()
            printmanager.print("cari",printDocumentAdapter, PrintAttributes.Builder().build())


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
        }, 2000) // Replace 3000 with your desired loading time
    }

    fun onBaslat(){
        btn_whatsapp=findViewById(R.id.btn_whatsapp)
        imageyazdir=findViewById(R.id.imageyazdir)
        btn_mail_gonder=findViewById(R.id.btn_mail_gonder)
        pdfviewer=findViewById(R.id.pdfviewer)
    }
    fun generatePDF() {
        var pdfDocument: PdfDocument = PdfDocument()

        var paint: Paint = Paint()
        var title: Paint = Paint()
        var plaka: Paint = Paint()
        var arac_bilgi: Paint = Paint()
        var arac_bilgileri: Paint = Paint()
        var arac_sahibi: Paint = Paint()
        var pdf_baslik: Paint = Paint()
        var arac_bilgileri_renk: Paint = Paint()
        var toplubilgi: Paint = Paint()
        var arac_bilgileri2: Paint = Paint()
        var toplu: Paint = Paint()
        var toplu2: Paint = Paint()
        var km: Paint = Paint()
        var textblue: Paint = Paint()
        var usta_bilgi: Paint = Paint()
        var paint_h: Paint = Paint()
        paint_h.textSize=12F
        paint_h.textAlign = Paint.Align.RIGHT




        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        var myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        // below line is used for setting
        // start page for our PDF file.
        var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

        // creating a variable for canvas
        // from our page of PDF.
        var canvas: Canvas = myPage.canvas

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        plaka.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        arac_bilgi.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        arac_bilgileri.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        toplubilgi.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        arac_bilgileri2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        toplu.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        km.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        usta_bilgi.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        textblue.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))




        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.textSize = 15F
        plaka.textSize=36F
        arac_bilgi.textSize=12F
        km.textSize=12F
        arac_bilgi.color= Color.parseColor("#FF6E40")
        arac_bilgileri.textSize=12F
        arac_bilgileri_renk.textSize=12F
        arac_bilgileri_renk.color= Color.GRAY
        arac_sahibi.textSize=15F
        pdf_baslik.textSize=20f
        toplubilgi.textSize=12F
        arac_bilgileri2.textSize=12F
        toplu.textSize=12F
        toplu2.textSize=12F
        textblue.textSize=12f
        usta_bilgi.textSize=12f

        canvas.drawText(getString(R.string.carihesapdokumupdf),250F,30F,pdf_baslik)
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.textSize = 15F
        canvas.drawText(getString(R.string.caribilgileri),100F,100f,arac_bilgi)
        canvas.drawLine(20f,120f,800f,120f,paint)
        canvas.drawText(getString(R.string.cariunvan),140f,140f,arac_bilgileri_renk)
        canvas.drawText(intent.getStringExtra("CariUnvan").toString(),275f,140f,arac_bilgileri2)

        canvas.drawLine(20f,150f,800f,150f,paint)
        canvas.drawText(getString(R.string.tarih),20f,180f,arac_bilgileri_renk)
        canvas.drawText(getString(R.string.turu),120f,180f,arac_bilgileri_renk)
        canvas.drawText(getString(R.string.aciklama),170f,180f,arac_bilgileri_renk)

        canvas.drawText(getString(R.string.borc),300f,180f,arac_bilgileri_renk)


        canvas.drawLine(20f,190F,800f,190F,paint)

        canvas.drawText(getString(R.string.alacak),410F,180F,arac_bilgileri_renk)
        canvas.drawText(getString(R.string.bakiye),470F,180F,arac_bilgileri_renk)
        canvas.drawText(getString(R.string.virman),570F,180F,arac_bilgileri_renk)
        canvas.drawText(getString(R.string.not),670F,180F,arac_bilgileri_renk)
        var x_tahsilat=320f
        var x_tarih=20f
        var y_tahsilat=210f
        var y_tarih=210f
        val x_odeme_turu=120f
        var y_odeme_turu=210f
        val x_aciklama=170f
        var y_aciklama=210f
        val x_bakiye=470f
        var y_bakiye=210f
        var x_not=670f
        var y_not=210f

        var x_odeme=430f
        var y_odeme=210f
        var x_virman=570f
        var y_virman=210f
        var binler = 1000
        var yuzler = 1000
        var ondalik = 18 % 100
        var bakiye_anlik:Int=0
        if(intent.getStringExtra("VirmanCari").toString()=="null" || intent.getStringExtra("VirmanCari").toString()==""){
            canvas.drawText("", x_virman, y_virman, arac_bilgileri2)

        }else{
            if(intent.getStringExtra("VirmanCari").toString().length>20){
                canvas.drawText(getString(R.string.ektedir), x_virman, y_virman, arac_bilgileri)

                canvas.drawText(getString(R.string.ek3)+" ", x_tarih, y_virman+100, arac_bilgi)
                canvas.drawText(getString(R.string.virman)+": "+intent.getStringExtra("VirmanCari").toString(), x_tarih, y_virman+130, arac_bilgileri)

            }else {
                canvas.drawText(
                    intent.getStringExtra("VirmanCari").toString(),
                    x_virman,
                    y_virman,
                    arac_bilgileri
                )
            }
            //canvas.drawText(intent.getStringExtra("VirmanCari").toString(), x_virman, y_virman, arac_bilgileri2)

        }
        val borc=intent.getStringExtra("Borc").toString().split(".")
        binler = borc[0].toInt() / 1000
            yuzler = borc[0].toInt() % 1000
            ondalik = (borc[0].toInt() * 100).toInt() % 100
            if(binler==0){
                canvas.drawText(String.format("%.2f",borc[0].toFloat()).replace(".",","),x_tahsilat+57,y_tahsilat,paint_h)

            }else
                canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_tahsilat+57,y_tahsilat,paint_h)

            //   canvas.drawText(odeme[i].toString(), x_tahsilat+60, y_tahsilat, paint_h)
        val alacak=intent.getStringExtra("Alacak").toString().split(".")
            binler = alacak[0].toInt()/ 1000
            yuzler = alacak[0].toInt() % 1000
            ondalik = (alacak[0].toInt() * 100).toInt() % 100
            if(binler==0){
                canvas.drawText(String.format("%.2f",alacak[0].toFloat()).replace(".",","),x_odeme+17,y_tahsilat,paint_h)

            }else
                canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_odeme+17,y_tahsilat,paint_h)

            //    canvas.drawText(tahsilat[i], x_odeme+50, y_odeme, paint_h)
            var tah=0
            var ode=0

                tah+=alacak[0].toInt()
                ode+=borc[0].toInt()

            bakiye_anlik=tah-ode

            if(bakiye_anlik.toFloat()>0) {
                var binler = bakiye_anlik.toFloat().toInt() / 1000
                var yuzler = bakiye_anlik.toFloat().toInt() % 1000
                var ondalik = (bakiye_anlik.toFloat().toInt() * 100).toInt() % 100
                if (binler == 0) {
                    canvas.drawText(
                        String.format("%.2f", bakiye_anlik.toFloat()).replace(".", ","),x_bakiye+50, y_bakiye, paint_h
                    )

                } else
                    canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_bakiye+50, y_bakiye, paint_h)
            }else{
                var binler = bakiye_anlik.toFloat().toInt() / 1000
                var yuzler = bakiye_anlik.toFloat().toInt() % 1000
                var ondalik = (bakiye_anlik.toFloat().toInt() * 100).toInt() % 100
                if (binler == 0) {
                    canvas.drawText(
                        String.format("%.2f",bakiye_anlik.toFloat()).replace(".", ","),x_bakiye+50, y_bakiye, paint_h
                    )

                } else
                    canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler*-1, ondalik),x_bakiye+50, y_bakiye, paint_h)

            }


            // canvas.drawText((bakiye_anlik).toString(), x_bakiye+50, y_bakiye, paint_h)
        if(intent.getStringExtra("Aciklama").toString().length>10){
            canvas.drawText(getString(R.string.ektedir), x_aciklama-5, y_not, arac_bilgileri)

            canvas.drawText(getString(R.string.ek2)+" ", x_tarih, y_virman+75, arac_bilgi)
            canvas.drawText(getString(R.string.aciklama)+": "+intent.getStringExtra("Aciklama").toString(), x_tarih, y_virman+95, arac_bilgileri)

        }else {
            canvas.drawText(
                intent.getStringExtra("Aciklama").toString(),
                x_aciklama - 5,
                y_not,
                arac_bilgileri
            )
        }
            canvas.drawLine(20f,y_virman+5,800f,y_virman+5,paint)




        canvas.drawLine(x_tarih+70,150f,x_tarih+70,y_virman+5,paint)

        canvas.drawLine(x_odeme_turu+35,150f,x_odeme_turu+35,y_virman+5,paint_h)
        canvas.drawLine(x_aciklama+70,150f,x_aciklama+70,y_virman+5,paint)
        canvas.drawLine(x_tahsilat+60,150f,x_tahsilat+60,y_virman+5,paint_h)
        canvas.drawLine(x_odeme+20,150f,x_odeme+20,y_virman+5,paint_h)
        canvas.drawLine(x_bakiye+50,150f,x_bakiye+50,y_virman+5,paint_h)
        canvas.drawLine(x_not-10,150f,x_not-10,y_virman+5,paint)

        canvas.drawText(intent.getStringExtra("EvrakTarihi").toString(), x_tarih, y_tarih, arac_bilgileri2)
        //canvas.drawText(intent.getStringExtra("IslemTuru").toString(), x_odeme_turu-10, y_odeme_turu, arac_bilgileri2)
        if(intent.getStringExtra("IslemTuru").toString().length>10){
            canvas.drawText(getString(R.string.ektedir), x_odeme_turu-10, y_odeme_turu, arac_bilgileri)

            canvas.drawText(getString(R.string.ek1)+" ", x_tarih, y_virman+35, arac_bilgi)
            canvas.drawText(getString(R.string.islemturu)+": "+intent.getStringExtra("IslemTuru").toString(), x_tarih, y_virman+50, arac_bilgileri)

        }else {
            canvas.drawText(
                intent.getStringExtra("IslemTuru").toString(),
                x_odeme_turu-10,
                y_odeme_turu,
                arac_bilgileri
            )
        }
        canvas.drawText(getString(R.string.bakiye),x_not,y_odeme,arac_bilgi)

        canvas.drawText(bakiye_anlik.toString()+",00",x_not+50,y_odeme,arac_bilgi)

        canvas.drawText(getString(R.string.isburapor)+" ",70f,800F,arac_bilgileri)
        textblue.color= Color.BLUE
        canvas.drawText("www.selparbulut.com ",140f,800f,textblue)
        canvas.drawText(getString(R.string.yazilimbilgi),280f,800F,arac_bilgileri)


        pdfDocument.finishPage(myPage)
        val file: File = File(Environment.getExternalStorageDirectory(), "CariHesapDokumu.pdf")
        try {
            pdfDocument.writeTo(file.outputStream())
            pdfviewer.fromFile(file).enableSwipe(true).swipeHorizontal(false).load()
            Toast.makeText(applicationContext, getString(R.string.pdfolusturuldu), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
