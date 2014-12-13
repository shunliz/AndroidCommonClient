package com.zsl.xue8;

import com.zsl.services.UpdateService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {
	private boolean isConnected;
	private boolean logined = true;
	private int serverVersion = 2;
	
	private Button loginButton = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//isConnected = NetWorkStatus();
		
		checkVersion();
		
		loginButton = (Button)findViewById(R.id.login_bt);
		loginButton.setOnClickListener(new LoginButtonListener());
	}
	
	public void checkVersion() {
		PackageInfo packageInfo = null;
		try {
			packageInfo = getApplicationContext()
					.getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		int localVersion = packageInfo.versionCode;
		if (localVersion < serverVersion) {

			// 发现新版本，提示用户更新
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("软件升级")
					.setMessage("发现新版本,建议立即更新使用.")
					.setPositiveButton("更新",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// 开启更新服务UpdateService
									// 这里为了把update更好模块化，可以传一些updateService依赖的值
									// 如布局ID，资源ID，动态获取的标题,这里以app_name为例
									Intent updateIntent = new Intent(
											LoginActivity.this,
											UpdateService.class);
									updateIntent.putExtra(
											"app_name",
											getResources().getString(
													R.string.app_name));
									startService(updateIntent);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
			alert.create().show();

		}
	}

	private boolean NetWorkStatus() {
		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		cwjManager.getActiveNetworkInfo();

		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
			netSataus = true;
		}

		if (netSataus == false) {
			Builder b = new AlertDialog.Builder(this).setTitle("没有可用的网络")
					.setMessage("请开启GPRS或WIFI网络连接");
			b.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent mIntent = new Intent("/");
					ComponentName comp = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					mIntent.setComponent(comp);
					mIntent.setAction("android.intent.action.VIEW");
					startActivityForResult(mIntent, 0); // 如果在设置完成后需要再次进行操作，可以重写操作代码，在这里不再重写
				}
			}).setNeutralButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			}).show();
		}
		return netSataus;
	}
	
	class LoginButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			//TODO: get httpclient and call the server to verify the identity
			
			if (logined){
				Intent intent = new Intent();
				intent.putExtra("testIntent", "data to pass here");
				intent.setClass(LoginActivity.this, MainActivity.class);
				LoginActivity.this.startActivity(intent);
				finish();
			}
		}
	}
}
