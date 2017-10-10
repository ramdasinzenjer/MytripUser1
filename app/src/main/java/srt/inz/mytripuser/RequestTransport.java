package srt.inz.mytripuser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel.MapMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.plus.model.people.Person.PlacesLived;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

@SuppressLint("WorldReadableFiles") public class RequestTransport extends Activity implements LocationListener{
	
	EditText etsrc,etdest; String sun,vhid,src,dest,sdate,resout; Button brq;
	ImageButton mloc,mpickdest;	double latitude,longitude;
	Location location; String sloc,sdst;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.requesttrans_layout);
		etsrc=(EditText)findViewById(R.id.editsrc);
		etdest=(EditText)findViewById(R.id.editdest);
		
		brq=(Button)findViewById(R.id.btnrqst);
		mloc=(ImageButton)findViewById(R.id.img_mlocbt);
		mpickdest=(ImageButton)findViewById(R.id.img_mlocdest);
		
		SharedPreferences share=getSharedPreferences("mKey", MODE_WORLD_READABLE);
		sun=share.getString("keyuid", "");
		
		SharedPreferences share1=getSharedPreferences("mKey2", MODE_WORLD_READABLE);
		vhid=share1.getString("vid", "");
		
		SharedPreferences sh=getSharedPreferences("mydest", MODE_WORLD_READABLE);
		sdst=sh.getString("destkey", "");
		
		if(sdst.equals(""))
		{
			etdest.setHint("Select r enter destination");
		}
		else
		{
			etdest.setText(sdst);
		}
				
		mloc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			getmyloc();
			
			
			}
		});		
		
		mpickdest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),MymapPicker.class);
				startActivity(i);
			}
		});
		
		brq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				src=etsrc.getText().toString();
				dest=etdest.getText().toString();
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Calendar calobj = Calendar.getInstance();
				//System.out.println(df.format(calobj.getTime()));
				sdate=df.format(calobj.getTime());
				
					new RequestApiTask().execute();		
			}
		});
		
	}
	
public class RequestApiTask extends AsyncTask<String,String,String> {
	    
	    @Override
	    protected String doInBackground(String... params) {


	            String urlParameters = null;
	            try {
	                urlParameters = "userid=" + URLEncoder.encode(sun, "UTF-8")+ "&&" 
	                        + "vh_id=" + URLEncoder.encode(vhid, "UTF-8")+ "&&" 
	                        + "source=" + URLEncoder.encode(src, "UTF-8")+ "&&"
	                        + "destination=" + URLEncoder.encode(dest, "UTF-8")+ "&&"
	                        + "datetime=" + URLEncoder.encode(sdate, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }		

	            resout = Connectivity.excutePost(Constants.REQUESTTRANSPORT_URL,
	                    urlParameters);
	            Log.e("You are at", "" + resout);

	       return resout;
	    }

	    @Override
	    protected void onPostExecute(String s) {
	        super.onPostExecute(s);
	        
	        if(resout.contains("success"))
	        {
	        	
	        	Toast.makeText(getApplicationContext(), ""+resout, Toast.LENGTH_SHORT).show();
	        
	        }
	        else
	        {
	        	Toast.makeText(getApplicationContext(), ""+resout, Toast.LENGTH_SHORT).show();
	        }
	        
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();

	    }
	}

@Override
public void onLocationChanged(Location location) {
	// TODO Auto-generated method stub
	
	Geocoder gc= new Geocoder(this, Locale.ENGLISH);
    // Getting latitude of the current location
    latitude =  location.getLatitude();

    // Getting longitude of the current location
    longitude =  location.getLongitude();

try {
List<Address> addresses = gc.getFromLocation(latitude,longitude, 1);

	if(addresses != null) {
		Address returnedAddress = addresses.get(0);
		StringBuilder strReturnedAddress = new StringBuilder("");
		for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) 
		{
			strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
			
		}
	
		sloc=strReturnedAddress.toString();
		
		//String[] splited = stplace.split("\\s+");
		  	
		Toast.makeText( getBaseContext(),sloc,Toast.LENGTH_SHORT).show();
		etsrc.setText(sloc);
	}
	//stores the current address to shared preferene shr.
	
	else{
		Toast.makeText(getBaseContext(),"GPS Disabled",Toast.LENGTH_SHORT).show();
	
	}
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	
	
}

@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
	// TODO Auto-generated method stub
	
}

@Override
public void onProviderEnabled(String provider) {
	// TODO Auto-generated method stub
	
}

@Override
public void onProviderDisabled(String provider) {
	// TODO Auto-generated method stub
	
}


public void getmyloc()
{
	try {
					
		 LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		    // Creating a criteria object to retrieve provider
		    Criteria criteria = new Criteria();

		    // Getting the name of the best provider
		    String provider = locationManager.getBestProvider(criteria, true);

		    // Getting Current Location
		    location = locationManager.getLastKnownLocation(provider);
		    

		    if(location!=null){
		            onLocationChanged(location);
		            
		    }

		    locationManager.requestLocationUpdates(provider, 12000, 0, this);
	} catch (Exception e) {
		// TODO: handle exception
		
		e.printStackTrace();
	}
}

}
