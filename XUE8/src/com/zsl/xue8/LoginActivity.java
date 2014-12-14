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

			// �����°汾����ʾ�û�����
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("�������")
					.setMessage("�����°汾,������������ʹ��.")
					.setPositiveButton("����",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// �������·���UpdateService
									// ����Ϊ�˰�update����ģ�黯�����Դ�һЩupdateService������ֵ
									// �粼��ID����ԴID����̬��ȡ�ı���,������app_nameΪ��
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
					.setNegativeButton("ȡ��",
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
			Builder b = new AlertDialog.Builder(this).setTitle("û�п��õ�����")
					.setMessage("�뿪��GPRS��WIFI��������");
			b.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent mIntent = new Intent("/");
					ComponentName comp = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					mIntent.setComponent(comp);
					mIntent.setAction("android.intent.action.VIEW");
					startActivityForResult(mIntent, 0); // �����������ɺ���Ҫ�ٴν��в�����������д�������룬�����ﲻ����д
				}
			}).setNeutralButton("ȡ��", new DialogInterface.OnClickListener() {
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
