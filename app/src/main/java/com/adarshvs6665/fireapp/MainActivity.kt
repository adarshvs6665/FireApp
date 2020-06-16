package com.adarshvs6665.fireapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import java.math.BigInteger
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    lateinit var name : EditText
    lateinit var button: Button
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.name)
        button = findViewById(R.id.button)
        password = findViewById(R.id.password)

        button.setOnClickListener {
            saveHero()
            name.setText("")
            password.setText("")
        }
    }

    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    private fun saveHero() {
        val name1 = name.text.toString().trim()
        val password1 = password.text.toString().trim()
        val hashedPass = password1.md5()
        if (name1.isEmpty()) {
            name.error = "Please enter a username"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("heroes")
        val heroId = ref.push().key

        val hero = Hero(heroId, name1, hashedPass)

        ref.child(heroId!!).setValue(hero).addOnCompleteListener {
            Toast.makeText(applicationContext, "Hero saved successfully", Toast.LENGTH_SHORT).show()
        }
    }
}