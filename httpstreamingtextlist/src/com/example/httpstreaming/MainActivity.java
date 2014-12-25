package com.example.httpstreaming;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.videoname,android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		ListView listview=getListView();
		listview.setOnItemClickListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private AdapterView.OnItemClickListener listener=new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String s=(String) ((TextView)view).getText();
			//Toast.makeText(parent.getContext(),((TextView)view).getText(), Toast.LENGTH_LONG).show();
			Toast.makeText(parent.getContext(),s, Toast.LENGTH_LONG).show();
			Intent intent =new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.parse(s), "video/mp4");
			startActivity(intent);
		}
	};

}
	