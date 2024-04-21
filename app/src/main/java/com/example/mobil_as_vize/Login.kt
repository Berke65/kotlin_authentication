package com.example.mobil_as_vize

import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mobil_as_vize.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
            lateinit var db : SQLiteDatabase
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences("UserSession" , MODE_PRIVATE)

        db = openOrCreateDatabase("TestDB" , MODE_PRIVATE , null)

    }

    fun openRegister(view: View) {
        startActivity(Intent(this, Register::class.java))
    }

    fun openLostPasswd(view: View) {
        startActivity(Intent(this,LostPassword::class.java))
    }

    fun processedLogin(view: View) {
        val loginEmail = binding.loginEmail.text.toString()
        val loginPasswd = binding.loginPasswd.text.toString()

        if(loginEmail.equals("") && loginPasswd.equals(""))
        {
            Toast.makeText(this, "Lütfen bilgileri eksiksiz giriniz" ,Toast.LENGTH_SHORT).show()

        }
        else {

            val result : Cursor = db.rawQuery("SELECT * FROM users WHERE email='$loginEmail'", null)

            if(result.count > 0) {
                result.moveToFirst()
                val userid = result.getInt(0)
                val name = result.getInt(1)
                val surname = result.getInt(2)
                val email = result.getInt(3)
                val password = result.getInt(4)

                Log.d("TEST" , "$userid , $name , $surname , $email , $password")

            if(password.equals(loginPasswd))
            {
                //login basarılı
                val intent = Intent(this, MainActivity::class.java)
            //    intent.putExtra("userid" , userid)
                sharedPreferences.edit().putInt("userid" , userid).apply()
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Şifre yanlış" ,Toast.LENGTH_SHORT).show()
            }
            }
            else {
                Toast.makeText( this,"Bu kullanıcı kayıtlı degil" ,Toast.LENGTH_SHORT).show()
            }
        }
    }
}