package id.ac.ubaya.informatika.dailymemedigest




import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.card_leader.view.*

// file untuk masing2 card mungkin ? yang di hold

class LeaderAdapter(val users: ArrayList<Users>) : // homes dari array homeFragment




    RecyclerView.Adapter<LeaderAdapter.HomeViewHolder>() {

    class HomeViewHolder(val v: View):RecyclerView.ViewHolder(v)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.card_leader, parent,false)
        return HomeViewHolder(v)





    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        var stared_name = ""
        val first_name = users[position].first_name
        val last_name = users[position].last_name
        val img_url = users[position].img_prof
        val priv_set = users[position].priv_set
        // name hider
        if (priv_set == 0){
            for (i in 0..(first_name.length-1)) {
                if (i<=2){
                    stared_name = stared_name + first_name[i]
                }
                else{
                    stared_name = stared_name + "*"

                }


            }

            stared_name = stared_name + " "

            for (i in 0..(last_name.length-1)) {
                stared_name = stared_name + "*"



            }

            holder.v.txtNameLeader.text = stared_name.toString()


        }

        else{
            holder.v.txtNameLeader.text = first_name.toString() + " " + last_name.toString()
        }
        ///
        val tot_likes = users[position].total_lkes
        Picasso.get().load(img_url).into(holder.v.imgLeaderProf)
        holder.v.txtLikesLeader.text = tot_likes.toString()+ " LIKES"


    }

    override fun getItemCount(): Int {
        return users.size
    }

}

