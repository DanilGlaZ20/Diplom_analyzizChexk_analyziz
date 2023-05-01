package com.example.diplom_analyzizchexk_analyziz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.diplom_analyzizchexk_analyziz.databinding.ActivityMainBinding
import com.example.diplom_analyzizchexk_analyziz.retrofit.Login
import com.example.diplom_analyzizchexk_analyziz.retrofit.MainAPI
import com.example.diplom_analyzizchexk_analyziz.retrofit.Token
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tv=findViewById<TextView>(R.id.login)
        val b=findViewById<Button>(R.id.button)

        val interceptor=HttpLoggingInterceptor()
        interceptor.level=HttpLoggingInterceptor.Level.BODY

        val client= OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit=Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val mainApi=retrofit.create(MainAPI::class.java)

        binding.button.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                val tokens:Token= mainApi.auth(
                    Login(
                            binding.login.text.toString(),
                            binding.number.text.toString()
                            )
                )
                runOnUiThread{
                    binding.apply{
                        tokenOUT.text=tokens.token
                    }
                }
            }
        }



    }
}