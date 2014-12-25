package com.example.button;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button onebtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		onebtn=(Button) findViewById(R.id.button1);
		onebtn.setOnClickListener(onebtnclick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private Button.OnClickListener onebtnclick=new Button.OnClickListener(){
		public void onClick(View v){
			
			Toast.makeText(getApplicationContext(), "button onclick", Toast.LENGTH_LONG).show();
		}
	};

}
