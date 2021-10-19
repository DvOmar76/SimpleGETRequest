package com.example.simplegetrequest

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
    lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchData()
    }

    fun fetchData() {
        funProgressDialog()
        if (apiInterface != null) {
            apiInterface.getData()?.enqueue(object : Callback<Data?> {
                override fun onResponse(call: Call<Data?>, response: Response<Data?>) {
                    progressDialog.dismiss()
                    val ob=response.body()!!
                    var text=""
                    for (name in ob){
                        text+=name.name+"\n"
                    }
                    textView.text=text
                 }

                override fun onFailure(call: Call<Data?>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
    fun funProgressDialog(){
        progressDialog= ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

    }
}