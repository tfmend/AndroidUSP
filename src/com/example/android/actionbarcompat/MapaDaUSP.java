package com.example.android.actionbarcompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;


public class MapaDaUSP extends ActionBarActivity {

	private MapView mapView;
	private LocationManager locationManager;
	private MyLocationOverlay myLocationOverlay;
	private MapController mapController;
	private MyOverlays itemizedoverlay1;
	private MyOverlays itemizedoverlay2;
	/*private MyOverlays itemizedoverlay3;
	private MyOverlays itemizedoverlay4;
	private MyOverlays itemizedoverlay5;
	private MyOverlays itemizedoverlay6;*/

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		 
		mapView = (MapView) findViewById(R.id.mapView); // Get mapView
		mapView.setBuiltInZoomControls(true); // Set to appears zoom controls
		mapView.setSatellite(false);
		
		mapController = mapView.getController(); // Get map controller
		mapController.setZoom(18); // Set initial zoom
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // Get location manager
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GeoUpdateHandler()); // Set the lisneter

		myLocationOverlay = new MyLocationOverlay(this, mapView); // 
		mapView.getOverlays().add(myLocationOverlay); // add overlay to my location

		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				mapView.getController().animateTo(
						myLocationOverlay.getMyLocation());
			}
		});

		itemizedoverlay1 = new MyOverlays(this, this.getResources().getDrawable(R.drawable.buildings));
		itemizedoverlay2 = new MyOverlays(this, this.getResources().getDrawable(R.drawable.sports));
		
		createMarker();
		
	}
	
	public class GeoUpdateHandler implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
	
	public void createMarker() {
		//! add institutes overlays
		setTypedOverlays(1); 
		if (itemizedoverlay1.size() > 0) {
			mapView.getOverlays().add(itemizedoverlay1);
		}
		
		setTypedOverlays(2);
		if(itemizedoverlay2.size() > 0) {
			mapView.getOverlays().add(itemizedoverlay2);
		}
		
	}


	private void setTypedOverlays(int type) {
		if(type < 1 || type > 6)
			return;
		
		try {
			InputStream is = getAssets().open("geopoints.csv");	
			BufferedReader bis = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while((line = bis.readLine()) != null) {				
				if(line.contains(";")) {
					String [] fields = line.split(";");
					if(fields.length >= 6) {
						//String id = fields[0];
						int t = Integer.parseInt(fields[1]);
						String description = fields[2];
						String abbr = fields[3];
						float lat = Float.parseFloat(fields[4]);
						float lon = Float.parseFloat(fields[5]);
					
						if(t == type) {
							GeoPoint p = new GeoPoint( (int) (lat * 1E6), (int) (lon * 1E6) );
							addOverlay(description, abbr, p, type);
						}
					}
				}
			}

			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	private void addOverlay(String description, String abbr, GeoPoint p, int type) {
		OverlayItem overlay;

		if(abbr.isEmpty()) {
			overlay = new OverlayItem(p, description, "");
		}
		else
			overlay = new OverlayItem(p, abbr, description);
		
		switch (type) {
			case 1:
				itemizedoverlay1.addOverlay(overlay);
				break;
			
			case 2:
				itemizedoverlay2.addOverlay(overlay);
				break;

			default:
				break;
		}
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_filter:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		myLocationOverlay.enableCompass(); 
		myLocationOverlay.enableMyLocation(); 
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		myLocationOverlay.disableCompass(); 
		myLocationOverlay.disableMyLocation(); 
	}
}
