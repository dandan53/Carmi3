package com.example.carmi3;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class DrawingActivity extends Activity implements View.OnClickListener {

	DrawingView drawingView;
	ImageButton btnShare;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.drawing);
		
		init();
	}

	Bundle extras;
	
	private void init() 
	{
		
		btnShare = (ImageButton) findViewById(R.id.btnShare);
		btnShare.setOnClickListener(this);
		
		drawingView = (DrawingView) findViewById(R.id.drawingView);
	}
	
	@Override
	public void onResume() {
	    super.onResume();

	   setPic();
	}
	
	@SuppressLint("NewApi")
	private void setPic()
	{
		extras = getIntent().getExtras();
		if (extras != null)
		{
			Bitmap bmp = (Bitmap) extras.get("bmp");
			if (bmp == null)
			{
				String selectedPath = extras.getString("path");
				Uri selectedImageUri = (Uri) extras.get("uri");
				bmp = BitmapFactory.decodeFile(selectedPath);
				
				//ImageView img = (ImageView)findViewById(R.id.imageView);
			   // img.setImageURI(selectedImageUri);
				
				Drawable drawable = new BitmapDrawable(getResources(),bmp);
				drawingView.setBackground(drawable);
			}
			else
			{
				Drawable drawable = new BitmapDrawable(getResources(),bmp);
				drawingView.setBackground(drawable);
			}
		}	
	}
		
	@Override
	public void onClick(View v) 
	{	
		switch(v.getId()){
        case R.id.btnShare:
        	savePic();
        	//sharePic();
        	break;
		}
	}
	
	private void savePic()
	{
		  Bitmap bmMyView = drawingView.getCanvasBitmap();
		  
		  Uri uri = getImageUri(this, bmMyView);
		  sharePic(uri);
	 }
	 
	 public Uri getImageUri(Context inContext, Bitmap inImage)
	 {
		  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		  inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		  String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
		  return Uri.parse(path);
	 }
	 
 	 private void sharePic(Uri uri)
 	 {
		 //Uri uri = Uri.parse("android.resource://" + "com.example.carmi1" + "/" + R.drawable.puppy1);
		 Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
	     whatsappIntent.setType("image/*");
	
	     whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Your text here");
	     whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);               
	     whatsappIntent.setPackage("com.whatsapp");        
	    
	    
	     PackageManager pm = getApplicationContext().getPackageManager();
   	     final List<ResolveInfo> matches = pm.queryIntentActivities(whatsappIntent, 0);
   	     boolean temWhatsApp = false;
   	     for (final ResolveInfo info : matches)
   	     {
   	    	 if (info.activityInfo.packageName.startsWith("com.whatsapp"))  
   	    	 {
	   	          final ComponentName name = new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);
	   	          temWhatsApp = true;
	   	          break;
   	    	 }
   	    }               
   	    if(temWhatsApp) 
   	    {
   	        //abre whatsapp
   	    	 startActivity(Intent.createChooser(whatsappIntent, "Your Awesome Text and Pic..."));
   	    } else {
   	        //alerta - você deve ter o whatsapp instalado
   	        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
   	    }
	 }
}