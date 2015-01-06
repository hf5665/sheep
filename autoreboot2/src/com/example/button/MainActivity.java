package com.example.button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.w3c.dom.Text;

import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button onebtn;
	private TextView text;
	Process process;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		onebtn=(Button) findViewById(R.id.button1);
		onebtn.setOnClickListener(onebtnclick);
		text = (TextView) findViewById(R.id.textView1);  
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private Button.OnClickListener onebtnclick=new Button.OnClickListener(){
		public void onClick(View v){
		
			Toast.makeText(getApplicationContext(), "do_exec(...)", Toast.LENGTH_LONG).show();
			//the command must be execute by user,if only for root,do_exec() can't run successful.		   
		
	
//  2015/01/05	
			
			try{
				Process process =Runtime.getRuntime().exec("su");
				Runtime rt=Runtime.getRuntime();
				 String[]cmd= {"./storage/sda1/startapk.sh"};//ok
				 //String[]cmd= {"./storage/sda1/reboot.sh"};//
				 //String[]cmd= {"top"};//ok			 		 
				 //String[]cmd= {"logcat"};//ok
				 Process pr=rt.exec(cmd);				 
				 BufferedReader input = new BufferedReader( new InputStreamReader(pr.getInputStream()));
				 String line = null;
				 while ((line = input.readLine()) != null) {  
		                System.out.println(line);                
		            
			 }
			 } catch (IOException e) {  
		            // TODO Auto-generated catch block  
		            e.printStackTrace();  
		         
		        
		}

}
};
}
		        

	
			
			
			
			
			

//2014/12/31			
 /*
			do_exec("netcfg");  //ok
			//do_exec("cat /proc/version"); //ok
			
			 //do_exec("ls"); //ok
			 //do_exec("adb install /storage/sda1/StabilityTest_2.7.apk"); //fail
			 //do_exec("sh /storage/sda1/cpufreq.sh");//fail
			 //do_exec("lsmod");//ok
			 //do_exec("date");//ok
			 
			 //do_exec("cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_cur_freq");//fail
		     //do_exec("cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"); ok
			 
		
	};
	

	 String do_exec(String cmd) {  
	        String s = "";  
	        
	        try {  
	     
	            Process p = Runtime.getRuntime().exec(cmd);  
	          
	            BufferedReader in = new BufferedReader(  
	                                new InputStreamReader(p.getInputStream()));
	            
	            String line = null;  
	            while ((line = in.readLine()) != null) {  
	                s += line + "\n";                 
	            }
	       
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        text.setText(s);
       
	        return cmd;       
	    }
	};

}

*/			
		
	