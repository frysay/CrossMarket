package com.crossshop;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dialog extends Activity {

	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);

		setResult(0);

		login = (Button) findViewById(R.id.dialog_login);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				EditText username = (EditText) findViewById(R.id.fld_username);
				EditText password = (EditText) findViewById(R.id.fld_pwd);
				String usernameValue = username.getText().toString();
				String passwordValue = password.getText().toString();

				if(usernameValue.compareTo("") != 0 && passwordValue.compareTo("") != 0) {
					SharedPreferences prefs = getSharedPreferences("smartshop_pref", MODE_PRIVATE);
					Editor editor = prefs.edit();
					editor.putString("username", usernameValue);
					editor.putString("password", passwordValue);
					editor.commit();

					setResult(-1);
					finish();
				}else {
					Toast.makeText(getApplicationContext(), "Error: invalid login", Toast.LENGTH_LONG).show();
				}
			}
		});

	}
}
