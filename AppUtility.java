package www.khh.domain.babytest.apputility;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Window;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 2016/2/15.
 */
public class AppUtility {

    public static String PREFER_UserID;
    public static String PREFER_UserName;
    public static String PREFER_Name;
    public static String PREFER_Title;
    public static String PREFER_Unique; //全球唯一識別碼

    public static Fragment curentFragment;
    //TODO URL區域
    //伺服器路徑
    public static final String HOST = "http://192.168.0.100:8080/GjunService/rest";
    //登入
    public static final String URL_Verify = "/UserLogin/UserCheck?Account=%s&Password=%s";
    //新增帳戶
    public static final String AddUser="/UserLogin/adduser?UserName=%s&Title=%s&Account=%s&Password=%s";



    //托育中心紀錄
    public static final String BossWriteMsgList = "/Boss/allevent";
    //照育討論區
    public static final String BossWriteMsgNew = "/Boss/addtip?UserID=%s&MemoTitle=%s";

    public static final String ParentNewContent="/Boss/addcontent?ContentUser=%s&ContentRecord=%s";
    public static final String ParentTakeGroup="/Boss/tip";

    //購買照育權限
    public static final String BuyPermission="/Boss/AddBaby?UserID=%s&BabyName=%s";
//    public static final String ParentTakeGroupMsg="/Boss/addtitle?UserID=%s&MemoTitle=%s";

    public static ProgressDialog createDialog(Context context,String msg){
        ProgressDialog dialog = new ProgressDialog(context,android.R.style.Theme_Holo_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setMessage(msg);
        dialog.show();

        return dialog;
    }

    public static InputStream openConnection(String urlString,String method){
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
            httpUrl.setRequestMethod(method);
            is = httpUrl.getInputStream();

        } catch (MalformedURLException e) {
            Log.i("URL格式錯誤", e.getMessage());
        } catch (IOException e) {
            Log.i("IO錯誤", e.getMessage());
        }


        return is;

    }

    //InputStream轉String
    public static String streamToString(InputStream is, String encode){
        StringBuilder sb = null;
        InputStreamReader reader = null;

        try {
            reader = new InputStreamReader(is, encode);
            //讀取步幅
            char[] step = new char[10];
            sb = new StringBuilder();

            int realLength = -1;
            while((realLength = reader.read(step)) > -1){
                sb.append(step,0,realLength);
            }


        } catch (UnsupportedEncodingException e) {
            Log.i("編碼錯誤", e.getMessage());
        } catch (IOException e) {
            Log.i("IO錯誤", e.getMessage());
        }

        try {
            reader.close();
        } catch (IOException e) {
            Log.i("IO錯誤", e.getMessage());
        }

        return sb.toString();
    }
}
