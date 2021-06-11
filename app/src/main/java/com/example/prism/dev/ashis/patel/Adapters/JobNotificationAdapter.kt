package com.example.prism.dev.ashis.patel.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prism.dev.ashis.patel.Model.JobDetails
import com.example.prism.dev.ashis.patel.R
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class JobNotificationAdapter(val jobPosts:ArrayList<JobDetails>) :
        RecyclerView.Adapter<JobNotificationAdapter.MyView>()
{
    private lateinit var mListner : onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner=listner
    }


    class MyView(view: View,listner: onItemClickListner) : RecyclerView.ViewHolder(view) {
        var companyImage: ImageView
        var textHiringFor : TextView
        var textRole : TextView
        var textQualification : TextView
        var textPublishedDate : TextView
        var textLastDate : TextView
        var salary:TextView

        init {
            companyImage = view.findViewById(R.id.imageMNC)
            textHiringFor= view.findViewById(R.id.txt_hiring_for)
            textRole= view.findViewById(R.id.txt_role)
            textQualification= view.findViewById(R.id.txt_qualification)
            textPublishedDate= view.findViewById(R.id.txt_published_date)
            textLastDate= view.findViewById(R.id.txt_last_date)
            salary=view.findViewById(R.id.txt_salary)

           itemView.setOnClickListener {
               listner.onItemClick(adapterPosition)
           }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.vertical_list_item, parent, false)
        return MyView(itemView,mListner)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val posts = jobPosts[position]
        Picasso.get().load(posts.companyImageUrl).placeholder(R.drawable.loa).into(holder.companyImage)
        holder.textHiringFor.text=posts.hiringFor
        holder.textRole.text=posts.role
        holder.textQualification.text=posts.qualification
        holder.textPublishedDate.text=posts.publishedDate
        holder.textLastDate.text=posts.lastDate
        holder.salary.text=posts.salaryStructure+" LPA"
    }

    override fun getItemCount(): Int {
        return jobPosts.size
    }
}