package id.ac.ubaya.informatika.dailymemedigest




import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_myhome.view.btnLikes
import kotlinx.android.synthetic.main.card_myhome.view.imageHome
import kotlinx.android.synthetic.main.card_myhome.view.txtBot
import kotlinx.android.synthetic.main.card_myhome.view.txtLikes
import kotlinx.android.synthetic.main.card_myhome.view.txtTop
import kotlinx.android.synthetic.main.card_myhome.view.*

// file untuk masing2 card mungkin ? yang di hold

class MyHomeAdapter(val homes: ArrayList<Home>, val usr_id: String) : // homes dari array homeFragment




    RecyclerView.Adapter<MyHomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(val v: View):RecyclerView.ViewHolder(v)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.card_myhome, parent,false)
        return HomeViewHolder(v)




    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val playlist = homes[position]
        val mem_id = playlist.id
        val url = homes[position].mem_img
        val topTxt = homes[position].top_txt
        val botTxt = homes[position].bot_txt
        Picasso.get().load(url).into(holder.v.imageHome)
        holder.v.txtLikes.text = homes[position].num_likes.toString()+ " LIKES"
        holder.v.txtMyComments.text = homes[position].comments_count.toString()+ " COMMENTS"
        holder.v.txtTop.text = topTxt
        holder.v.txtBot.text = botTxt

//                holder.v.fltbtn_add.setOnClickListener {
//
//
//
//
//
//
//
////                        val intent_2 = Intent(getActivity(holder.v.context),AddPlaylistActivity::class.java)
////
////
////
////                        getActivity(holder.v.context)!!.startActivity(intent_2)
//
//
//
//
//
//
//
//
//
//
////                        val intent = Intent(holder.v.getContext().applicationContext., AddPlaylistActivity::class.java)
////                        holder.v.context.startActivity(intent)
//
//
//                }
        holder.v.btnLikes.setOnClickListener {
            val q = Volley.newRequestQueue(holder.v.context)
            //val url = "http://10.0.2.2/music/set_likes.php"
            val url = "https://ubaya.fun/native/160420152/memes_api/set_likes.php"
            //val url = "http://192.168.0.13/music/set_likes.php"
//                        val stringRequest = StringRequest(
//                                Request.Method.POST,
//                                url,
//                                {
//                                        Log.d("volley result",it)
//                                },
//                                {
//                                        Log.e("volley error",it.message.toString())
//                                }
//
//
//                        )

//                        val stringRequest = object : StringRequest(Request.Method.POST, url,
//                                Response.Listener {
//                                        // blm selese
//                                        Log.d("cekparams", it)
//                                                  },
//                                Response.ErrorListener {
//                                        Log.d("cekparams", it.message.toString())
//                                }
//
//
//                        )
//
//
//
//
//
//                        {
//                                // anonymous object body
//
//                                override fun getParams(): MutableMap<String, String>? {
//                                        return super.getParams()
//                                        /// blm selese
//                                }
//
//                        }

            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener {
                    Log.d("checkparams", it)
                    //val obj = JSONObject(it)
                    val obj = it
                    if (obj=="OK"){
                        playlist.num_likes++ // i changed num_likes to var
                        holder.v.txtLikes.text = "${playlist.num_likes} LIKES"

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
                    return hashMapOf("memes_id" to playlist.id.toString(),
                        ("user_id" to usr_id.toString()) // passing usr_id (5)
                    )
                }
            }

            q.add(stringRequest)



        }
        holder.v.btnComments.setOnClickListener {
            val intent = Intent(holder.v.getContext(), CommentActivity::class.java)
            intent.putExtra("user_id",usr_id.toString())
            intent.putExtra("memes_id",mem_id.toString())
            intent.putExtra("img_url",url.toString())
            intent.putExtra("likes_",homes[position].num_likes.toString())
            intent.putExtra("top_text",topTxt)
            intent.putExtra("bot_text",botTxt)

            holder.v.getContext().startActivity(intent)

        }



    }

    override fun getItemCount(): Int {
        return homes.size
    }

}

