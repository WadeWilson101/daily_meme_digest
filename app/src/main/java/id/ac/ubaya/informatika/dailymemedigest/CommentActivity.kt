package id.ac.ubaya.informatika.dailymemedigest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.card_home.view.*
import kotlinx.android.synthetic.main.fragment_myhome.view.*
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val meme_url:String = intent.getStringExtra("img_url").toString()
        val topTxt:String = intent.getStringExtra("top_text").toString()
        val botTxt:String = intent.getStringExtra("bot_text").toString()
        val likes:String = intent.getStringExtra("likes_").toString()

        val usr_id:String = intent.getStringExtra("user_id").toString()
        val memes_id:String = intent.getStringExtra("memes_id").toString()




        Picasso.get().load(meme_url).into(imageComment)
        txtLikesComment.text = likes + " LIKES"
        txtTopComment.text = topTxt
        txtBotComment.text = botTxt

        var comments: ArrayList<Comments> = ArrayList()

        ///// volley

        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.fun/native/160420152/memes_api/get_comments.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                Log.d("apiresult", it)
                // convert whole json string into jsonobject
                val obj = JSONObject(it)
                // check the result value
                if (obj.getString("result") == "OK"){
                    // OK proceed to read the data json array
                    val data = obj.getJSONArray("data")
                    // for each JSON Object in the json Array..
                    for (i in 0 until data.length() ){
                        // retrieve the JSON object
                        val playObj = data.getJSONObject(i)
                        // Create a Playlist kotlin object, retrieveing the values from the json object
                        val comment = Comments(
                            playObj.getString("first_name"),
                            playObj.getString("last_name"),
                            playObj.getString("comment"),
                            playObj.getString("com_date"),
                            playObj.getString("avt_img"),







                            )
                        // add the playlist to the ArrayList
                        comments.add(comment)
                    }
                    updateList(comments)

                    // TODO: update the UI to show the values
                    Log.d("playlists", comments.toString())
                }

            },
            Response.ErrorListener {
                Log.d("checkparams", it.message.toString())
            }
        )
        /// body
        {
            override fun getParams(): MutableMap<String, String>? {
                return hashMapOf("meme_id" to memes_id.toString()

                )
            }
        }

        q.add(stringRequest)


        btnSendComm.setOnClickListener {

            if (editTextComment.text.toString() != ""){
                val q = Volley.newRequestQueue(this)
                //val url = "http://10.0.2.2/music/set_likes.php"
                val url = "https://ubaya.fun/native/160420152/memes_api/set_comments.php"

                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
                        Log.d("checkparams", it)
                        //val obj = JSONObject(it)
                        val obj = it
                        if (obj=="OK"){
                            Toast.makeText(this, "Comments are added",
                                Toast.LENGTH_LONG).show();

                        }
//                    else{
//                        Log.e("updateerror",obj.getString("message"))
//                    }
                        else if (obj=="NG"){

                        }

                    },
                    Response.ErrorListener {
                        Log.d("checkparams", it.message.toString())
                    }
                )
                /// body
                {
                    override fun getParams(): MutableMap<String, String>? {
                        return hashMapOf("memes_id" to memes_id.toString(),
                            "user_id" to usr_id.toString(),
                            "comm_txt" to editTextComment.text.toString(),
                            "date_txt" to getDateTime()
                        )
                    }
                }

                q.add(stringRequest)


            }

            else{

                Toast.makeText(this, "Please fill in comments",
                    Toast.LENGTH_SHORT).show();

            }



        }


    }

    fun updateList(comments: ArrayList<Comments>){
        val llm = LinearLayoutManager(this)
        commentView?.let {
            it.layoutManager = llm
            it.setHasFixedSize(true)
            it.adapter = CommentAdapter(comments) // passing user_id (4)


        }

    }

    /// to get current time
    private fun getDateTime(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
        val date = Date()
        return dateFormat.format(date)
    }





    }
