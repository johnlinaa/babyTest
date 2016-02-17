package www.khh.domain.babytest;

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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import www.khh.domain.babytest.apputility.AppUtility;
import www.khh.domain.babytest.apputility.LoginActivity;

public class AddUserActivity extends AppCompatActivity {
private EditText editUserName;
    private EditText editUserAccount;
    private EditText editUserPs;
    private EditText editTitle;
    private Button btnAddUser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        editUserName=(EditText)findViewById(R.id.editUserName);
        editTitle=(EditText)findViewById(R.id.editTitle);
        editUserPs=(EditText)findViewById(R.id.editUserPs);
        editUserAccount=(EditText)findViewById(R.id.editUserAccount);

       btnAddUser=(Button)findViewById(R.id.btnAddUser);


        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editUserName.getText().toString().isEmpty() ||
                        editUserAccount.getText().toString().isEmpty() ||
                        editUserPs.getText().toString().isEmpty()||
                        editTitle.getText().toString().isEmpty()) {
                    Toast.makeText(AddUserActivity.this, "欄位不能為空", Toast.LENGTH_SHORT).show();
                } else {

                    String url = null;
                    try {
                        url = String.format(AppUtility.HOST + AppUtility.AddUser,
                                URLEncoder.encode(editUserName.getText().toString(), "UTF-8"),
                                URLEncoder.encode(editTitle.getText().toString(), "UTF-8"),
                                URLEncoder.encode(editUserAccount.getText().toString(), "UTF-8"),
                                URLEncoder.encode(editUserPs.getText().toString(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        Log.i("編碼錯誤", e.getMessage());
                    }
                    AddUserTask addUserTask = new AddUserTask();
                    addUserTask.execute(url);

                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public class AddUserTask extends AsyncTask<String,Void,String>{
        private ProgressDialog dialog;
        @Override
        protected String doInBackground(String... params) {
            Log.i("URL稽核", params[0]);
            return AppUtility.streamToString(AppUtility.openConnection(params[0], "GET"), "UTF-8");
        }
        @Override
        protected void onPreExecute() {
            dialog = AppUtility.createDialog(AddUserActivity.this, "新增中...");
            dialog.show();
        }
        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            Log.i("JSON內容稽核", s);

            if(s.equals("Success")){
                Toast.makeText(AddUserActivity.this, "新增帳戶成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AddUserActivity.this,"新增帳戶失敗",Toast.LENGTH_SHORT).show();
            }
            //切到登入頁面
            Intent intent=new Intent(AddUserActivity.this, LoginActivity.class);
          startActivity(intent);
         }



    }





}
