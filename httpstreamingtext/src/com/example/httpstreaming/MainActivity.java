package com.example.httpstreaming;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		
		try {
			FileReader fr = new FileReader("/storage/sda1/list.txt");
			BufferedReader br =new BufferedReader(fr);
			String readData="";
			String temp=br.readLine();
			
			
			while (temp!=null){
				readData+=temp;
				temp=br.readLine();		
				
			}
			
		
			
			Context context =getApplicationContext();
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, readData,duration);
			toast.show();
			
			Intent intent =new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.parse(readData), "video/mp4");
			startActivity(intent);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
		
		
		}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
