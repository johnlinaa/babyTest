package www.khh.domain.babytest;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import www.khh.domain.babytest.apputility.AppUtility;
import www.khh.domain.babytest.apputility.TakeGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_TakeGroupMod#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_TakeGroupMod extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

     private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    private ListView listTakeGroup_Mod;
    private TextView txtTgTitle_M;
    private TextView txtTgContent_M;
    private Button btnNewTakeGroup_Mod;
    private Button btnEnter_M;
    private Button btnBack_M;
    private EditText editTgMsg_M;
    private EditText editTitle_M;
    private List<TakeGroup>data;
    private SwipeRefreshLayout mSwipeLayout;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_TakeGroupMod.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_TakeGroupMod newInstance(String param1, String param2) {
        Fragment_TakeGroupMod fragment = new Fragment_TakeGroupMod();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_TakeGroupMod() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            this.getActivity().setTitle("討論區");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment__take_group_mod, container, false);
        listTakeGroup_Mod=(ListView)view.findViewById(R.id.listTakeGroup_Mod);
        btnNewTakeGroup_Mod=(Button)view.findViewById(R.id.btnNewTakeGroup_Mod);

        //TODO:新增回復
        btnNewTakeGroup_Mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View layout = layoutInflater.inflate(R.layout.list_dialog_takegroup_mod, null);
                final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setView(layout)
                        .setCancelable(false)
                        .create();
                dialog.show();

                //問出成員
                editTgMsg_M=(EditText)layout.findViewById(R.id.editTgMsg_M);
                btnEnter_M=(Button)layout.findViewById(R.id.btnEnter_M);
                btnBack_M=(Button)layout.findViewById(R.id.btnBack_M);
                //按下返回鍵
                btnBack_M.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                //按下確定送出
                btnEnter_M.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TakeGroupMod_N_Task takeGroupMod_n_task=new TakeGroupMod_N_Task();
                        if(editTgMsg_M.getText().length()<5){
                            Toast.makeText(getActivity(),"請輸入5字以上",Toast.LENGTH_SHORT).show();
                        }else {
                            String url = null;
                            try {
                                url = String.format(AppUtility.HOST + AppUtility.ParentNewContent
                                        , AppUtility.PREFER_UserID
                                        , URLEncoder.encode(editTgMsg_M.getText().toString(), "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                Log.i("編碼錯誤", e.getMessage());
                            }
                            takeGroupMod_n_task.execute(url);
                            dialog.dismiss();
                        }



                    }
                });
            }
        });
//        TakeGroup_ModTask takeGroup_modTask=new TakeGroup_ModTask();
//        takeGroup_modTask.execute(AppUtility.HOST+AppUtility.ParentTakeGroup);
//
//        mSwipeLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
//        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mSwipeLayout.setRefreshing(false);
//                        //非同步下載資料,再鋪畫面
//                        TakeGroup_ModTask takeGroup_modTask=new TakeGroup_ModTask();
//                        takeGroup_modTask.execute(AppUtility.HOST+AppUtility.ParentTakeGroup);
//                    }
//                }, 1000);
//            }
//        });





   return  view;
    }
//    public class  TakeGroup_ModTask  extends AsyncTask<String,Void,String>{
//        private ProgressDialog dialog;
//        @Override
//        protected String doInBackground(String... params) {
//            Log.i("URL稽核", params[0]);
//            return AppUtility.streamToString(AppUtility.openConnection(params[0], "GET"), "UTF-8");
//        }
//        @Override
//        protected void onPreExecute() {
//            dialog = AppUtility.createDialog(getActivity(),"下載中...");
//            dialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            dialog.dismiss();
//            Log.i("JSON稽核", s);
//            data = new ArrayList<>();
//
//            try {
//                JSONArray jsonArray = new JSONArray(s);
//                for(int a = 0; a < jsonArray.length(); a++){
//                    JSONObject jsonObject = jsonArray.getJSONObject(a);
//                    TakeGroup tG=new TakeGroup();
//
//                    tG.setMemoID(jsonObject.getString("memoID"));
//                    tG.setUserID(jsonObject.getString("userID"));
//                    tG.setMemoContent(jsonObject.getString("memoContent"));
//                    tG.setUserName(jsonObject.getString("userName"));
//                    tG.setMemoTitle(jsonObject.getString("memoTitle"));
//                    data.add(tG);
//                }
//            } catch (JSONException e) {
//                Log.i("JSON問題", e.getMessage());
//            }
//            TakeGroupModAdapter takeGroupModAdapter=new TakeGroupModAdapter();
//            listTakeGroup_Mod.setAdapter(takeGroupModAdapter);
//        }
//    }
//
//
//public  class TakeGroupModAdapter extends BaseAdapter{
//    private LayoutInflater inflater = LayoutInflater.from(getActivity());
//        @Override
//        public int getCount() {
//            return data.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewTag tag = new ViewTag();
//           if(convertView==null){
//               convertView=inflater.inflate(R.layout.list_takegroup_mod,null);
//               tag.txtTgContetn_M=(TextView)convertView.findViewById(R.id.txtTgContent_M);
//               convertView.setTag(tag);
//           }else {
//            tag = (ViewTag)convertView.getTag();
//            }
//           tag.txtTgContetn_M.setText(data.get(position).getMemoContent());
//            return convertView;
//        }
//
//
//    public class ViewTag{
//        private TextView txtTgContetn_M;
//    }
//
//    }

    //TODO 新增回覆的Task
    public  class TakeGroupMod_N_Task extends  AsyncTask<String,Void,String>{
        private ProgressDialog dialog;
        @Override
        protected String doInBackground(String... params) {
            Log.i("URL稽核",params[0]);
            return AppUtility.streamToString(AppUtility.openConnection(params[0], "GET"), "UTF-8");
        }
        //事前
        @Override
        protected void onPreExecute() {
            dialog = AppUtility.createDialog(getActivity(), "上傳中...");
            dialog.show();
        }
        //事後
        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            Log.i("JSON內容稽核", s);

            if (s.equals("Success")) {
                Toast.makeText(getActivity(), "新增成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "新增失敗", Toast.LENGTH_LONG).show();
            }

            Fragment_TakeGroupMod fragment_takeGroupMod=Fragment_TakeGroupMod.newInstance("","");
            fragmentManager=getFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contain,fragment_takeGroupMod);
            fragmentTransaction.commit();

            AppUtility.curentFragment=fragment_takeGroupMod;

        }

        }


}
