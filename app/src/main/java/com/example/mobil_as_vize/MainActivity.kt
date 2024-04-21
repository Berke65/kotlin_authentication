package com.example.mobil_as_vize

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mobil_as_vize.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding  : ActivityMainBinding
    lateinit var  context : Context
    lateinit var db : SQLiteDatabase
    private lateinit var sharedPrefences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_main)

        // ÖNEMLİİİİİİİİİİİİİİ

        binding  = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root // tabi bunun icin build.gradle.kts den (module olandan 37.satıra bak buildFeatures kısmını eklemek gerekiyor)
        sharedPrefences = getSharedPreferences("UserSession", MODE_PRIVATE)
        setContentView(view)   // bu binding sayesinde findviewbyid kullanmak yerine binding.kullanacagımız id yazmak yeterli oluyor

        context = this
        val dbFile = context.getDatabasePath("testDB")
        Log.i("DBtest" , dbFile.exists().toString())

            db = context.openOrCreateDatabase("testDB" , Context.MODE_PRIVATE , null)

        db.execSQL("CREATE TABLE IF NOT EXISTS users (userid INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT , surname TEXT, email TEXT , password TEXT)") // if not exists cümlecigi eger tablo yoksa olusturmak icin yazar

       // val userId = intent.getIntExtra("userId" , -1) // eğer bir userId bilgisi verisi alamaz isi kullanıcı giris veya kayıt icin o sayfaya yönlendiriyor
      val userid = sharedPrefences.getInt("userid" , -1)
        if (userid > 0)
        {

        } else {
            val loginIntent = Intent(this,Login::class.java)
            startActivity(loginIntent)
            finish()
        }

    }

    fun processLogout (view: View) {
        sharedPrefences.edit().remove("userid")
        val loginIntent  = Intent(this, Login::class.java)
        startActivity(loginIntent)
        finish()
    }

}