package com.example.diplom_analyzizchexk_analyziz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.diplom_analyzizchexk_analyziz.databinding.FragmentLoginBinding
import com.example.diplom_analyzizchexk_analyziz.retrofit.Login
import com.example.diplom_analyzizchexk_analyziz.retrofit.MainAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mainApi: MainAPI
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()
        binding.apply {
            buttonLogin.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_productsFragment)
            }
            buttonYes.setOnClickListener {
                auth(
                    Login(
                        loginInput.text.toString(),
                        numberInput.text.toString()
                    )
                )

            }
        }

    }
    private fun initRetrofit()
    {
        val interceptor= HttpLoggingInterceptor()
        interceptor.level= HttpLoggingInterceptor.Level.BODY

        val client= OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit= Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
         mainApi=retrofit.create(MainAPI::class.java)
    }
    private fun auth(login: Login)
    {
        CoroutineScope(Dispatchers.IO).launch {
            val response=mainApi.auth(login)
            requireActivity().runOnUiThread {
                binding.error.text = response.errorBody()?.string()
                val tokens=response.body()
                if(tokens!=null)
                    binding.token.text=tokens.token
            }
        }
    }
}