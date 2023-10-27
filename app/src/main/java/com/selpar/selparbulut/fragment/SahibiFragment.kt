package com.selpar.selparbulut.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.selpar.selparbulut.R


class SahibiFragment : Fragment() {

    lateinit var txtVergiNo:TextView
    lateinit var txtVergiDairesi:TextView
    lateinit var txtAdresi:TextView
    lateinit var txtIlIlce:TextView
    lateinit var txtCariGSM:TextView
    lateinit var Adresi:String
    lateinit var VergiNo:String
    lateinit var VergiDairesi:String
    lateinit var Il:String
    lateinit var Ilce:String
    lateinit var CariGSM:String
    lateinit var tel_arama_layout:LinearLayout
    lateinit var ImageViewPhone:ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_sahibi, container, false)
        val args=this.arguments
        VergiNo=args?.getString("VergiNo").toString()
        VergiDairesi=args?.getString("VergiDairesi").toString()
        Adresi=args?.getString("Adresi").toString()
        Il=args?.getString("Il").toString()
        Ilce=args?.getString("Ilce").toString()
        CariGSM=args?.getString("CariGSM").toString()
        Toast.makeText(requireContext(),"CariGSM: "+CariGSM,Toast.LENGTH_LONG).show()
        txtVergiNo=view.findViewById(R.id.txtVergiNo)
        txtVergiDairesi=view.findViewById(R.id.txtVergiDairesi)
        txtAdresi=view.findViewById(R.id.txtAdresi)
        txtIlIlce=view.findViewById(R.id.txtIlIlce)
        txtCariGSM=view.findViewById(R.id.txtCariGSM)
        tel_arama_layout=view.findViewById(R.id.tel_arama_layout)
        ImageViewPhone=view.findViewById(R.id.ImageViewPhone)
        txtVergiNo.setText(getString(R.string.vergino)+": "+VergiNo)
        txtVergiDairesi.setText(getString(R.string.vergidairesi)+": "+VergiDairesi)
        txtAdresi.setText(getString(R.string.adresi)+": "+Adresi)
        txtIlIlce.setText(Il+"/"+Ilce)
        txtCariGSM.setText("GSM: "+CariGSM)
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_button)
/*
        txtVergiNo.animation=animation
        txtVergiDairesi.animation=animation
        txtAdresi.animation=animation
        txtIlIlce.animation=animation
        tel_arama_layout.animation=animation*/
        ImageViewPhone.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + CariGSM)
            startActivity(dialIntent)
        }
        return view
    }

}