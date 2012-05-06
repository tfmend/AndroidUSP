package br.usp.ime.mapa;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class BusRouteOverlay extends Overlay {

	private GeoPoint source;
	private GeoPoint sink;
	private int color;

	public BusRouteOverlay(GeoPoint source, GeoPoint sink, int color) { 
		this.source = source; 
		this.sink = sink;
		this.color = color;
	} 

	@Override 
	public void draw (Canvas canvas, MapView mapView, boolean shadow) { 
		
		Projection projection = mapView.getProjection(); 
		
		Paint paint = new Paint(); 
		paint.setAntiAlias(true);
		
		Point sourcePoint = new Point(); 
		projection.toPixels(source, sourcePoint);
				
		if(color == 8012)  
			paint.setARGB(127, 255, 0, 0);
		else if(color == 8022)
			paint.setARGB(127, 0, 0, 255);
		
		Point sinkPoint = new Point(); 
		projection.toPixels(sink, sinkPoint);
		
		paint.setStrokeWidth(3);
		paint.setAlpha(120);       
		canvas.drawLine(sourcePoint.x, sourcePoint.y, sinkPoint.x,sinkPoint.y, paint);       

		super.draw(canvas, mapView, true); 
	}
}
