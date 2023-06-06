package id.ac.ubaya.informatika.dailymemedigest

import android.content.Context
import android.content.ContextParams
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject


class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // fullscreen
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar()?.hide()

        // SHARED PREFS KEY

        val SHARED_USERNAME_ID = "usernameid"
        val SHARED_USERNAME = "username"
        val FIRST_NAME = "firstname"
        val LAST_NAME = "lastname"
        val REG_DATE = "regdate"
        val AVT_IMG = "avtimage"
        val PRIVACY_SET = "privacyset"

        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        /////

        button_Login_SignIn.setOnClickListener {

            val queue = Volley.newRequestQueue(this)
            val url = "https://ubaya.fun/native/160420152/memes_api/get_user.php"

            val stringReq : StringRequest =
                object : StringRequest(Method.POST, url,
                    Response.Listener { response ->
                        // response
                        Log.d("checkparams", response)
                        if (response != "error"){
                            var obj = JSONArray(response)
                            // grab name as an sucessfull login
                            var name = obj.getJSONObject(0).getString("username")
                            var nameid = obj.getJSONObject(0).getString("idusers")
                            var firstname = obj.getJSONObject(0).getString("first_name")
                            var lastname = obj.getJSONObject(0).getString("last_name")
                            var reg_date = obj.getJSONObject(0).getString("reg_date")
                            var avt_img = obj.getJSONObject(0).getString("avt_img")
                            var priv_set = obj.getJSONObject(0).getString("privacy_set")
                            if (name != ""){
                                Toast.makeText(this, "Login sucessfull "+name,
                                    Toast.LENGTH_LONG).show();
                                // store user data in sharedprefences
                                val editor = sharedPreferences.edit {
                                    putString(SHARED_USERNAME, name)
                                    putString(SHARED_USERNAME_ID, nameid)
                                    putString(FIRST_NAME, firstname)
                                    putString(LAST_NAME, lastname)
                                    putString(REG_DATE, reg_date)
                                    putString(AVT_IMG, avt_img)
                                    putString(PRIVACY_SET, priv_set)
                                }
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)


                            }



//                            Toast.makeText(this, response,
//                                Toast.LENGTH_LONG).show();

                        }

                        else{
                            Toast.makeText(this, "Please check your username and password",
                                Toast.LENGTH_LONG).show();
                        }



                    },
                    Response.ErrorListener { error ->
                        Log.d("API", "error => $error")
                    }
                ){
                    override fun getParams(): MutableMap<String, String>? {
                        return hashMapOf("username" to editText_Login_username.text.toString(),
                            "password" to editText_Login_password.text.toString()
                        )
                    }
                }
            queue.add(stringReq)


        }

        // register button

        button_Login_CreateAccount.setOnClickListener {
            val intent = Intent(this, Create_Account::class.java)
            startActivity(intent)

        }
    }
}