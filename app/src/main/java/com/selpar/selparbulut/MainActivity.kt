package com.selpar.selparbulut

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.storage.StorageManager
import android.provider.Settings
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.selpar.navigotionview.ExpandableListAdapter
import com.selpar.navigotionview.ExpandedMenuModel
import com.selpar.selparbulut.fragment.Home
import com.selpar.selparbulut.fragment.MyBooking
import com.selpar.selparbulut.fragment.ProfileSettingActivity
import com.selpar.selparbulut.interfacess.Communicator
import com.selpar.selparbulut.ui.AcikKartlarActivity
import com.selpar.selparbulut.ui.AlisEvrakActivity
import com.selpar.selparbulut.ui.BugunYapilacakOdemelerActivity
import com.selpar.selparbulut.ui.BugunYapilacakTahsilatlarActivity
import com.selpar.selparbulut.ui.CariListesiActivity
import com.selpar.selparbulut.ui.EvrakSatisActivity
import com.selpar.selparbulut.ui.GelenEFaturaActivity
import com.selpar.selparbulut.ui.GidenFaturaActivity
import com.selpar.selparbulut.ui.KapaliKartlarActivity
import com.selpar.selparbulut.ui.OdemeEkleActivity
import com.selpar.selparbulut.ui.OdemeListesiActivity
import com.selpar.selparbulut.ui.OncekiOnarimlarActivity
import com.selpar.selparbulut.ui.OrijinalStokListesiActivity
import com.selpar.selparbulut.ui.SilinenKabulListesiActivity
import com.selpar.selparbulut.ui.SingInActivity
import com.selpar.selparbulut.ui.StokListesiActivity
import com.selpar.selparbulut.ui.TahsilatEkleActivity
import com.selpar.selparbulut.ui.TahsilatListesiActivity
import com.selpar.selparbulut.ui.TahsilatiYapilmayanActivity
import com.selpar.selparbulut.ui.TumAlacakOdemeActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList
import java.util.HashMap

class MainActivity : AppCompatActivity(), Communicator {
    var navigationView: NavigationView? = null
    var navHeader: View? = null
    var imgprofile: CircleImageView? = null
    var txtad_soyad: TextView? = null
    var tvEmail: TextView? = null
    var tvOther: TextView? = null
    var tvEnglish: TextView? = null
    var llProfileClick: LinearLayout? = null
    var ivFilter: ImageView? = null
    var navItemIndex = 0
    var tAGMAIN = "main"
    var tAGPROFILESETINGS = "profile_settings"
    var menuLeftIV: ImageView? = null
    var PERMISSION_CODE = 1

    var cURRENTTAG: String = tAGMAIN
    var drawer: DrawerLayout? = null
    private val mHandler: Handler? = null
    var inputManager: InputMethodManager? = null

    private val shouldLoadHomeFragOnBackPress = true
    var listDataHeader: List<ExpandedMenuModel>? = null
    var listDataChild: HashMap<ExpandedMenuModel, List<String>>? = null

    var expandableList: ExpandableListView? = null
    var mMenuAdapter: ExpandableListAdapter? = null
    var bundlem = Bundle()
    lateinit var calismasekli: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_in_left)

        navigationView = findViewById<View>(R.id.navigationview) as NavigationView
        drawer = findViewById<View>(R.id.drawelayout) as DrawerLayout
        menuLeftIV = findViewById<View>(R.id.menuLeftIV) as ImageView
        txtad_soyad = findViewById(R.id.txtad_soyad)
        txtad_soyad!!.setText(intent.getStringExtra("adi") + " " + intent.getStringExtra("soyadi"))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED ||
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
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.MANAGE_DOCUMENTS,
                    android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
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
        /*
                navHeader = navigationView!!.getHeaderView(0)
                imgprofile = navHeader!!.findViewById<CircleImageView>(R.id.img_profile)
                tvName = navHeader!!.findViewById(R.id.tvName)
                txtad_soyad!!.setText(intent.getStringExtra("adi")+" "+intent.getStringExtra("soyadi"))
                tvEmail = navHeader!!.findViewById(R.id.tvEmail)
                tvEmail!!.setText(intent.getStringExtra("username"))
        */
        calismasekli = intent.getStringExtra("calismasekli").toString()
        ivFilter = findViewById<View>(R.id.ivFilter) as ImageView
        val fragobj = Home()
        bundlem.putString("username", intent.getStringExtra("username"))
        bundlem.putString("uid", intent.getStringExtra("uid"))
        bundlem.putString("FirmaLogo", intent.getStringExtra("FirmaLogo"))
        bundlem.putString("KullaniciId", intent.getStringExtra("KullaniciId"))
        bundlem.putString("PlasiyerId", intent.getStringExtra("PlasiyerId"))
        bundlem.putString("PlasiyerAdi", intent.getStringExtra("PlasiyerAdi"))
        bundlem.putString("calismasekli", calismasekli)
        bundlem.putString("kapalionarimlar", "")
        bundlem.putString("acikonarimlar", "")
        bundlem.putString("oncekionarimlar", "")
        fragobj.arguments = bundlem
        var fragmentmaneger = this.supportFragmentManager.beginTransaction()
            .replace(R.id.frame, fragobj)
            .commit()
        if (calismasekli == "hom" || calismasekli == "ms") {


//        tvEnglish = navHeader!!.findViewById(R.id.tvEnglish)
            //      tvOther = navHeader!!.findViewById(R.id.tvOther)
            ///   tvOther = navHeader!!.findViewById(R.id.tvOther)
            //    llProfileClick = navHeader!!.findViewById<LinearLayout>(R.id.llProfileClick)
            inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            /*
        llProfileClick!!.setOnClickListener(View.OnClickListener {
            ivFilter!!.setVisibility(View.GONE)
            navItemIndex = 9
            cURRENTTAG = tAGPROFILESETINGS
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            fragmentTransaction.replace(R.id.frame, ProfileSettingActivity())
            fragmentTransaction.commitAllowingStateLoss()
            drawer!!.closeDrawers()
        })*/
            menuLeftIV!!.setOnClickListener(View.OnClickListener { drawerOpen() })
            setUpNavigationView()

            val menu = navigationView!!.menu
            expandableList =
                findViewById<View>(R.id.navigationmenu) as ExpandableListView
            val navigationView =
                findViewById<View>(R.id.navigationview) as NavigationView

            navigationView?.let { setupDrawerContent(it) }
            if (navigationView != null) {
                setupDrawerContent(navigationView);
            }
            //  changeColorItem(menu, R.id.nav_home_features)
            //  changeColorItem(menu, R.id.nav_bookings_and_job)
            //  changeColorItem(menu, R.id.nav_personal)
            //  changeColorItem(menu, R.id.other)

            for (i in 0 until menu.size()) {
                val mi = menu.getItem(i)
                val subMenu = mi.subMenu
                if (subMenu != null && subMenu.size() > 0) {
                    for (j in 0 until subMenu.size()) {
                        val subMenuItem = subMenu.getItem(j)
                        // applyCustomFont(subMenuItem)
                    }
                }
                //applyCustomFont(mi)
            }
            prepareListDataMsHome()
            mMenuAdapter = ExpandableListAdapter(
                this,
                listDataHeader!!, listDataChild!!,
                expandableList!!
            )
            expandableList!!.setAdapter(mMenuAdapter)


            expandableList!!.setOnGroupClickListener { expandableListView, view, i, l ->
                false
            }
            expandableList!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id -> //Nothing here ever fires
                if (groupPosition == 5 && childPosition == 0) {
                    onAlert()
                }
                if (groupPosition == 0 && childPosition == 0) {
                    val i = Intent(this, AcikKartlarActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 0 && childPosition == 1) {
                    val i = Intent(this, KapaliKartlarActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 0 && childPosition == 2) {
                    val i = Intent(this, TahsilatiYapilmayanActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 0 && childPosition == 3) {
                    val i = Intent(this, OncekiOnarimlarActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 0 && childPosition == 4) {
                    val i = Intent(this, SilinenKabulListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 1 && childPosition == 1) {
                    val i = Intent(this, TahsilatEkleActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("PlasiyerId", intent.getStringExtra("PlasiyerId"))
                    i.putExtra("PlasiyerAdi", intent.getStringExtra("PlasiyerAdi"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 1 && childPosition == 4) {
                    val i = Intent(this, OdemeEkleActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("PlasiyerId", intent.getStringExtra("PlasiyerId"))
                    i.putExtra("PlasiyerAdi", intent.getStringExtra("PlasiyerAdi"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 1 && childPosition == 3) {
                    val i = Intent(this, BugunYapilacakTahsilatlarActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 1 && childPosition == 6) {
                    val i = Intent(this, BugunYapilacakOdemelerActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 1 && childPosition == 7) {
                    val i = Intent(this, TumAlacakOdemeActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 4 && childPosition == 0) {
                    val i = Intent(this, GelenEFaturaActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("entegrator", intent.getStringExtra("entegrator"))
                    i.putExtra("entegratorkullanici", intent.getStringExtra("entegratorkullanici"))
                    i.putExtra("entegratorsifre", intent.getStringExtra("entegratorsifre"))
                    i.putExtra("vergino", intent.getStringExtra("vergino"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 4 && childPosition == 1) {
                    val i = Intent(this, GidenFaturaActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("entegrator", intent.getStringExtra("entegrator"))
                    i.putExtra("entegratorkullanici", intent.getStringExtra("entegratorkullanici"))
                    i.putExtra("entegratorsifre", intent.getStringExtra("entegratorsifre"))
                    i.putExtra("vergino", intent.getStringExtra("vergino"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 1 && childPosition == 0) {
                    val i = Intent(this, CariListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("PlasiyerId", intent.getStringExtra("PlasiyerId"))
                    i.putExtra("PlasiyerAdi", intent.getStringExtra("PlasiyerAdi"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 2 && childPosition == 0) {
                    val i = Intent(this, StokListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 2 && childPosition == 1) {
                    val i = Intent(this, OrijinalStokListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 3 && childPosition == 0) {
                    val i = Intent(this, EvrakSatisActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 3 && childPosition == 1) {
                    val i = Intent(this, AlisEvrakActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 1 && childPosition == 2) {
                    val i = Intent(this, TahsilatListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 1 && childPosition == 5) {
                    val i = Intent(this, OdemeListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    startActivity(i)

                }
                if (groupPosition == 4 && childPosition == 2) {
                    Toast.makeText(this, "Yapım aşamasında....", Toast.LENGTH_LONG).show()
                }
                true
            }
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.menu_animation)
            navigationView.startAnimation(animation)


        } else {
            prepareListData()
            inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            /*
        llProfileClick!!.setOnClickListener(View.OnClickListener {
            ivFilter!!.setVisibility(View.GONE)
            navItemIndex = 9
            cURRENTTAG = tAGPROFILESETINGS
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            fragmentTransaction.replace(R.id.frame, ProfileSettingActivity())
            fragmentTransaction.commitAllowingStateLoss()
            drawer!!.closeDrawers()
        })*/
            menuLeftIV!!.setOnClickListener(View.OnClickListener { drawerOpen() })
            setUpNavigationView()

            val menu = navigationView!!.menu
            expandableList =
                findViewById<View>(R.id.navigationmenu) as ExpandableListView
            val navigationView =
                findViewById<View>(R.id.navigationview) as NavigationView

            navigationView?.let { setupDrawerContent(it) }
            if (navigationView != null) {
                setupDrawerContent(navigationView);
            }
            //  changeColorItem(menu, R.id.nav_home_features)
            //  changeColorItem(menu, R.id.nav_bookings_and_job)
            //  changeColorItem(menu, R.id.nav_personal)
            //  changeColorItem(menu, R.id.other)

            for (i in 0 until menu.size()) {
                val mi = menu.getItem(i)
                val subMenu = mi.subMenu
                if (subMenu != null && subMenu.size() > 0) {
                    for (j in 0 until subMenu.size()) {
                        val subMenuItem = subMenu.getItem(j)
                        // applyCustomFont(subMenuItem)
                    }
                }
                //applyCustomFont(mi)
            }
            mMenuAdapter = ExpandableListAdapter(
                this,
                listDataHeader!!, listDataChild!!,
                expandableList!!
            )
            expandableList!!.setAdapter(mMenuAdapter)


            expandableList!!.setOnGroupClickListener { expandableListView, view, i, l ->
                false
            }
            expandableList!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id -> //Nothing here ever fires
                if (groupPosition == 4 && childPosition == 0) {
                    onAlert()
                }
                /*if (groupPosition == 0 && childPosition == 0) {
                    val i = Intent(this, AcikKartlarActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)
                }
                if (groupPosition == 0 && childPosition == 1) {
                    val i = Intent(this, KapaliKartlarActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 0 && childPosition == 2) {
                    val i = Intent(this, TahsilatiYapilmayanActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 0 && childPosition == 3) {
                    val i = Intent(this, OncekiOnarimlarActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 0 && childPosition == 4) {
                    val i = Intent(this, SilinenKabulListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }*/
                if (groupPosition == 0 && childPosition == 1) {
                    val i = Intent(this, TahsilatEkleActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("PlasiyerId", intent.getStringExtra("PlasiyerId"))
                    i.putExtra("PlasiyerAdi", intent.getStringExtra("PlasiyerAdi"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)
                    if (drawer!!.isDrawerOpen(GravityCompat.START)) {
                        drawer!!.closeDrawers()
                    }


                }
                if (groupPosition == 0 && childPosition == 4) {
                    val i = Intent(this, OdemeEkleActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("PlasiyerId", intent.getStringExtra("PlasiyerId"))
                    i.putExtra("PlasiyerAdi", intent.getStringExtra("PlasiyerAdi"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)
                    if (drawer!!.isDrawerOpen(GravityCompat.START)) {
                        drawer!!.closeDrawers()
                    }

                }
                if (groupPosition == 0 && childPosition == 3) {
                    val i = Intent(this, BugunYapilacakTahsilatlarActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)
                    drawer!!.closeDrawers()

                }
                if (groupPosition == 0 && childPosition == 6) {
                    val i = Intent(this, BugunYapilacakOdemelerActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 0 && childPosition == 7) {
                    val i = Intent(this, TumAlacakOdemeActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 3 && childPosition == 0) {
                    val i = Intent(this, GelenEFaturaActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("entegrator", intent.getStringExtra("entegrator"))
                    i.putExtra("entegratorkullanici", intent.getStringExtra("entegratorkullanici"))
                    i.putExtra("entegratorsifre", intent.getStringExtra("entegratorsifre"))
                    i.putExtra("vergino", intent.getStringExtra("vergino"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 3 && childPosition == 1) {
                    val i = Intent(this, GidenFaturaActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("entegrator", intent.getStringExtra("entegrator"))
                    i.putExtra("entegratorkullanici", intent.getStringExtra("entegratorkullanici"))
                    i.putExtra("entegratorsifre", intent.getStringExtra("entegratorsifre"))
                    i.putExtra("vergino", intent.getStringExtra("vergino"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 0 && childPosition == 0) {
                    val i = Intent(this, CariListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("PlasiyerId", intent.getStringExtra("PlasiyerId"))
                    i.putExtra("PlasiyerAdi", intent.getStringExtra("PlasiyerAdi"))
                    i.putExtra("KullaniciId", intent.getStringExtra("KullaniciId"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 1 && childPosition == 0) {
                    val i = Intent(this, StokListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 1 && childPosition == 1) {
                    val i = Intent(this, OrijinalStokListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 2 && childPosition == 0) {
                    val i = Intent(this, EvrakSatisActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 2 && childPosition == 1) {
                    val i = Intent(this, AlisEvrakActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 0 && childPosition == 2) {
                    val i = Intent(this, TahsilatListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 0 && childPosition == 5) {
                    val i = Intent(this, OdemeListesiActivity::class.java)
                    i.putExtra("username", intent.getStringExtra("username"))
                    i.putExtra("uid", intent.getStringExtra("uid"))
                    i.putExtra("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    startActivity(i)

                }
                if (groupPosition == 3 && childPosition == 2) {
                    Toast.makeText(this, "Yapım aşamasında....", Toast.LENGTH_LONG).show()
                }
                true
            }
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.menu_animation)
            navigationView.startAnimation(animation)
        }
    }

    override fun onStart() {
        super.onStart()
        drawer!!.closeDrawers()
    }

    override fun onResume() {
        super.onResume()
        drawer!!.closeDrawers()
    }

    override fun onDestroy() {
        super.onDestroy()
        drawer!!.closeDrawers()
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
            object : NavigationView.OnNavigationItemSelectedListener {
                @SuppressLint("SuspiciousIndentation")
                override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                    menuItem.setChecked(true)
                    if (menuItem.itemId == 0)
                        drawer!!.closeDrawers()
                    //  Toast.makeText(this@HomeActivity,menuItem.itemId,Toast.LENGTH_LONG).show()
                    return true
                }
            })
    }

    fun drawerOpen() {
        try {
            inputManager!!.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        } else {
            drawer!!.openDrawer(GravityCompat.START)
        }
    }

    private fun loadHomeFragment(fragment: Fragment, TAG: String) {
        val mPendingRunnable = Runnable {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            fragmentTransaction.replace(R.id.frame, fragment, TAG)
            fragmentTransaction.commitAllowingStateLoss()
            ivFilter!!.setVisibility(View.VISIBLE)
        }
        mHandler!!.post(mPendingRunnable)
        drawer!!.closeDrawers()
        invalidateOptionsMenu()
    }

    /*
        fun changeColorItem(menu: Menu, id: Int) {
            val tools = menu.findItem(id)
            val s = SpannableString(tools.title)
            s.setSpan(TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length, 0)
            tools.title = s
        }
    */
    private fun setUpNavigationView() {
        navigationView!!.setNavigationItemSelectedListener { menuItem ->
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    ivFilter!!.visibility = View.VISIBLE
                    navItemIndex = 0
                    cURRENTTAG = tAGMAIN
                    val fragobj = Home()
                    bundlem.putString("username", intent.getStringExtra("username"))
                    bundlem.putString("uid", intent.getStringExtra("uid"))
                    bundlem.putString("FirmaLogo", intent.getStringExtra("FirmaLogo"))
                    bundlem.putString("KullaniciId", intent.getStringExtra("KullaniciId"))
                    fragobj.arguments = bundlem
                    var fragmentmaneger = this.supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, fragobj)
                        .commit()
                }

                R.id.nav_chat -> {
                    ivFilter!!.visibility = View.GONE
                    navItemIndex = 1
                    //cURRENTTAG = tAGCHAT
                    // fragmentTransaction.replace(R.id.frame, ChatList())
                }

                R.id.nav_booking -> {
                    ivFilter!!.visibility = View.GONE
                    navItemIndex = 2
                    // cURRENTTAG = tAGBOOKING
                    //fragmentTransaction.replace(R.id.frame, MyBooking())
                }

                R.id.nav_jobs -> {
                    ivFilter!!.visibility = View.GONE
                    navItemIndex = 3
                    // cURRENTTAG = tAGJOBS
                    // fragmentTransaction.replace(R.id.frame, Jobs())
                }

                R.id.nav_appointment -> {
                    ivFilter!!.visibility = View.GONE
                    navItemIndex = 4
                    //cURRENTTAG = tAGAPPOINTMENT
                    // fragmentTransaction.replace(R.id.frame, AppointmentFrag())
                }

                R.id.nav_notification -> {
                    ivFilter!!.visibility = View.GONE
                    navItemIndex = 5
                    // cURRENTTAG = tAGNOTIFICATION
                    //fragmentTransaction.replace(R.id.frame, NotificationActivity())
                }

                R.id.nav_history -> {
                    ivFilter!!.visibility = View.GONE
                    navItemIndex = 6
                    // cURRENTTAG = tAGDISCOUNT
                    // fragmentTransaction.replace(R.id.frame, HistoryFragment())
                }

                R.id.nav_discount -> {
                    ivFilter!!.visibility = View.GONE
                    navItemIndex = 7
                    // cURRENTTAG = tAGHISTORY
                    // fragmentTransaction.replace(R.id.frame, GetDiscountActivity())
                }

                R.id.nav_wallet -> {
                    ivFilter!!.visibility = View.GONE
                    navItemIndex = 8
                    //cURRENTTAG = tAGWALLET
                    //fragmentTransaction.replace(R.id.frame, Wallet())
                }

                R.id.nav_profilesetting -> {
                    navItemIndex = 9
                    cURRENTTAG = tAGPROFILESETINGS
                    ivFilter!!.visibility = View.GONE
                    fragmentTransaction.replace(R.id.frame, ProfileSettingActivity())
                }

                R.id.nav_tickets -> {
                    navItemIndex = 10
                    //cURRENTTAG = tAGTICKETS
                    ivFilter!!.visibility = View.GONE
                    //fragmentTransaction.replace(R.id.frame, Tickets())
                }

                R.id.nav_logout -> {
                    //  prefrence.clearAllPreferences()
                    //val intent = Intent(this@BaseActivity, ChooseAppActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                else -> {}
            }
            fragmentTransaction.commitAllowingStateLoss()
            drawer!!.closeDrawers()
            if (menuItem.isChecked) {
                menuItem.isChecked = false
            } else {
                menuItem.isChecked = true
            }
            menuItem.isChecked = true
            true
        }
    }

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawers()
            return
        }
        if (shouldLoadHomeFragOnBackPress && navItemIndex != 0) {
            navItemIndex = 0
            cURRENTTAG = tAGMAIN
            loadHomeFragment(Home(), cURRENTTAG)
            return
        }
        clickDone()
    }

    fun clickDone() {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.app_name))
            .setMessage(resources.getString(R.string.closeMsg))
            .setPositiveButton(
                resources.getString(R.string.yes)
            ) { dialog, which ->
                dialog.dismiss()
                val i = Intent()
                i.action = Intent.ACTION_MAIN
                i.addCategory(Intent.CATEGORY_HOME)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                finish()
            }
            .setNegativeButton(
                resources.getString(R.string.no)
            ) { dialog, which -> dialog.dismiss() }
            .show()
    }

    private fun prepareListDataMsHome() {
        listDataHeader = ArrayList<ExpandedMenuModel>()
        listDataChild = HashMap<ExpandedMenuModel, List<String>>()
        val item1 = ExpandedMenuModel()
        item1.setIconName(getString(R.string.servis_islemleri))
        item1.setIconImg(R.drawable.car)
        val heading1: MutableList<String> = ArrayList()
        heading1.add(getString(R.string.acik_onarimlar))
        heading1.add(getString(R.string.kapali_onarimlar))
        heading1.add(getString(R.string.tahsilati_yapilmayan_onarimlar))
        heading1.add(getString(R.string.onceki_onarimlar))
        heading1.add(getString(R.string.silinen_kabul_listesi))
        heading1.add(getString(R.string.servis_gun_sonu_raporu))
        ///
        val item2 = ExpandedMenuModel()
        item2.setIconName(getString(R.string.finans_islemleri))
        item2.setIconImg(R.drawable.money)
        val heading2: MutableList<String> = ArrayList()
        heading2.add(getString(R.string.cari_listesi))
        heading2.add(getString(R.string.tahsilat_ekle))
        heading2.add(getString(R.string.tahsilatlar_listesi))
        heading2.add(getString(R.string.bugun_yapilacak_tahsilatlar))
        heading2.add(getString(R.string.odeme_ekle))
        heading2.add(getString(R.string.odeme_listesi))
        heading2.add(getString(R.string.bugun_yapilacak_odemeler))
        heading2.add(getString(R.string.mizan))
        ///
        val item3 = ExpandedMenuModel()
        item3.setIconName(getString(R.string.stok_islemleri))
        item3.setIconImg(R.drawable.stok)
        val heading3: MutableList<String> = ArrayList()
        heading3.add(getString(R.string.stok_listemiz))
        heading3.add(getString(R.string.orijinal_stok_listesi))
        //
        val item4 = ExpandedMenuModel()
        item4.setIconName(getString(R.string.alim_satim))
        item4.setIconImg(R.drawable.list)
        val heading4: MutableList<String> = ArrayList()
        //heading4.add(getString(R.string.satis_islemi))
        // heading4.add(getString(R.string.alis_islemi))
        heading4.add(getString(R.string.satis_evrak_listesi))
        heading4.add(getString(R.string.alis_evrak_listesi))
        ///
        val item5 = ExpandedMenuModel()
        item5.setIconName(getString(R.string.e_islemler))
        item5.setIconImg(R.drawable.fatura)
        val heading5: MutableList<String> = ArrayList()
        heading5.add(getString(R.string.gelen_fatura))
        heading5.add(getString(R.string.giden_fatura))
        heading5.add(getString(R.string.giden_irsaliye))
        val item6 = ExpandedMenuModel()
        item6.setIconName(getString(R.string.cikis_yap))
        item6.setIconImg(R.drawable.logout)
        val heading6: MutableList<String> = ArrayList()
        heading6.add(getString(R.string.cikis_yap))
        // Adding data header
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item1)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item2)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item3)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item4)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item5)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item6)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(0), heading1)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(1), heading2)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(2), heading3)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(3), heading4)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(4), heading5)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(5), heading6)


    }

    private fun prepareListData() {
        listDataHeader = ArrayList<ExpandedMenuModel>()
        listDataChild = HashMap<ExpandedMenuModel, List<String>>()

        /*val item1 = ExpandedMenuModel()
        item1.setIconName(getString(R.string.servis_islemleri))
        item1.setIconImg(R.drawable.car)
        val heading1: MutableList<String> = ArrayList()
        heading1.add(getString(R.string.acik_onarimlar))
        heading1.add(getString(R.string.kapali_onarimlar))
        heading1.add(getString(R.string.tahsilati_yapilmayan_onarimlar))
        heading1.add(getString(R.string.onceki_onarimlar))
        heading1.add(getString(R.string.silinen_kabul_listesi))
        heading1.add(getString(R.string.servis_gun_sonu_raporu))*/
        ///
        val item2 = ExpandedMenuModel()
        item2.setIconName(getString(R.string.finans_islemleri))
        item2.setIconImg(R.drawable.money)
        val heading2: MutableList<String> = ArrayList()
        heading2.add(getString(R.string.cari_listesi))
        heading2.add(getString(R.string.tahsilat_ekle))
        heading2.add(getString(R.string.tahsilatlar_listesi))
        heading2.add(getString(R.string.bugun_yapilacak_tahsilatlar))
        heading2.add(getString(R.string.odeme_ekle))
        heading2.add(getString(R.string.odeme_listesi))
        heading2.add(getString(R.string.bugun_yapilacak_odemeler))
        heading2.add(getString(R.string.mizan))
        ///
        val item3 = ExpandedMenuModel()
        item3.setIconName(getString(R.string.stok_islemleri))
        item3.setIconImg(R.drawable.stok)
        val heading3: MutableList<String> = ArrayList()
        heading3.add(getString(R.string.stok_listemiz))
        heading3.add(getString(R.string.orijinal_stok_listesi))
        //
        val item4 = ExpandedMenuModel()
        item4.setIconName(getString(R.string.alim_satim))
        item4.setIconImg(R.drawable.list)
        val heading4: MutableList<String> = ArrayList()
        //heading4.add(getString(R.string.satis_islemi))
        // heading4.add(getString(R.string.alis_islemi))
        heading4.add(getString(R.string.satis_evrak_listesi))
        heading4.add(getString(R.string.alis_evrak_listesi))
        ///
        val item5 = ExpandedMenuModel()
        item5.setIconName(getString(R.string.e_islemler))
        item5.setIconImg(R.drawable.fatura)
        val heading5: MutableList<String> = ArrayList()
        heading5.add(getString(R.string.gelen_fatura))
        heading5.add(getString(R.string.giden_fatura))
        heading5.add(getString(R.string.giden_irsaliye))
        val item6 = ExpandedMenuModel()
        item6.setIconName(getString(R.string.cikis_yap))
        item6.setIconImg(R.drawable.logout)
        val heading6: MutableList<String> = ArrayList()
        heading6.add(getString(R.string.cikis_yap))
        // Adding data header
        //(listDataHeader as ArrayList<ExpandedMenuModel>).add(item1)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item2)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item3)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item4)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item5)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item6)
        //listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(0),heading1)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(0), heading2)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(1), heading3)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(2), heading4)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(3), heading5)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(4), heading6)


    }

    private fun onAlert() {
        val builder = AlertDialog.Builder(this)

        builder
            .setMessage("ÇIKIŞ YAPILSIN MI?")
            .setPositiveButton("Evet") { dialog, which ->
                cikisYap()
            }
            .setNegativeButton("Hayır") { dialog, which ->
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun cikisYap() {
        //cikisYapApi(intent.getStringExtra("id"))
        val sheredpreferens = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        sheredpreferens.edit().remove("adi").commit()
        sheredpreferens.edit().remove("soyadi").commit()
        sheredpreferens.edit().remove("username").commit()
        sheredpreferens.edit().remove("uid").commit()
        val editor = sheredpreferens.edit()
        editor.commit()
        editor.clear()
        editor.remove("adi")
        editor.remove("soyadi")
        editor.remove("username")
        editor.remove("uid")
        val i = Intent(this, SingInActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun padData(text: String) {
        val budlem = Bundle()
        budlem.putString("username", intent.getStringExtra("username"))
        val transaction = this.supportFragmentManager.beginTransaction()
        val book = MyBooking()
        book.arguments = bundlem
        transaction.replace(R.id.frame, book).commit()
    }


}