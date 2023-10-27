package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.selparbulut.MainActivity
import com.selpar.selparbulut.R
import com.selpar.selparbulut.data.Alert
import com.selpar.selparbulut.interfacess.Consts
import com.selpar.selparbulut.model.KartDetayModel
import org.json.JSONArray
import org.w3c.dom.Text


class SigortaFragment : Fragment() {
    lateinit var txtSigortaSirketi: TextView
    lateinit var txtPoliceTuru: TextView
    lateinit var txtDosyaNo: TextView
    lateinit var txtEksperAdi: TextView
    lateinit var txtEksperUnvani: TextView
    lateinit var txtEksperTelefon: TextView
    lateinit var txtEksperMail: TextView
    lateinit var txtEksperGsm: TextView
    lateinit var txtEksperAdresi: TextView
    lateinit var txtEksperIli: TextView
    lateinit var txtSigertaAcentesi: TextView
    lateinit var txtSigortaAcenteTelefon: TextView
    lateinit var txtSigortaAcenteAdresi: TextView
    lateinit var txtSigortaAcenteIli: TextView
    lateinit var linearlayout_sigorta:LinearLayout
    lateinit var username:String
    lateinit var uid:String
    lateinit var id:String
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sigorta, container, false)
        val args=this.arguments
        username=args?.getString("username").toString()
        uid=args?.getString("uid").toString()
        id=args?.getString("id").toString()
        linearlayout_sigorta=view.findViewById(R.id.linearlayout_sigorta)
        txtSigortaSirketi=view.findViewById(R.id.txtSigortaSirketi)
        txtPoliceTuru=view.findViewById(R.id.txtPoliceTuru)
        txtDosyaNo=view.findViewById(R.id.txtDosyaNo)
        txtEksperAdi=view.findViewById(R.id.txtEksperAdi)
        txtEksperUnvani=view.findViewById(R.id.txtEksperUnvani)
        txtEksperTelefon=view.findViewById(R.id.txtEksperTelefon)
        txtEksperMail=view.findViewById(R.id.txtEksperMail)
        txtEksperGsm=view.findViewById(R.id.txtEksperGsm)
        txtEksperAdresi=view.findViewById(R.id.txtEksperAdresi)
        txtEksperIli=view.findViewById(R.id.txtEksperIli)
        txtSigertaAcentesi=view.findViewById(R.id.txtSigertaAcentesi)
        txtSigortaAcenteTelefon=view.findViewById(R.id.txtSigortaAcenteTelefon)
        txtSigortaAcenteAdresi=view.findViewById(R.id.txtSigortaAcenteAdresi)
        txtSigortaAcenteIli=view.findViewById(R.id.txtSigortaAcenteIli)
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_button)

        txtSigortaSirketi.animation=animation
        txtPoliceTuru.animation=animation
        txtDosyaNo.animation=animation
        txtEksperAdi.animation=animation
        txtEksperUnvani.animation=animation
        txtEksperTelefon.animation=animation
        txtEksperMail.animation=animation
        txtEksperGsm.animation=animation
        txtEksperAdresi.animation=animation
        txtEksperIli.animation=animation
        txtSigertaAcentesi.animation=animation
        txtSigortaAcenteTelefon.animation=animation
        txtSigortaAcenteAdresi.animation=animation
        txtSigortaAcenteIli.animation=animation
        onSigortaGetir()
        return view
    }
    private fun onSigortaGetir(){
        val urlek ="&username="+username+"&uid="+uid+"&KabulId="+id
        //"&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        var url = Consts.KabulKartiSigorta + urlek
        //"https://api.selparbulut.com/?kaynak=KabulKartlari&username=antalyaav&uid=996EF642-212F-77E8-261D-0AE63A082CC5&" +
        // "&baslangic_Tarihi=2023-06-01&bitis_Tarihi=2023-06-30"
        //Consts.BASE_URL + urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        Log.d("kart_detay", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["data"] as JSONArray
                    //val json2= response["message"] as JsonObjectRequest
                    for (i in 0 until json.length()) {
                        //   Toast.makeText(requireContext(), response["message"].toString(),Toast.LENGTH_LONG).show()
                        val item = json.getJSONObject(i)
                        if(item.getString("SigortaSirketi")=="null" &&
                            item.getString("DosyaNo")=="null"&&
                            item.getString("EksperAdi")=="null"){
                            txtSigortaSirketi.visibility=GONE
                            txtPoliceTuru.visibility=GONE
                            txtDosyaNo.visibility=GONE
                            txtEksperAdi.visibility=GONE
                            txtEksperUnvani.visibility=GONE
                            txtEksperTelefon.visibility=GONE
                            txtEksperMail.visibility=GONE
                            txtEksperGsm.visibility=GONE
                            txtEksperAdresi.visibility=GONE
                            txtEksperIli.visibility=GONE
                            txtSigertaAcentesi.visibility=GONE
                            txtSigortaAcenteIli.visibility=GONE
                            txtSigortaAcenteTelefon.visibility=GONE
                            txtSigortaAcenteAdresi.visibility=GONE
                        }
                            else {
                            txtSigortaSirketi.setText(getString(R.string.sigortasirketi)+": " + item.getString("SigortaSirketi"))
                            txtPoliceTuru.setText(getString(R.string.policeturu)+": " + item.getString("PoliceTuru"))
                            txtDosyaNo.setText(getString(R.string.dosyanoo)+": " + item.getString("DosyaNo"))
                            txtEksperAdi.setText(getString(R.string.eksperadi)+": " + item.getString("EksperAdi"))
                            txtEksperUnvani.setText(getString(R.string.eksperunvani)+": " + item.getString("EksperUnvani"))
                            txtEksperTelefon.setText(getString(R.string.ekspertelefon)+": " + item.getString("EksperTelefon"))
                            txtEksperMail.setText(getString(R.string.ekspermail)+": " + item.getString("EksperMail"))
                            txtEksperGsm.setText(getString(R.string.ekspergsm)+": " + item.getString("EksperGsm"))
                            txtEksperAdresi.setText(getString(R.string.eksperadresi)+": " + item.getString("EksperAdresi"))
                            txtEksperIli.setText(getString(R.string.eksperil)+": " + item.getString("EksperIli"))
                            txtSigertaAcentesi.setText(getString(R.string.sigortaacentesi)+": " + item.getString("SigertaAcentesi"))
                            txtSigortaAcenteTelefon.setText(
                                getString(R.string.sigortaacentesitelefon)+": " + item.getString(
                                    "SigortaAcenteTelefon"
                                )
                            )
                            txtSigortaAcenteAdresi.setText(
                                getString(R.string.sigortaacentesiadres)+": " + item.getString(
                                    "SigortaAcenteAdresi"
                                )
                            )
                            txtSigortaAcenteIli.setText(getString(R.string.sigortaacentesiil)+": " + item.getString("SigortaAcenteIli"))

                        }




                    }
                } catch (e: Exception) {
                    linearlayout_sigorta.visibility=GONE
                    Toast.makeText(requireContext(),e.message, Toast.LENGTH_LONG).show()
                    val alert= Alert()
                    alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitsigortabilgisibulunmamakta))

                }


            }, { error ->
                linearlayout_sigorta.visibility=GONE
                Toast.makeText(requireContext(),error.message, Toast.LENGTH_LONG).show()
                Log.e("TAG", "RESPONSE IS $error")
                val alert= Alert()
                alert.showAlert(requireContext(),getString(R.string.uyari),getString(R.string.aracaaitsigortabilgisibulunmamakta))


            }
        )
        queue.add(request)
    }

}