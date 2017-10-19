package com.mobile.cetfour;

import java.io.IOException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Addti extends Activity {

	private EditText editText1, editText2, editTexta, editTextb, editTextc,
			editTextd;
	private RadioGroup group;
	String answer;
	private CheckBox checkBox1,checkBox2,checkBox3,checkBox4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		if (name.equals("单选")) {
			setContentView(R.layout.activity_addti);
			init();
		}else if(name.equals("多选")){
			setContentView(R.layout.addduoxuan);
			initduo();
		}
		else if(name.equals("判断")){
			setContentView(R.layout.addpanduan);
			initpan();
		}
		
		

	}
	//初始化多选
	private void initduo(){
		
		editText1=(EditText) findViewById(R.id.editText1);//title
		editTexta=(EditText) findViewById(R.id.editText2);
		editTextb=(EditText) findViewById(R.id.EditText01);
		editTextc=(EditText) findViewById(R.id.EditText02);
		editTextd=(EditText) findViewById(R.id.EditText03);
		checkBox1=(CheckBox) findViewById(R.id.checkBox1);
		checkBox2=(CheckBox) findViewById(R.id.checkBox2);
		checkBox3=(CheckBox) findViewById(R.id.checkBox3);
		checkBox4=(CheckBox) findViewById(R.id.checkBox4);
		editText2=(EditText) findViewById(R.id.editText3);//detail
		
	}
	  
	//addd
	public void addd(View v){
		String title=editText1.getText().toString();
		String detail=editText2.getText().toString();
		String one=editTexta.getText().toString();
		String two=editTextb.getText().toString();
		String three=editTextc.getText().toString();
		String four=editTextd.getText().toString();
		if (title.isEmpty()||detail.isEmpty()||one.isEmpty()||two.isEmpty()||three.isEmpty()||four.isEmpty()) {
			Toast.makeText(Addti.this, "您输入的信息不完整", 0).show();
		}else if(!checkBox1.isChecked()&&!checkBox2.isChecked()&&!checkBox3.isChecked()&&!checkBox4.isChecked()){
			Toast.makeText(Addti.this, "您尚未选择本题答案", 0).show();
		}else{
			String da1="";
			String da2="";
			String da3="";
			String da4="";
			if (checkBox1.isChecked()) {
				da1="ok";
			}if (checkBox2.isChecked()) {
				da2="ok";
			}if (checkBox3.isChecked()) {
				da3="ok";
			}if (checkBox4.isChecked()) {
				da4="ok";
			}
			Intent intent = getIntent();
			String name = intent.getStringExtra("name");
			// 交互
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
			RequestParams params = new RequestParams();// 保存传入的参数
			params.add("title", title);
			params.add("one", one);
			params.add("two", two);
			params.add("three", three);
			params.add("four", four);
			
			params.add("types", name);
			params.add("deta", detail);
			params.add("daanone", da1);
			params.add("daantwo", da2);
			params.add("daanthree", da3);
			params.add("daanfour", da4);
			asyncHttpClient.post("http://www.huanglinqing.com/cet4/addti/addduo.php", params, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					Toast.makeText(Addti.this, "添加成功", 0).show();
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					Toast.makeText(Addti.this, "服务器繁忙", 0).show();
				}
			});
		}
	}
    //初始化判断
	private void initpan(){
		editText1=(EditText) findViewById(R.id.editText1);
		editText2=(EditText) findViewById(R.id.editText2); 
		group=(RadioGroup) findViewById(R.id.radioGroup1);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.radio0) {
					answer = "daanone";
				} else if (checkedId == R.id.radio1) {
					answer = "daantwo";
				} 
			}
		});
	}
//	/添加
	public void save(View v){
		String title=editText1.getText().toString();
		String deta=editText2.getText().toString();
		if (title.isEmpty()||deta.isEmpty()) {
			Toast.makeText(Addti.this, "您的内容输入的不完整", 0).show();
		}else{
			Intent intent = getIntent();
			String name = intent.getStringExtra("name");
			// 交互
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
			RequestParams params = new RequestParams();// 保存传入的参数
			params.add("title", title);
			params.add("one", "对");
			params.add("two", "错");
			
			params.add("types", name);
			params.add("deta", deta);
			params.add("answer", answer);
			asyncHttpClient.post("http://www.huanglinqing.com/cet4/addti/addpanduan.php", params,new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					Toast.makeText(Addti.this, "添加成功", 0).show();
					
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					Toast.makeText(Addti.this, "服务器繁忙", 0).show();
					
				}
			});
		}
		
		
	}
	// 初始化//单选
	private void init() {
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		group = (RadioGroup) findViewById(R.id.radioGroup1);
		editTexta = (EditText) findViewById(R.id.editText3
				);
		editTextb = (EditText) findViewById(R.id.editText4);
		editTextc = (EditText) findViewById(R.id.editText5);
		editTextd = (EditText) findViewById(R.id.editText6);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.radio0) {
					answer = "daanone";
				} else if (checkedId == R.id.radio1) {
					answer = "daantwo";
				} else if (checkedId == R.id.radio2) {
					answer = "daanthree";
				} else if (checkedId == R.id.radio3) {
					answer = "daanfour";
				}
			}
		});
	}

	// 添加题目//选择
	public void add(View v) {

		String title = editText1.getText().toString();
		String jiexi = editText2.getText().toString();
		String a = editTexta.getText().toString();
		String b = editTextb.getText().toString();
		String c = editTextc.getText().toString();
		String d = editTextd.getText().toString();
		if (title.isEmpty() || jiexi.isEmpty() || a.isEmpty() || b.isEmpty()
				|| c.isEmpty() || d.isEmpty()) {
			Toast.makeText(Addti.this, "您的内容输入的不完整", 0).show();
		}
		else{
			Intent intent = getIntent();
			String name = intent.getStringExtra("name");
			// 交互
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
			RequestParams params = new RequestParams();// 保存传入的参数
			params.add("title", title);
			params.add("one", a);
			params.add("two", b);
			params.add("three", c);
			params.add("four", d);
			params.add("types", name);
			params.add("deta", jiexi);
			params.add("answer", answer);
			asyncHttpClient.post("http://www.huanglinqing.com/cet4/addti/index.php", params,new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
	 				 Toast.makeText(Addti.this, "添加成功", 0).show();
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					// TODO Auto-generated method stub
					Toast.makeText(Addti.this, "服务器繁忙", 0).show();
				}
			});
		}
		

	}

}
