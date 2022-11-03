package com.ashram7.mapsmylocation

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ashram7.mapsmylocation.databinding.ActivityMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    // 2개의 권한에 대한 승인이기 때문에 제네릭은 문자열 배열인 <Array<String>>로...
    lateinit var locationPermission: ActivityResultLauncher<Array<String>>
    //val binding by lazy { ActivityMapsBinding.inflate(layoutInflater) }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    // OnMapReady() 위치를 처리하기 위한 변수 2개를 선언해 둔다.
    // FusedLocationClient는 위치값을 사용하기 위해서 필요
    // LocationCallback은 위칫값 요청에 대한 갱신 정보를 받는데 필요함
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)*/

        // 런쳐를 생성하는 코드를 작성하고 앞에서 선언해 둔 변수에 저장한다.
        locationPermission = registerForActivityResult(
            // 한 번에 2개의 권한에 대한 승인을 요청하기 때문에
            // Contract로 RequestMultiplePermissions()를 사용해야 한다.
            ActivityResultContracts.RequestMultiplePermissions()) { results ->
            if(results.all{ it.value }) {
                startProcess()
            } else {
                Toast.makeText(this
                    , "권한 승인이 필요합니다."
                    , Toast.LENGTH_LONG).show()
            }
        }

        // 런처를 실행해서 권한 승인을 요청한다. 2개의 권한을 파라미터에 전달해야 되기 때문에
        // arrayOf()를 사용해서 권한 2개를 같이 launch()의 파라미터로 입력한다.
        locationPermission.launch(
            arrayOf(
                // Manifest : Android 용으로 import
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION )
        )

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // 바로 아랫줄의 updateLocation() 메서드를 호출
        updateLocation()
    }

    @SuppressLint("MissingPermission")
    fun updateLocation() {
        // LocationRequest : google 용도로 import
        val locationRequest = LocationRequest.create()
        // 위치 정보를 요청할 정확도와 주기를 설정할 locationRequest 생성
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
        }

        // 해당 주기마다 반환받을 locationCallback를 생성
        locationCallback = object : LocationCallback() {
            // onLocationResult()로 1초에 한 번씩 변화된 위치 정보가 전달된다.
            override fun onLocationResult(locationResult: LocationResult?) {
                // onLocationResult()는 반환받은 정보에서 위치 정보를 setLastLocation()으로 전달
                locationResult?.let {
                    for(location in it.locations) {
                        Log.d("Location", "${location.latitude} , ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }
        // 현재 위치를 검색하기 위해서 FusedLocationProviderClient를 생성해서 사용한다.
        // requestLocationUpdates()에 앞에서 생성한 2개와 함께 루퍼(스레드와 비슷) 정보를 넘겨준다.
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,
            Looper.myLooper())
        // 위 코드는 권한 처리가 필요한데 현재 코드에서는 확인할 수 없다.
        // 따라서 메서드 상단에 해당 코드를 체크하지 않아도 된다는 의미로
        // @SuppressLint("MissingPermission") 애너테이션을 달아 준다.
        // 어노테이션은 사전적 의미로는 주석이라는 뜻을 가지고 있으며 컴퓨터 언어에선 컴파일러에게
        // 정보를 제공하거나, 실행 시 특정 코드를 자동으로 생성해주도록 도와주는 역할을 하고 있습니다.
    }

    // 승인 후 실행 할 코드를 입력한다.
    fun startProcess() {
        // startProcess() 메서드에서 구글 지도를 준비하는 작업을 진행하도록 코드를 입력
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // 위치 정보를 받아서 마커를 그리고 화면을 이동하는 setLastLocation()
    fun setLastLocation(lastLocation: Location) {
        // 전달받은 위치 정보로 좌표를 생성하고 해당 좌표로 마커를 생성한다.
        val LATLNG = LatLng(lastLocation.latitude, lastLocation.longitude)

        val markerOptions = MarkerOptions()
            .position(LATLNG)
            .title("Here!")

        // 카메라 위치를 현재 위치로 세팅하고 마커와 함께 지도에 반영한다.
        // 마커를 지도에 반영하기 전에 mMap.clear()를 호출해서 이전에 그려진 마커가 있으면 지운다.
        val cameraPosition = CameraPosition.Builder()
            .target(LATLNG)
            .zoom(15.0f)
            .build()

        mMap.clear()
        mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}