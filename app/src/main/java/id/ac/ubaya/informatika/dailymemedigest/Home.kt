package id.ac.ubaya.informatika.dailymemedigest

data class Home(val id: Int, val mem_img: String, val top_txt: String,
                val bot_txt: String, val id_usr: Int,
                var num_likes: Int, var comments_count: Int)
