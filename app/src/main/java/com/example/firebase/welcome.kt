package com.example.firebase
// you have put this activity as launcher activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.databinding.ActivityWelcomeBinding


class welcome : AppCompatActivity() {

    private val binding : ActivityWelcomeBinding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

       Handler(Looper.getMainLooper()).postDelayed({
           startActivity(Intent(this,loginActivity::class.java))
           finish()
       },3000)

        val text = "Welcome"
        val spannableString = SpannableString(text)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#FF0000")), 0, text.length, 0)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#312222")), 5, text.length, 0)
        binding.welcome.text = spannableString
    }
}