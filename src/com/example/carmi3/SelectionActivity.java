package com.example.carmi3;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;


public class SelectionActivity extends Activity implements View.OnClickListener {

	private static final int ACTION_TAKE_PHOTO = 1;
	private static final int ACTION_SELECT_FILE  = 2;
	
	ImageButton btnTakePic;
	ImageButton btnUpload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.selection);	
		init();
	}

	private void init() 
	{
			
		btnTakePic = (ImageButton) findViewById(R.id.btnTakePic);
		btnTakePic.setOnClickListener(this);
		
		btnUpload = (ImageButton) findViewById(R.id.btnUpload);
		btnUpload.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		
		Intent intent;
		
		switch(v.getId()){
      
        case R.id.btnTakePic:

        	intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			
        	startActivityForResult(intent, ACTION_TAKE_PHOTO);
        	        	
            break;
        
        case R.id.btnUpload:

        	intent = new Intent(Intent.ACTION_GET_CONTENT);
 	        intent.setType("image/*");

 	        startActivityForResult(Intent.createChooser(intent,"Select file to upload "), ACTION_SELECT_FILE);
        	 
            break;
		}
	}
		
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		Intent intent;
		
		switch (requestCode) 
		{
			case ACTION_TAKE_PHOTO: 
			{
				if (resultCode == RESULT_OK)
				{
					Bundle extras = data.getExtras();
					Bitmap bmp = (Bitmap) extras.get("data");
					
					intent = new Intent("com.example.carmi3.DrawingActivity");
					intent.putExtra("bmp", bmp);
					
					startActivity(intent);
				}
				
				break;
			}
				
			case ACTION_SELECT_FILE: 
			{
				if (resultCode == RESULT_OK)
				{
					Uri selectedImageUri = data.getData();
					String selectedPath = getPath(selectedImageUri);
		            //bmp = BitmapFactory.decodeFile(selectedPath);
		            
		            intent = new Intent("com.example.carmi3.DrawingActivity");
					intent.putExtra("path", selectedPath);
					intent.putExtra("uri", selectedImageUri);

					startActivity(intent);
				}
				
				break;
			}
		}
	}
	
	public String getPath(Uri uri) 
	{
		String[] projection = { MediaStore.Images.Media.DATA };
		
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		
		cursor.moveToFirst();
		
		return cursor.getString(column_index);
		
	}
	
}
