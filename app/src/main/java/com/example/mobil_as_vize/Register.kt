package com.example.mobil_as_vize

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mobil_as_vize.databinding.ActivityMainBinding
import com.example.mobil_as_vize.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {

    private lateinit var binding  : ActivityRegisterBinding
            lateinit var db : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //     setContentView(R.layout.activity_register)

        binding  = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root // tabi bunun icin build.gradle.kts den (module olandan 37.satıra bak buildFeatures kısmını eklemek gerekiyor)
        setContentView(view)

        db = openOrCreateDatabase("TestDB" , MODE_PRIVATE,null)

    }


    fun register(view:View){ //degisken isimleri binding icin düzenlenecek

        val nameView = binding.registerName // bu sekilde alırsak nesne olarak alır sonradan alıp text olarak cagırabiliriz
        val name = binding.registerName.text.toString() // bu sekilde de sadece textini alır
        val surname = binding.registerSurname.text.toString()
        val email = binding.registerEmail.text.toString()
        val password = binding.registerPassword.text.toString()
        val passwordRepeat = binding.registerPasswordAgain.text.toString()


        if(name.equals("") || surname.equals("") || email.equals("") || password.equals("") || passwordRepeat.equals(""))
        {
            Toast.makeText(this, "Lütfen bilgileri eksiksiz giriniz" ,Toast.LENGTH_SHORT).show()
        }
        else {
            val result : Cursor = db.rawQuery("SELECT * FROM users WHERE email='$email'", null)
            if(result.count > 0)
            {
                Toast.makeText( this,"Bu email zaten kullanımda" , Toast.LENGTH_SHORT).show()
            }
            else {
            if(password.equals(passwordRepeat))
            {
                val passwordLength = password.length
                if(passwordLength < 6)
                {
                    Toast.makeText(this, "Lütfen Şifrenizi 6 karakterden fazla olacak şekilde oluşturunuz" ,Toast.LENGTH_SHORT).show()  // en az bir harf olmalı yap REGEX
                }
                else {
                    db.execSQL("INSERT INTO users (name, surname, email, password) VALUES ('$name', '$surname', '$email', '$password')")
                        Toast.makeText( this, "Kullanıcı Hesabı Oluşturuldu",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,Login::class.java))
                }

            }
            else {
                Toast.makeText( this, "Girdiginiz şifreler eşleşmiyor" ,Toast.LENGTH_SHORT).show()
            }
            }
        }
    }

}