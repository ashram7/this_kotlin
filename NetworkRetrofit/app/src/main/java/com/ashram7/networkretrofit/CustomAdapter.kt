package com.ashram7.networkretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashram7.networkretrofit.databinding.ItemRecyclerBinding
import com.bumptech.glide.Glide

class CustomAdapter : RecyclerView.Adapter<Holder>() {
    /* 어뎁터에서 사용할 데이터 컬렉션을 변수로 만들어 놓는다.
    우리가 사용할 데이터셋은 앞에서 자동으로 생성해두었던 Repository이다. nullable로 선언한다. */
    var userList: Repository? = null

    // 홀더를 생성하는 onCreateViewHolder를 구현한다. 레이아웃을 인플레이트한 후 바인딩을 달아서 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    // 목록에 출력되는 총 아이템 개수를 정하는 getItemCount()
    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    /* 실제 목록에 뿌려지는 아이템을 그려주는 onBindViewHolder()를 구현한다. 현 위치의 사용자
    데이터를 userList에서 가져오고 아직 만들어지지 않은 홀더의 setUser() 메서드에 넘김 */
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = userList?.get(position)
        holder.setUser(user)
    }
}

// 홀더의 생성자에서 바인딩을 전달받고 상속받은 ViewHolder에는 binding.root를 전달한다.
class Holder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){
    /* setUser() 메서드는 1개의 RepositoryItem을 파라미터로 사용한다. 클래스 가장 윗줄에서
    userList가 nullable이기 때문에 user 파라미터도 nullable로 설정되어야 한다.*/
    fun setUser(user:RepositoryItem?) {
        user?.let {
            // 먼저 사용자의 이름과 아이디를 셋팅하고 아바타는 Glide를 사용하여 이미지뷰에 세팅한다.
            binding.textName.text = it.name
            binding.textId.text = it.node_id
            Glide.with(binding.imageAvatar).load(it.owner.avatar_url).into(binding.imageAvatar)
        }
    }
}