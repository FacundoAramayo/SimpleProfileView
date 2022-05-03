package eth.facundoaramayo.simpleprofileview.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eth.facundoaramayo.simpleprofileview.R
import eth.facundoaramayo.simpleprofileview.domain.model.UserModel
import eth.facundoaramayo.simpleprofileview.utils.ext.setImage

class UserAdapter(
    private val data: List<UserModel>,
    private val listener: OnUserItemClickListener
): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvName: TextView
        val imgAvatar: ImageView
        val btnEdit: ImageButton
        val btnRemove: ImageButton
        init {
            tvName = view.findViewById(R.id.item_name)
            imgAvatar = view.findViewById(R.id.item_avatar)
            btnEdit = view.findViewById(R.id.btn_edit)
            btnRemove = view.findViewById(R.id.btn_remove)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            itemView.setOnClickListener {
                listener.onItemClicked(data[position])
            }
            tvName.text = data[position].name
            imgAvatar.setImage(data[position].avatar)
            btnEdit.setOnClickListener {
                listener.onEditClicked(data[position])
            }
            btnRemove.setOnClickListener {
                listener.onRemoveClicked(data[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
