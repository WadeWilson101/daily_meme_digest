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
import kotlinx.android.synthetic.main.card_comment.view.*

// file untuk masing2 card mungkin ? yang di hold

class CommentAdapter(val comments: ArrayList<Comments>) : // homes dari array homeFragment




    RecyclerView.Adapter<CommentAdapter.HomeViewHolder>() {

    class HomeViewHolder(val v: View):RecyclerView.ViewHolder(v)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.card_comment, parent,false)
        return HomeViewHolder(v)





    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val first_name = comments[position].first_name
        val last_name = comments[position].last_name
        val img_url = comments[position].usr_img
        val com_txt = comments[position].comment_txt
        val date_txt = comments[position].date_

        Picasso.get().load(img_url).into(holder.v.imageCommentUser)
        holder.v.txtCommentNameUser.text = first_name +" "+ last_name
        holder.v.txtCommentUser.text = com_txt
        holder.v.txtCommentDate.text = date_txt


    }

    override fun getItemCount(): Int {
        return comments.size
    }

}

