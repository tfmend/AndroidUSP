package br.usp.ime.mapa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import br.ime.usp.R;

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
	private LocationOverlay itemizedoverlay1;
	private LocationOverlay itemizedoverlay2;
	private LocationOverlay itemizedoverlay3;
	private LocationOverlay itemizedoverlay4;
	private LocationOverlay itemizedoverlay5;
	private LocationOverlay itemizedoverlay6; 

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.setTitle("Mapa da USP");

		mapView = (MapView) findViewById(R.id.mapView); // Get mapView
		mapView.setBuiltInZoomControls(true); // Set to appears zoom controls
		mapView.setSatellite(false);

		mapController = mapView.getController(); // Get map controller
		mapController.setZoom(18); // Set initial zoom

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // Get location manager
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationUpdateHandler()); // Set the lisneter

		myLocationOverlay = new MyLocationOverlay(this, mapView); // 
		mapView.getOverlays().add(myLocationOverlay); // add overlay to my location

		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				mapView.getController().animateTo(
						myLocationOverlay.getMyLocation());
			}
		});

		itemizedoverlay1 = new LocationOverlay(this, this.getResources().getDrawable(R.drawable.buildings));
		itemizedoverlay2 = new LocationOverlay(this, this.getResources().getDrawable(R.drawable.sports));
		itemizedoverlay3 = new LocationOverlay(this, this.getResources().getDrawable(R.drawable.foundation));
		itemizedoverlay4 = new LocationOverlay(this, this.getResources().getDrawable(R.drawable.tree));
		itemizedoverlay5 = new LocationOverlay(this, this.getResources().getDrawable(R.drawable.peoples));
		itemizedoverlay6 = new LocationOverlay(this, this.getResources().getDrawable(R.drawable.restaurants));

		createMarker();

		drawBusRoute(getGeoPoints("8022.txt"), 8022);
		drawBusRoute(getGeoPoints("8012.txt"), 8012);
	}

	private void drawBusRoute(String[] geoPoints, int color) {
		for(int i = 0; i < geoPoints.length; i++) {
			drawPath(color, mapView, geoPoints[i]);
		}
	}

	private String [] getGeoPoints(String string) {

		List<String> stringList = new ArrayList<String>();

		try {
			InputStream is = getAssets().open(string);	
			BufferedReader bis = new BufferedReader(new InputStreamReader(is));
			String line = null;

			while((line = bis.readLine()) != null) {				
				stringList.add(line);
			}

			int n = stringList.size();

			String [] ret = new String[n];

			for(int i = 0; i < n; i++)
				ret[i] = stringList.get(i);

			return ret;


		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void drawPath(int color, MapView mMapView01, String nextPath) {
		String[] pairs = nextPath.split(" ");

		System.out.println(nextPath);				

		for (int i = 0; i < pairs.length-1; i++) {
			String [] sourcePoint = pairs[i].split(",");
			String [] sinkPoint   = pairs[i+1].split(",");

			System.out.println();

			mMapView01.getOverlays().add(new BusRouteOverlay(
					new GeoPoint(
							(int) (Double.parseDouble(sourcePoint[1]) * 1E6),
							(int) (Double.parseDouble(sourcePoint[0]) * 1E6)), 
							new GeoPoint(
									(int) (Double.parseDouble(sinkPoint[1]) * 1E6),
									(int) (Double.parseDouble(sinkPoint[0]) * 1E6)), 
									color));

		}

	}

	public void createMarker() {
		setTypedOverlays(1); 
		if (itemizedoverlay1.size() > 0) {
			mapView.getOverlays().add(itemizedoverlay1);
		}

		setTypedOverlays(2);
		if(itemizedoverlay2.size() > 0) {
			mapView.getOverlays().add(itemizedoverlay2);
		}

		setTypedOverlays(3);
		if(itemizedoverlay3.size() > 0) {
			mapView.getOverlays().add(itemizedoverlay3);
		}

		setTypedOverlays(4);
		if(itemizedoverlay4.size() > 0) {
			mapView.getOverlays().add(itemizedoverlay4);
		}

		setTypedOverlays(5);
		if(itemizedoverlay5.size() > 0) {
			mapView.getOverlays().add(itemizedoverlay5);
		}
		
		setTypedOverlays(6);
		if(itemizedoverlay6.size() > 0) {
			mapView.getOverlays().add(itemizedoverlay6);
		}
	}

	public class LocationUpdateHandler implements LocationListener {

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
					if(fields.length >= 5) {
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

		case 3:
			itemizedoverlay3.addOverlay(overlay);
			break;

		case 4:
			itemizedoverlay4.addOverlay(overlay);
			break;

		case 5:
			itemizedoverlay5.addOverlay(overlay);
			break;
			
		case 6:
			itemizedoverlay6.addOverlay(overlay);
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


	private void disableGPS() {
		myLocationOverlay.disableCompass(); 
		myLocationOverlay.disableMyLocation();
	}

	private void enableGPS() {
		myLocationOverlay.enableCompass(); 
		myLocationOverlay.enableMyLocation();
	}

	@Override
	protected void onResume() {
		super.onResume();
		enableGPS();
	}

	@Override
	protected void onPause() {
		super.onPause();
		disableGPS(); 
	}

	@Override
	protected void onDestroy () {
		super.onDestroy ();
		disableGPS();
	}

	@Override
	protected void onRestart () {
		super.onRestart ();
		enableGPS();
	}

	@Override
	protected void onStart () {
		super.onStart ();
		enableGPS();
	}

	@Override
	protected void onStop () {
		super.onStop ();
		disableGPS();
	}

	@Override
	public void onBackPressed() {
		disableGPS();
		super.onBackPressed();
	}
}
