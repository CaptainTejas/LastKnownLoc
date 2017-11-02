package sample.shinde.tejas.com.lastknownloc;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private String TAG = "TAG";

    private Activity activity = MainActivity.this;

    final String gpsLocationProvider = LocationManager.GPS_PROVIDER;
    final String nwLocationProvider = LocationManager.NETWORK_PROVIDER;

    String wantpermission = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
        if (checkPerm(wantpermission)) {
            Location lastknowngps = locationManager.getLastKnownLocation(gpsLocationProvider);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location lastknownnw = locationManager.getLastKnownLocation(nwLocationProvider);

            if(lastknowngps == null){
                Log.d(TAG,"no gps");
                if (lastknownnw == null){
                    Log.d(TAG,"No Network");
                    Log.d(TAG,"No Location");

                }else{
                    Log.d(TAG,"Network"+lastknownnw.toString());
                    Log.d(TAG,"Location (Network)"+lastknownnw.getLatitude()+","+lastknownnw.getLongitude());

                }
            }else{
                Log.d(TAG,lastknowngps.toString());

                if(lastknownnw == null){
                    Log.d(TAG,"No Network");
                    Log.d(TAG,"Location(GPS)"+lastknowngps.getLatitude()+" , "+lastknowngps.getLongitude());
                    Log.d(TAG,"TIME(GPS)"+lastknowngps.getTime());
                    Log.d(TAG,"Accuracy(GPS)"+lastknowngps.getAccuracy());
                }else{
                    Log.d(TAG,"Network"+lastknownnw.toString());

                    if(lastknowngps.getAccuracy() <= lastknownnw.getAccuracy()){
                        Log.d(TAG,"Location (GPS)" +lastknowngps.getLatitude()+" , "+lastknowngps.getLongitude());

                    }else{
                        Log.d(TAG,"Location (Network)"+lastknownnw.getLatitude()+","+lastknownnw.getLongitude());
                    }
                }
            }


        }


    }

    private boolean checkPerm(String permissions){
        if (Build.VERSION.SDK_INT >= 23){
            int result = ContextCompat.checkSelfPermission(activity,permissions);

            if (result == PackageManager.PERMISSION_GRANTED){
                return  true;
            }else {
                return  false;
            }
        }else { return true;}
    }
}
