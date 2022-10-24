package com.ashram7.viewpagerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashram7.viewpagerview.databinding.ItemViewpagerBinding

class CustomPagerAdapter: RecyclerView.Adapter<Holder>() {
    var textList = listOf<String>()
    //onCreateViewHolder()메서 바인딩을 생성한 후 Holder에 전달
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemViewpagerBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false)
        return  Holder(binding)
    }
    //onBindViewHolder()에서 Holder에 만들어둔 setText 메서드를 호출해서 화면에 출력
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val text = textList[position]
        holder.setText(text)
    }
    //getItemCount 메서드는 몇 개의 페이지가 보일 건지 결정한다.
    override fun getItemCount(): Int {
        return textList.size
    }
}

class Holder(val binding: ItemViewpagerBinding): RecyclerView.ViewHolder(binding.root){
    fun setText(text:String) {
        binding.textView.text = text
    }
}