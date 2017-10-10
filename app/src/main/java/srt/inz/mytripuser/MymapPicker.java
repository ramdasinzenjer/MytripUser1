package srt.inz.mytripuser;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

@SuppressLint("NewApi") public class MymapPicker extends FragmentActivity implements OnMapReadyCallback{

	GoogleMap googleMap; String addressText;
	double lat; double lon;  String sdest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mappicker);

		SupportMapFragment supportMapFragment = (SupportMapFragment)
				getSupportFragmentManager().findFragmentById(R.id.m_map);
		// Getting a reference to the map
		supportMapFragment.getMapAsync(this);
		googleMap.setMyLocationEnabled(true);
		
		googleMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				
				lat=arg0.latitude; lon= arg0.longitude;
				
				new GeocoderTask().execute("");
				Toast.makeText(getApplicationContext(), ""+lat+lon, Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public void onMapReady(GoogleMap googleMap) {

	}

	private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{

		@Override
		protected List<Address> doInBackground(String... locationName) {
			
			// Creating an instance of Geocoder class
			Geocoder geocoder = new Geocoder(getBaseContext());
			List<Address> addresses = null;
			
			try {
				// Getting a maximum of 3 Address that matches the input text
				addresses = geocoder.getFromLocation(lat,lon, 3);
			} catch (IOException e) {
				e.printStackTrace();
			}			
			return addresses;
		}
				
		@Override
		protected void onPostExecute(List<Address> addresses) {			
	       
	        
	        // Clears all the existing markers on the map
	        googleMap.clear();
			
	        // Adding Markers on Google Map for each matching address
			for(int i=0;i<addresses.size();i++){				
				
				Address address = (Address) addresses.get(i);
					       		        
		         addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());
		        
			}	
			
			openDialog(addressText);
		}		
	}
	
	public void openDialog(String st){
	      
		sdest=st;
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	      
	     		  
	    	 alertDialogBuilder.setTitle("Please choose an action!");
		      alertDialogBuilder.setMessage("Would you like to continue with the selected loation :\n"+st);
	    
		 alertDialogBuilder.setPositiveButton("Submit Destination", new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface arg0, int arg1) {
	            
	        	 
	        	 SharedPreferences sh=getSharedPreferences("mydest", MODE_APPEND);
	        	 SharedPreferences.Editor ed =sh.edit();
	        	 ed.putString("destkey", sdest);	        	 
	        	 ed.commit();
	        	 
	        	 finish();
	        	 Intent i=new Intent(getApplicationContext(),RequestTransport.class);
	        	 startActivity(i);
	            
	         }
	      });
	      
	      alertDialogBuilder.setNegativeButton("Back",new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	        	
	        //	 Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_SHORT).show();
	        
	        	 //finish();
	         }
	      });
	      
	    
	     
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
  	 
	}  

}
