package androidlabs.com.androidbasics;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapTest extends FragmentActivity implements OnMapReadyCallback {
    public final static String EXTRA_LATITUDE = "latitude";
    public final static String EXTRA_LONGINTUDE = "longitude";
    public final static String EXTRA_LOCATION = "location";

    private double latitude;
    private  double longitude;
    private  String location;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_test2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       latitude =getIntent().getExtras().getDouble(EXTRA_LATITUDE);
       longitude=getIntent().getExtras().getDouble(EXTRA_LONGINTUDE);
       location=getIntent().getExtras().getString(EXTRA_LOCATION);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng place = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(place).title(location));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        CameraPosition cameraPosition1 = new CameraPosition.Builder()
                .target(place)
                .tilt(90)
                .zoom(17)
                .build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition1));
    }
}
