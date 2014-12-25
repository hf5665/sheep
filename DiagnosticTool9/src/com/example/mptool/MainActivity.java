package com.example.mptool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.conn.util.InetAddressUtils;

import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  {

	private Button videotest,sdcardtest,wifitest,ethtest;
	private TextView Result,m,text_wifi,text_eth,text_tmp;
	public String readData,temp,ipv4,ethname,choice;
	
	//public String localtest="2_Dongle"; //[HF]local test
	private static final String TAG = "[HF]";
	private WifiManager mWifiManager;
	private WifiConfiguration wifiConfiguration;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		videotest=(Button)findViewById(R.id.btnvideotest);
		videotest.setOnClickListener(videotestclick);
		sdcardtest=(Button)findViewById(R.id.btnsdcardtest);
		sdcardtest.setOnClickListener(sdcardtestclick);		
		wifitest=(Button)findViewById(R.id.btnwifitest);
		wifitest.setOnClickListener(wifitestclick);
		ethtest=(Button)findViewById(R.id.btnethtest);
		ethtest.setOnClickListener(ethtestclick);
		Result=(TextView)findViewById(R.id.textResult);
		text_wifi=(TextView)findViewById(R.id.text_wifi);
		text_eth=(TextView)findViewById(R.id.text_eth);

		m=(TextView)findViewById(R.id.text_m);
		text_tmp=(TextView)findViewById(R.id.text_tmp);
		
		Log.e(TAG, "start onCreate.~~~");   
		try {
			FileReader fr = new FileReader("/storage/sda1/product_type.txt");
			BufferedReader br =new BufferedReader(fr);
			readData="";
			temp=br.readLine();
			
			while (temp!=null){
				readData+=temp;
				temp=br.readLine();				
			}
			
			Context context =getApplicationContext();
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, "Product type:"+ readData,duration);
			toast.show();		
			m.setText("Product type:"+readData);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {
			Thread.sleep(1000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		new GoodTask().execute();
		new GoodTask2().execute();
		
		wifitest.setEnabled(false);
		ethtest.setEnabled(false);
		sdcardtest.setEnabled(false);
		if(m.getText().toString().equals("Product type:1_OTT"))
		//if(localtest=="1_OTT") //[HF]local test
		{
		videotest.setVisibility(View.VISIBLE);
		text_wifi.setVisibility(View.VISIBLE);
		text_eth.setVisibility(View.VISIBLE);
		ethtest.setVisibility(View.VISIBLE);
		}
		else
		{
			ethtest.setVisibility(View.INVISIBLE);
			text_wifi.setVisibility(View.VISIBLE);
			text_eth.setVisibility(View.INVISIBLE);
			
		}
		
		
	}

	
	
	private Button.OnClickListener videotestclick=new Button.OnClickListener(){
		public void onClick(View v){
		 			
			Context context =getApplicationContext();
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, "Video testing",duration);
			toast.show();
		
			File file = new File("/storage/sda1/VIDEO0002.mp4");
			Intent intent =new Intent(Intent.ACTION_VIEW);
			intent.setAction(android.content.Intent.ACTION_VIEW);
				
			intent.setDataAndType(Uri.fromFile(file), "video/mp4");
			startActivity(intent);
			
			videotest.setEnabled(false);
			sdcardtest.setEnabled(true);
			wifitest.setEnabled(false);	
			ethtest.setEnabled(false);	
		}
	};

	private Button.OnClickListener sdcardtestclick=new Button.OnClickListener(){
		public void onClick(View v){
			
			
			Context context =getApplicationContext();
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, "SD Card testing,press return key to exit.",duration);
			toast.show();
				
			File file = new File("/storage/mmcblk1p1/212.jpg");
			
			
			
			Intent intent =new Intent(Intent.ACTION_VIEW);
			intent.setAction(android.content.Intent.ACTION_VIEW);
				
			intent.setDataAndType(Uri.fromFile(file), "image/jpg");
			startActivity(intent);
			
			videotest.setEnabled(false);
			sdcardtest.setEnabled(false);
			wifitest.setEnabled(true);	
			ethtest.setEnabled(false);	
		}
	};

	
	private Button.OnClickListener wifitestclick=new Button.OnClickListener(){
		public void onClick(View v){
	
			WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();  
			String networkIp = "";  
			int hostAddress = (wifiInfo == null) ? 0 : wifiInfo.getIpAddress();  
			byte[] addressByte = {  
			        (byte)(0xff & hostAddress),  
			        (byte)(0xff & (hostAddress >> 8)),  
			        (byte)(0xff & (hostAddress >> 16)),  
			        (byte)(0xff & (hostAddress >> 24))  
			};  
			InetAddress inet = null;  
			try {  
			    inet = InetAddress.getByAddress(addressByte);  
			    networkIp = inet.getHostAddress().toString();  
			} catch (Exception e) {  
			    // TODO Auto-generated catch block  
			    e.printStackTrace();  
			}  
			//text_wifi.setText("wifi ip="+getLocalIpAddress());
			text_wifi.setText("wifi ip="+networkIp);
			text_tmp.setText("AP Name:"+getSSID());
			
			wifitest.setEnabled(false);
			ethtest.setEnabled(true);
			
			
			
			if(m.getText().toString().equals("Product type:1_OTT"))
			//if(localtest=="1_OTT") //[HF]local test
			{
				Result.setVisibility(View.INVISIBLE);
				ethtest.setVisibility(View.VISIBLE);	
				text_eth.setVisibility(View.INVISIBLE);			
			}
			else
			{ 
				ethtest.setVisibility(View.INVISIBLE);
				text_eth.setVisibility(View.INVISIBLE);
				if(text_wifi.getText().toString().equals("wifi ip=0.0.0.0"))
				{
				Result.setVisibility(View.VISIBLE);
				Result.setTextColor(Color.RED);
				Result.setText("Result:Test fail!");
				}
				else
				{
				Result.setVisibility(View.VISIBLE);
				Result.setTextColor(Color.BLUE);
				Result.setText("Result:Test Pass!");
				}
			}
			
		}
			
	};

	
	
	private Button.OnClickListener ethtestclick=new Button.OnClickListener(){
		public void onClick(View v){
			ethtest.setEnabled(false);
			
		
			
			WifiAdmin wifiAdmin = new WifiAdmin(MainActivity.this);  
			wifiAdmin.closeWifi(); 
			
			
			
			text_eth.setText("eth ip="+getLocalIpAddress());
		
			ethtest.setEnabled(false);
			ethtest.setVisibility(View.VISIBLE);
			text_eth.setVisibility(View.VISIBLE);
			if(text_eth.getText().toString().equals("eth ip=null")||text_wifi.getText().toString().equals("wifi ip=0.0.0.0") )
			{
			Result.setVisibility(View.VISIBLE);
			Result.setTextColor(Color.RED);
			Result.setText("Result:Test fail!");
			}
			else
			{
			Result.setVisibility(View.VISIBLE);
			Result.setTextColor(Color.BLUE);
			Result.setText("Result:Test Pass!");
			}
				
			
			
		}
			
	};
	
	//will del
	public void pause(int seconds)
	{
	    synchronized(this)
	    {
	        try { 
	            this.wait(seconds); 
	        } catch (InterruptedException ie) { 
	            ie.printStackTrace();
	        }
	    }
	}
	//will del
	
	
	public String getLocalIpAddress()
	  {
	          try {
	              for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	                  NetworkInterface intf = en.nextElement();
	                  Log.e(TAG, "getName ="+intf.getName());
	                  if(intf.getName().toLowerCase().equals("eth0")|| intf.getName().toLowerCase().equals("wlan0"))
	                  {
	                  for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                      InetAddress inetAddress = enumIpAddr.nextElement();
	                      if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4 = inetAddress.getHostAddress()))  {
	                         return inetAddress.getHostAddress().toString();
	                          
	                      }
	                  }
	              }
	              
	              }
	          } catch (Exception ex) {
	              Log.e("IP Address", ex.toString());
	          }
	          return null;
	      }
				

		
	
	public String getSSID(){
		WifiManager wifiManager = (WifiManager) getSystemService (Context.WIFI_SERVICE);
		WifiInfo info = wifiManager.getConnectionInfo ();
	return info.getSSID ();
		
	}
	
	@Override  
    protected void onPause() { 
		//WifiAdmin wifiAdmin = new WifiAdmin(MainActivity.this);  		
		//wifiAdmin.closeWifi();
		//Log.e(TAG, "call wifiAdmin.closeWifi");
        super.onPause();  
        Log.v(TAG, "onPause");  
    }  
    @Override  
    protected void onStop() {  
        super.onStop();  
        Log.v(TAG, "onStop");  
    }  
	
	@Override  
    protected void onDestroy() {   
        super.onDestroy();   
        
        Log.e(TAG, "start onDestroy~~~");   
    }   
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class WifiAdmin {   
	    // 定义WifiManager对象    
	    private WifiManager mWifiManager;   
	    // 定义WifiInfo对象    
	    private WifiInfo mWifiInfo;   
	    // 扫描出的网络连接列表    
	    private List<ScanResult> mWifiList;   
	    // 网络连接列表    
	    private List<WifiConfiguration> mWifiConfiguration;   
	    // 定义一个WifiLock    
	    WifiLock mWifiLock;  
	    private ConnectivityManager mConnectivityManager;
	  
	   
	    // 构造器    
	    public WifiAdmin(Context context) {   
	        // 取得WifiManager对象    
	        mWifiManager = (WifiManager) context   
	                .getSystemService(Context.WIFI_SERVICE);   
	        // 取得WifiInfo对象    
	        mWifiInfo = mWifiManager.getConnectionInfo();   
	        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    }   
	   
	    // 打开WIFI    
	    public void openWifi() {   
	      // if (!mWifiManager.isWifiEnabled()) { 
	    	 if(mWifiManager.getWifiState()== WifiManager.WIFI_STATE_DISABLED){
	    		 mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	            mWifiManager.setWifiEnabled(true);   
	        }   
	    }   
	   
	    // 关闭WIFI    
	    public void closeWifi() {   
	      //  if (mWifiManager.isWifiEnabled()) {   
	        if(mWifiManager.getWifiState()== WifiManager.WIFI_STATE_ENABLED){	
	            mWifiManager.setWifiEnabled(false);   
	        }   
	    }   
	   
	    // 检查当前WIFI状态    
	    public int checkState() {   
	        return mWifiManager.getWifiState();   
	    }   
	   
	    // 锁定WifiLock    
	    public void acquireWifiLock() {   
	        mWifiLock.acquire();   
	    }   
	   
	    // 解锁WifiLock    
	    public void releaseWifiLock() {   
	        // 判断时候锁定    
	        if (mWifiLock.isHeld()) {   
	            mWifiLock.acquire();   
	        }   
	    }   
	   
	    // 创建一个WifiLock    
	    public void creatWifiLock() {   
	        mWifiLock = mWifiManager.createWifiLock("Test");   
	    }   
	   
	    // 得到配置好的网络    
	    public List<WifiConfiguration> getConfiguration() {   
	        return mWifiConfiguration;   
	    }   
	   
	    // 指定配置好的网络进行连接    
	    public void connectConfiguration(int index) {   
	        // 索引大于配置好的网络索引返回    
	        if (index > mWifiConfiguration.size()) {   
	            return;   
	        }   
	        // 连接配置好的指定ID的网络    
	        mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,   
	                true);   
	    }   
	   
	    public void startScan() {   
	        mWifiManager.startScan();   
	        // 得到扫描结果    
	        mWifiList = mWifiManager.getScanResults();   
	        // 得到配置好的网络连接    
	        mWifiConfiguration = mWifiManager.getConfiguredNetworks();   
	    }   
	   
	    // 得到网络列表    
	    public List<ScanResult> getWifiList() {   
	        return mWifiList;   
	    }   
	   
	    // 查看扫描结果    
	    public StringBuilder lookUpScan() {   
	        StringBuilder stringBuilder = new StringBuilder();   
	        for (int i = 0; i < mWifiList.size(); i++) {   
	            stringBuilder   
	                    .append("Index_" + new Integer(i + 1).toString() + ":");   
	            // 将ScanResult信息转换成一个字符串包    
	            // 其中把包括：BSSID、SSID、capabilities、frequency、level    
	            stringBuilder.append((mWifiList.get(i)).toString());   
	            stringBuilder.append("/n");   
	        }   
	        return stringBuilder;   
	    }  
	   
	    // 得到MAC地址    
	    public String getMacAddress() {   
	        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();   
	    }   
	   
	    // 得到接入点的BSSID    
	    public String getBSSID() {   
	        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();   
	    }   
	   
	    // 得到IP地址    
	    public int getIPAddress() {   
	        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();   
	    }   
	   
	    // 得到连接的ID    
	    public int getNetworkId() {   
	        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();   
	    }   
	   
	    // 得到WifiInfo的所有信息包    
	    public String getWifiInfo() {   
	        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();   
	    }   
	   
	    // 添加一个网络并连接    
	    public void addNetwork(WifiConfiguration wcg) {   
	     int wcgID = mWifiManager.addNetwork(wcg);   
	     boolean b =  mWifiManager.enableNetwork(wcgID, true);   
	     System.out.println("a--" + wcgID);  
	     System.out.println("b--" + b);  
	    }   
	   
	    // 断开指定ID的网络    
	    public void disconnectWifi(int netId) {   
	        mWifiManager.disableNetwork(netId);   
	        mWifiManager.disconnect();   
	    }   
	   
	//然后是一个实际应用方法，只验证过没有密码的情况：  
	   
	    public WifiConfiguration CreateWifiInfo(String SSID, String Password, int Type)   
	    {   
	    	Log.e(TAG, "SSID = " + SSID + "## Password = " + Password + "## Type = " + Type); //0529
	    	
	    	
	    	
	          WifiConfiguration config = new WifiConfiguration();     
	           config.allowedAuthAlgorithms.clear();   
	           config.allowedGroupCiphers.clear();   
	           config.allowedKeyManagement.clear();   
	           config.allowedPairwiseCiphers.clear();   
	           config.allowedProtocols.clear();   
	          config.SSID = "\"" + SSID + "\"";     
	            
	          WifiConfiguration tempConfig = this.IsExsits(SSID);             
	          if(tempConfig != null) {    
	              mWifiManager.removeNetwork(tempConfig.networkId);    
	          }  
	            
	          if(Type == 1) //WIFICIPHER_NOPASS  
	          {   
	   
	               //config.wepKeys[0] = "";
	        	   config.wepKeys[0] = "\"" + "\"";
	               config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);   
	               config.wepTxKeyIndex = 0;   
	          }   
	          if(Type == 2) //WIFICIPHER_WEP  
	          {   
	              config.hiddenSSID = true;  
	              config.wepKeys[0]= "\""+Password+"\"";   
	              config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);   
	              config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);   
	              config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);   
	              config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);   
	              config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);   
	              config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);   
	              config.wepTxKeyIndex = 0;   
	          }   
	          if(Type == 3) //WIFICIPHER_WPA  
	          {   
	          config.preSharedKey = "\""+Password+"\"";   
	          config.hiddenSSID = true;     
	          config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);     
	          config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);                           
	          config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);                           
	          config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);                      
	          //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);    
	          config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);  
	          config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);  
	          config.status = WifiConfiguration.Status.ENABLED;     
	          }  
	           return config;   
	    }   
	 
	      
	    private WifiConfiguration IsExsits(String SSID)    
	    {    
	        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();    
	           for (WifiConfiguration existingConfig : existingConfigs)     
	           {    
	             if (existingConfig.SSID.equals("\""+SSID+"\""))    
	             {    
	                 return existingConfig;    
	             }    
	           }    
	        return null;     
	    }  
	    
	} 
	

	
	
	
	
	public class GoodTask extends AsyncTask<Void, Integer, String> {
 
		@Override
		protected String doInBackground(Void... arg0) {
		//TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
			} catch (InterruptedException e) {
		//TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.e(TAG, "AsyncTask doInBackground~~~");
			WifiAdmin wifiAdmin = new WifiAdmin(MainActivity.this);  
			wifiAdmin.openWifi();
			Log.e(TAG, "call wifiAdmin.openWifi ");
	
			return null;}

@Override
protected void onPreExecute() {
// TODO Auto-generated method stub
super.onPreExecute();

// 背景工作處理"前"需作的事
}

@Override
protected void onProgressUpdate(Integer... values) {
// TODO Auto-generated method stub
super.onProgressUpdate(values);

// 背景工作處理"中"更新的事
}

@Override
protected void onPostExecute(String result) {
// TODO Auto-generated method stub
super.onPostExecute(result);

// 背景工作處理完"後"需作的事
}

@Override
protected void onCancelled() {
// TODO Auto-generated method stub
super.onCancelled();

// 背景工作被"取消"時作的事，此時不作 onPostExecute(String result)
}
}
	
	
	
	
	
	public class GoodTask2 extends AsyncTask<Void, Integer, String> {
        // <傳入參數, 處理中更新介面參數, 處理後傳出參數>
@Override
protected String doInBackground(Void... arg0) {
// TODO Auto-generated method stub

// 再背景中處理的耗時工作

    try {
		Thread.sleep(20000);
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	Log.e(TAG, "AsyncTask2 doInBackground~~~");
	WifiAdmin wifiAdmin = new WifiAdmin(MainActivity.this);  	
	wifiAdmin.addNetwork(wifiAdmin.CreateWifiInfo("AP001", "", 1));
    Log.e(TAG, "call addNetwork");	

return null; // 會傳給 onPostExecute(String result) 的 String result
}

@Override
protected void onPreExecute() {
// TODO Auto-generated method stub
super.onPreExecute();

// 背景工作處理"前"需作的事
}

@Override
protected void onProgressUpdate(Integer... values) {
// TODO Auto-generated method stub
super.onProgressUpdate(values);

// 背景工作處理"中"更新的事
}

@Override
protected void onPostExecute(String result) {
// TODO Auto-generated method stub
super.onPostExecute(result);

// 背景工作處理完"後"需作的事
}

@Override
protected void onCancelled() {
// TODO Auto-generated method stub
super.onCancelled();

// 背景工作被"取消"時作的事，此時不作 onPostExecute(String result)
}
}
	
		
	
	
	
	
	
}
