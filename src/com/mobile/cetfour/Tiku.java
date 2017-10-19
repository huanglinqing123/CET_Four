package com.mobile.cetfour;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Tiku extends Activity {

	private ListView listView;
	private Adapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tiku);
		listView = (ListView) findViewById(R.id.listView1);
		String name[] = { "单选题",  "多选题", "判断题" };
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, name);
		listView.setAdapter((ListAdapter) adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {
					Intent intent = new Intent(Tiku.this, Tikuxiangqing.class);
					intent.putExtra("name1", "单选");
					startActivity(intent);

				}else if(arg2==1){
					Intent intent = new Intent(Tiku.this, Tikuxiangqing.class);
					intent.putExtra("name1", "多选");
					startActivity(intent);
				}else if(arg2==2){
					Intent intent = new Intent(Tiku.this, Tikuxiangqing.class);
					intent.putExtra("name1", "判断");
					startActivity(intent);
				}

			}
		});
	}

}
