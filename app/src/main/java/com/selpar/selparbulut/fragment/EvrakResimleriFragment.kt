package com.selpar.selparbulut.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.R
import com.selpar.selparbulut.adapter.EvrakGVAdapter
import com.selpar.selparbulut.adapter.acikKartGetirAdapter
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.AcikKartlarModel
import com.selpar.selparbulut.model.EvrakResimModel
import org.json.JSONArray
import java.time.LocalDate
import java.util.ArrayList


class EvrakResimleriFragment : Fragment() {

    var bundlem = Bundle()
    lateinit var username: String
    lateinit var uid: String
    lateinit var id: String
    lateinit var coursesGV: GridView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_evrak_resimleri, container, false)
        coursesGV = view.findViewById(com.selpar.selparbulut.R.id.idGVcourses)

        val args = this.arguments
        username = args?.getString("username").toString()
        uid = args?.getString("uid").toString()
        id = args?.getString("id").toString()
        evrakResimGetir()
        return view
    }


    private fun evrakResimGetir() {
        val urlek = "&username=" + username + "&uid=" + uid +"&KabulId="+id
        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.KabulKartiResimler + urlek
        val deneme_url="https://api.selparbulut.com/?kaynak=KabulKartiResimler&ResimTuru=Evrak&username=karparsrv&uid=5B3B491E-B0CB-ECB7-D90B-A5AD5E0A5F22&KabulId=746289"
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        Log.d("evrak_resim", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    val evrakModelArrayList: ArrayList<EvrakResimModel> = ArrayList<EvrakResimModel>()

                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)

                        val EvrakAdi=item.getString("EvrakAdi")
                        val ResimYolu=item.getString("ResimYolu")
                        evrakModelArrayList.add(EvrakResimModel(username,uid,EvrakAdi,ResimYolu))



                    }
                    val adapter2 = EvrakGVAdapter(requireContext(), evrakModelArrayList)
                    coursesGV.adapter = adapter2
                } catch (e: Exception) {
                    //Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                    Toast.makeText(requireContext(),getString(R.string.aracaaitevrakresmibulunmamakta),Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitevrakresmibulunmamakta))

                }


            }, { error ->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitevrakresmibulunmamakta))


            }
        )
        queue.add(request)


    }



}