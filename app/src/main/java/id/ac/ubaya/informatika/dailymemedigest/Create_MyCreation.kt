package id.ac.ubaya.informatika.dailymemedigest

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_create_my_creation.*
import kotlinx.android.synthetic.main.card_home.view.*
import org.json.JSONObject


class Create_MyCreation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_my_creation)
        // fullscreen
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // SHARED STUFF

        // shared prefs Key
        val SHARED_USERNAME_ID = "usernameid"

        val sharedPrefences = getSharedPreferences(packageName, Context.MODE_PRIVATE)

        val username_id = sharedPrefences.getString(SHARED_USERNAME_ID,"")

        Toast.makeText(this, username_id,
            Toast.LENGTH_LONG).show();

        editText_Create_imgURL.addTextChangedListener {
//            Picasso.get().load(editText_Create_imgURL.text.toString())
//                .into(imgCreateMeme)
            // use this to avoid errors when url is not valid
            if (editText_Create_imgURL.text.length > 1){
                val builder = Picasso.Builder(this)
                builder.listener { picasso, uri, exception -> exception.printStackTrace() }
                builder.build().load(editText_Create_imgURL.text.toString()).into(imgCreateMeme)

            }
            else{
                Toast.makeText(this, "Please fill the correct URL",
                    Toast.LENGTH_LONG).show();

            }








        }

        editText_Create_topTxt.addTextChangedListener {
            txtTopCreate.text = editText_Create_topTxt.text.toString()
        }

        editText_Create_botTxt.addTextChangedListener {
            txtBotCreate.text = editText_Create_botTxt.text.toString()
        }

        button_Create_CreateMeme.setOnClickListener {
            if (editText_Create_imgURL.text.toString() != ""){
                var top_text = editText_Create_topTxt.text.toString()
                var bot_text = editText_Create_botTxt.text.toString()
                if (editText_Create_topTxt.text.toString() == "" || editText_Create_topTxt.text.toString() == null || editText_Create_topTxt == null){
                    top_text = " "
                }
                if (editText_Create_botTxt.text.toString() == "" || editText_Create_botTxt.text.toString() == null || editText_Create_botTxt == null){
                    bot_text = " "
                }

                val queue2 = Volley.newRequestQueue(this)
                val url2 = "https://ubaya.fun/native/160420152/memes_api/add_memes.php"

                val stringReq2 : StringRequest =
                    object : StringRequest(
                        Method.POST, url2,
                        Response.Listener { response ->
                            // response
                            Toast.makeText(this, response,
                                Toast.LENGTH_LONG).show();
                            Log.d("checkparams", response)
                            val obj = JSONObject(response)
                            if (obj.getString("result")=="OK"){

                                Toast.makeText(this, "Memes Successfully added ! ",
                                    Toast.LENGTH_LONG).show();

//                            val intent = Intent(this, MainActivity::class.java)
//                            startActivity(intent)


                            }

                            else{
                                Toast.makeText(this, "Memes not added, please try again later",
                                    Toast.LENGTH_LONG).show();
                            }



                        },
                        Response.ErrorListener { error ->
                            Log.d("API", "error => $error")
                        }
                    ){
                        override fun getParams(): MutableMap<String, String>? {
                            return hashMapOf("iduser" to username_id!!.toString(),
                                "img_url" to editText_Create_imgURL.text.toString(),
                                "bottom_txt" to bot_text,
                                "top_txt" to top_text

                            )
                        }
                    }
                queue2.add(stringReq2)



            }
            else{
                Toast.makeText(this, "Please fill the URL",
                    Toast.LENGTH_LONG).show();

            }


        }






    }
}