package cn.edu.gdmec.t00385.remotecalldemo;

import cn.edu.gdmec.t00385.remoteservicedemo.AllResult;
import cn.edu.gdmec.t00385.remoteservicedemo.IMathService;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button mybtn1,mybtn2,mybtn3,mybtn4;
	TextView mytv;
	Intent myit = new Intent("cn.edu.gdmec.t00385.remoteservicedemo");
	boolean isbind = false;

	
	//声明远程调用接口；
	private IMathService mySvr;
	
	private ServiceConnection myConn = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			//根据AIDL生成的java接口声明来创建本地服务对象
			//跨进程服务
			mySvr = IMathService.Stub.asInterface(arg1);
			Toast.makeText(MainActivity.this,"get service binder", 1000).show();
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mySvr = null;
			
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mytv = (TextView) findViewById(R.id.textView1);
		mybtn1 = (Button) findViewById(R.id.button1);
		mybtn2 = (Button) findViewById(R.id.button2);
		mybtn3 = (Button) findViewById(R.id.button3);
		mybtn4= (Button) findViewById(R.id.button4);
		
		mybtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				bindService(myit,myConn,Context.BIND_AUTO_CREATE);
			}
			
		});
		
		mybtn3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				long a=100;
				long b=200;
				long result=0;
				try {
					result=mySvr.Add(a, b);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mytv.setText(String.valueOf(result));
				
			}
			
		});
		
		mybtn4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AllResult allResult=null ;
				long a = Math.round(Math.random()*100);
				long b = Math.round(Math.random()*100);
				try {
					allResult =mySvr.CompuerAny(a, b);
					
					String str =  String.valueOf(a)+" and "+String.valueOf(b)+"\n";
					str +="add :"+String.valueOf(allResult.AddResult)+"\n";
					str +="sub :"+String.valueOf(allResult.SubResult)+"\n";
					str +="mul :"+String.valueOf(allResult.MulResult)+"\n";
					str +="div :"+String.valueOf(allResult.DivResult)+"\n";
					mytv.setText(str);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
