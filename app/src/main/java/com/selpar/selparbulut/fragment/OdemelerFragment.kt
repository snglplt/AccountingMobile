package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.OdemeGetirAdapter
import com.selpar.selparbulut.adapter.tahsilatOdemeGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.SharedViewModel
import com.selpar.selparbulut.model.TahsilatOdemeModel
import com.selpar.selparbulut.ui.TumAlacakOdemeActivity
import org.json.JSONArray
import java.time.LocalDate
import java.util.ArrayList


class OdemelerFragment : Fragment() {
    lateinit var username:String
    lateinit var uid:String
    lateinit var recyclerView_odeme: RecyclerView
    lateinit var ivBack: ImageView
    var ID= ArrayList<String>()
    var EVRAK_NO= ArrayList<String>()
    var TIPI= ArrayList<String>()
    var CARI_ID= ArrayList<String>()
    var CARI_UNVAN= ArrayList<String>()
    var CARI_VNO= ArrayList<String>()
    var BORC= ArrayList<String>()
    var PARA_BIRIMI= ArrayList<String>()
    var ALACAK= ArrayList<String>()
    var ISLEM_TIPI= ArrayList<String>()
    var ACIKLAMA= ArrayList<String>()
    var EVRAK_TARIHI= ArrayList<String>()
    var TUTAR= ArrayList<String>()
    private lateinit var newArrayList: ArrayList<TahsilatOdemeModel>
    lateinit var progressBar: ProgressBar
    lateinit var sharedViewModel: SharedViewModel
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_odemeler, container, false)
        sharedViewModel = (activity as TumAlacakOdemeActivity).getSharedViewModel()

        username=sharedViewModel.username
        uid=sharedViewModel.uid
        recyclerView_odeme=view.findViewById(R.id.recyclerView_odeme)

        progressBar = view.findViewById(R.id.progressBar)
        Toast.makeText(requireContext(),getString(R.string.yapilanodemelerlisteleniyor), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        onTahsilatGetir()

        return view
    }
    private fun startLoadingAnimation() {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        progressBar.setVisibility(View.VISIBLE)
        progressBar.startAnimation(animation)

        // Simulate loading delay
        // Remove this code in your actual implementation
        Handler().postDelayed(Runnable {
            progressBar.clearAnimation()
            progressBar.setVisibility(View.GONE)
        }, 5000) // Replace 3000 with your desired loading time
    }/*
    private fun odemeTarihGetir() {
        Toast.makeText(requireContext(),getString(R.string.secilentarihegoreodemelisteleme), Toast.LENGTH_LONG).show()
        startLoadingAnimation()
        ID.clear()
        EVRAK_NO.clear()
        TIPI.clear()
        CARI_ID.clear()
        CARI_UNVAN.clear()
        CARI_VNO.clear()
        BORC.clear()
        PARA_BIRIMI.clear()
        ALACAK.clear()
        ISLEM_TIPI.clear()
        ACIKLAMA.clear()
        EVRAK_TARIHI.clear()
        val urlek ="&username="+username+"&uid="+uid+"&baslangic_Tarihi="+
                edTarih1_dat+"&bitis_Tarihi="+edTarih2_dat
        //"&CariID="+MusteriId

        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.OdemeListesi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        Log.d("odeme_listesi_tarih", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        ID.add(item.getString("ID"))
                        EVRAK_NO.add(item.getString("EVRAK_NO"))
                        TIPI.add(item.getString("TIPI"))
                        CARI_ID.add(item.getString("CARI_ID"))
                        CARI_UNVAN.add(item.getString("CARI_UNVAN"))
                        CARI_VNO.add(item.getString("CARI_VNO"))
                        BORC.add("0")
                        PARA_BIRIMI.add(item.getString("PARA_BIRIMI"))
                        ALACAK.add(item.getString("ALACAK"))
                        ISLEM_TIPI.add(item.getString("ISLEM_TIPI"))
                        ACIKLAMA.add(item.getString("ACIKLAMA"))
                        EVRAK_TARIHI.add(item.getString("EVRAK_TARIHI"))


                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(requireContext(),getString(R.string.odemelistesibulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.odemelistesibulunmamakta))


                }
                recyclerView_odeme.layoutManager = LinearLayoutManager(requireContext())
                recyclerView_odeme.setHasFixedSize(false)
                newArrayList = arrayListOf<TahsilatOdemeModel>()
                getUserData(requireContext())

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(),getString(R.string.odemelistesibulunmamakta), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.odemelistesibulunmamakta))

            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }

*/

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onTahsilatGetir() {
        val today = LocalDate.now()
        val fiveDaysAgo = today.minusDays(1)
        val urlek ="&username="+username+"&uid="+uid+
                "&baslangic_Tarihi="+sharedViewModel.tarih1+"&bitis_Tarihi="+sharedViewModel.tarih2
        var url = Consts.OdemeListesi + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        Log.d("odeme_listesi", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        ID.add(item.getString("ID"))
                        EVRAK_NO.add(item.getString("EVRAK_NO"))
                        TIPI.add(item.getString("TIPI"))
                        CARI_ID.add(item.getString("CARI_ID"))
                        CARI_UNVAN.add(item.getString("CARI_UNVAN"))
                        CARI_VNO.add(item.getString("CARI_VNO"))
                        BORC.add(item.getString("BORC"))
                        PARA_BIRIMI.add(item.getString("PARA_BIRIMI"))
                        ALACAK.add("0")
                        ISLEM_TIPI.add(item.getString("ISLEM_TIPI"))
                        TUTAR.add(item.getString("TUTAR"))
                        ACIKLAMA.add(item.getString("ACIKLAMA"))
                        EVRAK_TARIHI.add(item.getString("EVRAK_TARIHI"))


                    }
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    Toast.makeText(requireContext(),getString(R.string.odemelistesibulunmamakta), Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.odemelistesibulunmamakta))

                }
                recyclerView_odeme.layoutManager = LinearLayoutManager(requireContext())
                recyclerView_odeme.setHasFixedSize(false)
                newArrayList = arrayListOf<TahsilatOdemeModel>()
                getUserData(requireContext())

            }, { error ->
                // Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(),getString(R.string.odemelistesibulunmamakta), Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.odemelistesibulunmamakta))

            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)

    }
    private fun getUserData(context: Context) {
        for (i in ID.indices) {
            val news = TahsilatOdemeModel(
                username,
                uid,
                ID[i],
                EVRAK_NO[i],
                TIPI[i],
                CARI_ID[i],
                CARI_UNVAN[i],
                CARI_VNO[i],
                BORC[i],
                ALACAK[i],
                PARA_BIRIMI[i],
                ISLEM_TIPI[i],
                ACIKLAMA[i],
                EVRAK_TARIHI[i],
                TUTAR[i],

            )


            newArrayList.add(news)
        }

        recyclerView_odeme.adapter = OdemeGetirAdapter(newArrayList)
    }
    }