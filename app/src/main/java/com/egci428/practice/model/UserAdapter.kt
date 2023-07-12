package com.egci428.practice.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.egci428.practice.R

class UserAdapter (val mContext: Context, val layoutResId: Int, val userList: List<User>): ArrayAdapter<User>(mContext, layoutResId, userList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflator: LayoutInflater = LayoutInflater.from(mContext)
        val view:View = layoutInflator.inflate(layoutResId, null)
        val usrTextView = view.findViewById<TextView>(R.id.usrTextView)
        val usr = userList[position]
        usrTextView.text = usr.username
        return view
    }
}