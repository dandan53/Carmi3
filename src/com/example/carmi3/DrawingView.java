package com.example.carmi3;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

	boolean touching = false;
	
	ArrayList<Point> Points = new ArrayList<Point>();
	
	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	float bitmapW = 0;
	float bitmapH = 0;	
	
	Bitmap BGbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	
	public DrawingView(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	    this.setDrawingCacheEnabled(true);
	    Init();
	}

	public DrawingView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    this.setDrawingCacheEnabled(true);
	    Init();
	}
	
	public DrawingView(Context context) {
	    super(context);
	    this.setDrawingCacheEnabled(true);
	    Init();
	}
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void Init()	{
	   
		//1. setBackgroundResource(R.drawable.puppy1);
	    
		//2. ob = new BitmapDrawable(BGbitmap);
	   //    setBackgroundDrawable(ob);
	     
	     Drawable drawable = new BitmapDrawable(getResources(),BGbitmap);
	     setBackground(drawable);
	     
	    
	    bitmapW = bitmap.getWidth() / 2;
	    bitmapH = bitmap.getHeight() / 2;
	}
		
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
	    super.onDraw(canvas);    //To change body of overridden methods use File | Settings | File Templates.
	
	    
	    if (touching) {
	       
	    	if (Points != null){
	    		for (Point p : Points) {
	    	        canvas.drawBitmap(bitmap, p.x, p.y, null);
	    		}
	    	}
	    }
	    
	  //canvas.setBitmap(bitmap);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	
		float x = event.getX();
		float y = event.getY();
		
		x -= bitmapW;
		y -= bitmapH;
		
		Point point = new Point((int) x, (int) y);
		Points.add(point);
				
	    switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	            touching = true;
	            invalidate();
	            break;
	        case MotionEvent.ACTION_CANCEL:
	        case MotionEvent.ACTION_UP:
	            touching = false;
	            invalidate();
	            break;
	    }
	    return super.onTouchEvent(event);    //To change body of overridden methods use File | Settings | File Templates.
	}
	
	public class Point
	{
		public Point(int x, int y) {
		    this.x = x;
		    this.y = y;
		}
		
		public int x =  0;
		public int y =  0;
	}
	
	Bitmap myCanvasBitmap = null;
	 Canvas myCanvas = null;
	 Matrix identityMatrix;

	 /*
	 @Override
	 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	 
	  int w = MeasureSpec.getSize(widthMeasureSpec);
	  int h = MeasureSpec.getSize(heightMeasureSpec);
	 
	  myCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
	  myCanvas = new Canvas();
	  myCanvas.setBitmap(myCanvasBitmap);
	   
	  identityMatrix = new Matrix();
	   
	  setMeasuredDimension(w, h);
	  
	 }
	 */
	 
	 //////// API ////////
	 
	 public Bitmap getCanvasBitmap(){
	  
		   return this.getDrawingCache();
	   
	 }
	 
	 public void ResetPoints(){
		  
		   Points = new ArrayList<Point>();
		   
		   invalidate();
	 }
	 
}