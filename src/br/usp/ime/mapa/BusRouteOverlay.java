package br.usp.ime.mapa;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class BusRouteOverlay extends Overlay {

	private GeoPoint gp1;
	private GeoPoint gp2;
	private int defaultColor;

	public BusRouteOverlay(GeoPoint gp1,GeoPoint gp2, int defaultColor) { 
		this.gp1 = gp1; 
		this.gp2 = gp2;
		this.defaultColor  = defaultColor;
	} 

	
	@Override 
	public void draw (Canvas canvas, MapView mapView, boolean shadow) { 
		Projection projection = mapView.getProjection(); 

		Paint paint = new Paint(); 
		paint.setAntiAlias(true); 

		Point point = new Point(); 
		projection.toPixels(gp1, point);

		paint.setColor(defaultColor);   

		Point point2 = new Point(); 
		projection.toPixels(gp2, point2);
		paint.setStrokeWidth(5);
		paint.setAlpha(120);       
		canvas.drawLine(point.x, point.y, point2.x,point2.y, paint);       

		super.draw(canvas, mapView, false); 
	}
}
