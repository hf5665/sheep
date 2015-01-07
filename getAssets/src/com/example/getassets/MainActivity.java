package com.example.getassets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText firstField;
	private EditText secondField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AssetManager assetManager = getAssets();
		String[] files = null;
		//get png file name
		try{
			files = assetManager.list("image");
		} catch(IOException e){
			Log.e("tag",e.getMessage());
		}
		firstField =(EditText)findViewById(R.id.editText1);
		firstField.setText(Integer.toString(files.length)+ "file.file name is "+ files[0]);
		InputStream inputStream = null;
		
		//get text content 
		try{
			inputStream=assetManager.open("NetCom_0.5m.txt");
		}catch(IOException e){
			Log.e("tag",e.getMessage());
		}
		String s = readTextFile(inputStream);
		secondField=(EditText)findViewById(R.id.editText2);
		secondField.setText(s);
		
	}
	

	/**
	 * This method reads simple text file
	 * @param inputStream
	 * @return data from file
	 */
	private String readTextFile(InputStream inputStream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
		}
		return outputStream.toString();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}


