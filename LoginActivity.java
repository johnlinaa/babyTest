package www.khh.domain.babytest.apputility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import www.khh.domain.babytest.AddUserActivity;
import www.khh.domain.babytest.MainActivity;
import www.khh.domain.babytest.R;
import www.khh.domain.babytest.babysitter.BabyActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText userID;
    private  EditText password;
    private Button btnLogin;
    private Button btnAdd;

    private  String verify="Error";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登入");

        userID=(EditText)findViewById(R.id.userID);
        password=(EditText)findViewById(R.id.password);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnAdd=(Button)findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });




        //TODO 按鈕:登入
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(),"帳號密碼不能為空",Toast.LENGTH_SHORT).show();
                }else if((userID.getText().toString().length() > 10) || (password.getText().toString().length() > 10)) {
                    Toast.makeText(getBaseContext(),"帳號或密碼不能大於10個字",Toast.LENGTH_SHORT).show();
                }else {
                    //非同步
                    String id = userID.getText().toString();
                    String pass = password.getText().toString();
                    String url = String.format(AppUtility.HOST + AppUtility.URL_Verify, id, pass);
                    //String url = AppUtility.URL_login_succ;

                    LoginTask loginTask = new LoginTask();
                    loginTask.execute(url);
                }
                }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private class LoginTask extends AsyncTask<String,Void,String>{
    private ProgressDialog dialog;
        @Override
        protected String doInBackground(String... params) {
            String jsonString = AppUtility.streamToString(AppUtility.openConnection(params[0], "GET"),"UTF-8");
            Log.i("URL登入", jsonString);
            return jsonString;
        }

        @Override
        protected void onPreExecute() {
            dialog =AppUtility.createDialog(LoginActivity.this,"驗證中...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();

            JSONArray jsonArray = null;
            JSONObject jsonObject = null;
            try {
                jsonArray = new JSONArray(s);
                jsonObject = jsonArray.getJSONObject(0);
                verify = jsonObject.getString("verify");
            } catch (JSONException e) {
                Log.i("Json問題", e.getMessage());
            }
            //2-1 驗證成功
            if (verify.equals("Success")) {
                //資訊先放入AppUtility
                try {
                    AppUtility.PREFER_UserID = jsonObject.getString("userID");
                    AppUtility.PREFER_Name = jsonObject.getString("userName");
                    AppUtility.PREFER_Title = jsonObject.getString("title");
                    } catch (JSONException e) {
                    Log.i("Json問題", e.getMessage());
                }
                Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_LONG).show();

                if(AppUtility.PREFER_Title.equals("父母")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else if(AppUtility.PREFER_Title.equals("保母") || AppUtility.PREFER_Title.equals("總經理")){
                    Intent intent = new Intent(LoginActivity.this, BabyActivity.class);
                    startActivity(intent);
                    finish();
                }

                else{
                    Toast.makeText(getBaseContext(),"身份未認可,請詢問人事部",Toast.LENGTH_LONG);
                }

                //2-2 驗證失敗
            } else if (verify.equals("Error")) {
                Toast.makeText(LoginActivity.this, "登入失敗,請重新輸入", Toast.LENGTH_LONG).show();
            }



        }
    }



}
