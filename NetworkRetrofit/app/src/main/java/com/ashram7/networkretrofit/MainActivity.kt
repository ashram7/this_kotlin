package com.ashram7.networkretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashram7.networkretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /* 이제 레트로핏을 사용할 준비가 되었으니 데이터를 요청할 차례이다.
        recyclerView의 adapter에 앞에서 만들었던 CustomAdapter를 생성하고 recyclerView에 연결 */
        val adapter = CustomAdapter()
        binding.recyclerView.adapter = adapter
        // 이어서 리니어 레이아웃 매니저도 연결한다.
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        /* Retrofit.Bilder()를 사용해서 레트로핏을 생성하고 retrofit 변수에 담는다.
        baseUrl이 되는 Github의 도메인 주소와 JSON 데이터를 앞에서 생성한 Repository 클래스의
        컬렉션으로 변환해주는 컨버터를 입력하고 build() 메서드를 호출해서 생성한다. */
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        /* 이제 요청 버튼을 클릭하면 앞에서 생성해둔 레트로핏을 이용해 데이터를 불러오고
        어댑터에 세팅할 것이다. buttonRequest에 클릭리스너를 연결한다. */
        binding.buttonRequest.setOnClickListener {
            /* 레트로핏의 create() 메서드 앞에서 정의한 인터페이스를 파라미터로 넘겨주면
            실행 가능한 서비스 객체를 생성해서 반환해준다. 더블콜론이란? */
            val githubService = retrofit.create(GithubService::class.java)
            /* githubService에는 GitHubService 인터페이스를 이용해서 객체를 생성했기 때문에
            실행(호출)가능한 상태의 users() 메서드를 가지고 있다.
            레크로핏의 create() 메서드는 인터페이스를 실행 가능한 서비스 객체로 만들면서
            users() 메서드 안에 비동기 통신으로 데이터를 가져오는 enqueue() 메서드를 추가해 놓았다.
            enqueue()가 호출되면 통신이 시작된다. */
            githubService.users().enqueue(object: Callback<Repository> {
                /* enqueue() 메서드를 호출한 후 Github API 서버로부터 응답을 받으면 enqueue() 안에
                작성하는 콜백 인터페이스가 작동하게 된다. enqueue()의 파라미터로 콜백 인터페이스를 구현 */
                override fun onFailure(call: Call<Repository>, t: Throwable) {
                    /* */
                }
                /* 위와 아래 Ctrl + I 키를 눌러서 콜백 인터페이스의 필수 메서드를 구현 */
                /* 통신이 성공적이면 두 번째 메서드인 onResponse() 가 호출된다. */
                override fun onResponse(
                    call: Call<Repository>, response: Response<Repository>
                )
                {
                    /* response의 body() 메서드를 호출하면 서버로부터 전송된 데이터를 꺼낼 수 있다.
                    꺼낸 데이터를 List<Repository>로 형변환한 후에 어댑터의 userList에 담는다. */
                    adapter.userList = response.body() as Repository
                    /* 마지막으로 어댑터의 notifyDataSetChanged를 호출하면
                    리사이클러뷰에 변경된 사항이 반영된다. */
                    adapter.notifyDataSetChanged()
                }
            })
        }
    }
}
interface GithubService {
    @GET("users/Kotlin/repos")
    fun users(): Call<Repository>
}