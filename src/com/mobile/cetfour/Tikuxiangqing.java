package com.mobile.cetfour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobile.cetfour.ui.MainActivity;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Tikuxiangqing extends Activity {

	private ListView listView;
	private List<Map<String, Object>> dates = new ArrayList<Map<String, Object>>();
	private SimpleAdapter adapter2;
	String title[] = new String[1024];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tikuxiangqing);
		listView = (ListView) findViewById(R.id.listView123);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name1");

		String url = "http://www.huanglinqing.com/cet4/cettiku/getjson.php?name="
				+ name;
		new HTTPutils();
		HTTPutils.getNewsJSON(url, handler);
		registerForContextMenu(listView);

	}

	// 长按事件菜单
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo contextMenuInfo) {
		menu.setHeaderTitle("请选择操作");
		menu.add(0, 1, 0, "删除");
		menu.add(0, 2, 0, "取消");
	}

	//
	// 事件
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 1:
			ContextMenuInfo info = item.getMenuInfo();
			AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			//
			int position = contextMenuInfo.position;
			AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
			RequestParams params = new RequestParams();// 保存传入的参数
			params.add("title", title[position]);
			Intent intent = getIntent();
			final String name = intent.getStringExtra("name1");
			asyncHttpClient.post("http://www.huanglinqing.com/cet4/delete/", params,new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					Toast.makeText(Tikuxiangqing.this, "删除成功", 0).show();
					String url = "http://www.huanglinqing.com/cet4/cettiku/getjson.php?name="
							+ name;
					new HTTPutils();
					dates.clear();
					HTTPutils.getNewsJSON(url, handler);
					
					adapter2.notifyDataSetChanged();
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					Toast.makeText(Tikuxiangqing.this, "服务器繁忙", 0).show();
				}
			});
			break;
		default:
			System.out.println("用户取消操作");
		}
		return false;

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			try {
				JSONArray array = new JSONArray(json);
				for (int i = 0; i < array.length(); i++) {
					JSONObject object = array.getJSONObject(i);
					title[i] = object.getString("title");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("title", title[i]);
					dates.add(map);
				}
				adapter2 = new SimpleAdapter(Tikuxiangqing.this, dates,
						R.layout.item, new String[] { "title" },
						new int[] { R.id.textView1 });
				listView.setAdapter(adapter2);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		};

	};

	// 添加题目按钮监听事件
	public void add(View v) {
		Intent intent = getIntent();
		String name = intent.getStringExtra("name1");
		Intent intent2 = new Intent(this, Addti.class);
		intent2.putExtra("name", name);
		startActivity(intent2);
	}
}
