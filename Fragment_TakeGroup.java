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
import android.widget.AdapterView;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import www.khh.domain.babytest.apputility.AppUtility;
import www.khh.domain.babytest.apputility.TakeGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_TakeGroup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_TakeGroup extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;



    private Button btnNewTakeGroup;
    private EditText editTgTitle;
    private ListView listTakeGroup;
    private Button btnEnter;
    private Button btnBack;
    private List<TakeGroup>data;
    private SwipeRefreshLayout mSwipeLayout;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_TakeGroup.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_TakeGroup newInstance(String param1, String param2) {
        Fragment_TakeGroup fragment = new Fragment_TakeGroup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_TakeGroup() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            this.getActivity().setTitle("照育討論區");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__take_group, container, false);
        btnNewTakeGroup=(Button)view.findViewById(R.id.btnNewTakeGroup_Mod);
        listTakeGroup=(ListView)view.findViewById(R.id.listTakeGroup);


        //TODO 按鈕:listView的item按鈕
        listTakeGroup.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //1.夾帶資訊到下個畫面
                    Intent intent=new Intent(getActivity(),Fragment_TakeGroupMod.class);

                    TakeGroup takeGroup=data.get(position);
                    intent.putExtra("takegroup_mod", takeGroup);
                     getActivity().setIntent(intent);


                    //切畫面
                    Fragment_TakeGroupMod fragment_takeGroupMod=Fragment_TakeGroupMod.newInstance("","");
                    fragmentManager=getFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    listTakeGroup.setVisibility(View.INVISIBLE);
                    fragmentTransaction.add(R.id.contain,fragment_takeGroupMod);
                    fragmentTransaction.commit();
                    AppUtility.curentFragment=fragment_takeGroupMod;

                }
        });


                //TODO 按鈕:新增討論區-Dialog
                btnNewTakeGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                        View layout = layoutInflater.inflate(R.layout.list_dialog_new_takegroup, null);
                        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                .setView(layout)
                                .setCancelable(false)
                                .create();
                        dialog.show();


                        btnEnter = (Button) layout.findViewById(R.id.btnEnter);
                        btnBack = (Button) layout.findViewById(R.id.btnBack);
                        editTgTitle = (EditText) layout.findViewById(R.id.editTgTitle);

                        //按下取消鍵
                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        //按下確定送出
                        btnEnter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                NewTakeGroupTask newTakeGroupTask = new NewTakeGroupTask();
                                if (editTgTitle.getText().toString().length() < 5) {
                                    Toast.makeText(getActivity(), "請輸入5字以上", Toast.LENGTH_SHORT).show();
                                } else {
                                    String url = null;

                                    try {
                                        url = String.format(AppUtility.HOST + AppUtility.BossWriteMsgNew,
                                                AppUtility.PREFER_UserID,
                                                URLEncoder.encode(editTgTitle.getText().toString(), "UTF-8"));


                                    } catch (UnsupportedEncodingException e) {
                                        Log.i("編碼錯誤", e.getMessage());
                                    }

                                    newTakeGroupTask.execute(url);
                                    dialog.dismiss();
                                }
                            }
                        });


                    }
                });
        //非同步下載資料,再鋪畫面
        TakeGroupTask takeGroupTask=new TakeGroupTask();
        takeGroupTask.execute(AppUtility.HOST+AppUtility.ParentTakeGroup);

        mSwipeLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeLayout.setRefreshing(false);
                        //非同步下載資料,再鋪畫面
                        TakeGroupTask takeGroupTask=new TakeGroupTask();
                        takeGroupTask.execute(AppUtility.HOST+AppUtility.ParentTakeGroup);
                    }
                }, 1000);
            }
        });






        return  view;
    }
public class  TakeGroupTask extends AsyncTask<String,Void,String>{
    private ProgressDialog dialog;
    @Override
    protected String doInBackground(String... params) {
        Log.i("URL稽核",params[0]);
        return AppUtility.streamToString(AppUtility.openConnection(params[0], "GET"), "UTF-8");
    }

    @Override
    protected void onPreExecute() {
        dialog = AppUtility.createDialog(getActivity(),"下載中...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        Log.i("JSON稽核", s);
        data = new ArrayList<>();

        //把下載到的資訊鋪成畫面
        try {
            JSONArray jsonArray = new JSONArray(s);
            for(int p = 0; p < jsonArray.length(); p++){
                JSONObject jsonObject = jsonArray.getJSONObject(p);
                TakeGroup takeGroup = new TakeGroup();


                takeGroup.setMemoID(jsonObject.getString("memoID"));
                takeGroup.setUserID(jsonObject.getString("userID"));
                takeGroup.setUserName(jsonObject.getString("userName"));
                takeGroup.setMemoTitle(jsonObject.getString("memoTitle"));


                data.add(takeGroup);
            }
        } catch (JSONException e) {
            Log.i("JSON問題", e.getMessage());
        }
        TakeGroupAdapter takeGroupAdapter=new TakeGroupAdapter();
        listTakeGroup.setAdapter(takeGroupAdapter);
    }
}

    public  class TakeGroupAdapter extends BaseAdapter{
        private LayoutInflater inflater = LayoutInflater.from(getActivity());
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewTag tag = new ViewTag();
            if(convertView == null){
                convertView=inflater.inflate(R.layout.list_takegrouplist,null);
                tag.txtNewGroupTitle=(TextView)convertView.findViewById(R.id.txtNewGroupTitle);
                convertView.setTag(tag);
            } else{
                tag = (ViewTag)convertView.getTag();
            }
            tag.txtNewGroupTitle.setText(data.get(position).getMemoTitle());

            return convertView;
        }

        public class ViewTag{
            private TextView txtNewGroupTitle;
        }
    }





    //TODO 新增討論區的Task
    public  class  NewTakeGroupTask extends AsyncTask<String,Void,String>{
        private ProgressDialog dialog;
        @Override
        protected String doInBackground(String... params) {
            Log.i("URL稽核", params[0]);
            return AppUtility.streamToString(AppUtility.openConnection(params[0], "GET"), "UTF-8");
        }

        @Override
        protected void onPreExecute() {
            dialog = AppUtility.createDialog(getActivity(), "上傳中...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            Log.i("JSON內容稽核", s);

            if(s.equals("Success")){
                Toast.makeText(getActivity(), "新增成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),"新增失敗",Toast.LENGTH_SHORT).show();
            }

            Fragment_TakeGroup fragment_takeGroup=Fragment_TakeGroup.newInstance("","");
            fragmentManager=getFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contain,fragment_takeGroup);
            fragmentTransaction.commit();
            AppUtility.curentFragment=fragment_takeGroup;




        }
    }





}
