package www.khh.domain.babytest;


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
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import www.khh.domain.babytest.apputility.AppUtility;
import www.khh.domain.babytest.apputility.CenterList;
import www.khh.domain.babytest.apputility.JsonAll;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_BabyCenterR#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_BabyCenterR extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listBabyCenter;
    private List<JsonAll>data;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private SwipeRefreshLayout mSwipeLayout;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_BabyCenterR.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_BabyCenterR newInstance(String param1, String param2) {
        Fragment_BabyCenterR fragment = new Fragment_BabyCenterR();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_BabyCenterR() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            this.getActivity().setTitle("托育中心參訪紀錄");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment__babycenter_r, container, false);

        listBabyCenter=(ListView)view.findViewById(R.id.listBabyCenter);

        //TODO 按鈕:listView的item按鈕
        listBabyCenter.setOnItemClickListener(new AdapterView.OnItemClickListener(){

          @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              Intent intent=new Intent(getActivity(),Fragment_BabyCenter_Detail.class);
              JsonAll jsonAll=data.get(position);
              intent.putExtra("center_record",jsonAll);
              getActivity().setIntent(intent);


              fragmentManager=getFragmentManager();
              fragmentTransaction=fragmentManager.beginTransaction();
              Fragment_BabyCenter_Detail fragment_babyCenter_detail=Fragment_BabyCenter_Detail.newInstance("","");
              listBabyCenter.setVisibility(View.INVISIBLE);
              fragmentTransaction.add(R.id.contain, fragment_babyCenter_detail);
              fragmentTransaction.commit();

              AppUtility.curentFragment=fragment_babyCenter_detail;

             }

        });

        //非同步下載資料,再鋪畫面
        CenterTask centerTask=new CenterTask();
        centerTask.execute(AppUtility.HOST + AppUtility.BossWriteMsgList);

        mSwipeLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeLayout.setRefreshing(false);
                        //非同步下載資料,再鋪畫面
                        CenterTask centerTask=new CenterTask();
                        centerTask.execute(AppUtility.HOST+ AppUtility.BossWriteMsgList);
                    }
                }, 1000);
            }
        });

        return  view;
    }

public class  CenterTask extends AsyncTask<String,Void,String>{

    private ProgressDialog dialog;
    @Override
    protected String doInBackground(String... params) {
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

        try {
            JSONArray jsonArray = new JSONArray(s);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
               JsonAll jsonAll=new JsonAll();


                jsonAll.setEventLevel(jsonObject.getString("eventLevel"));
                jsonAll.setEventLocation(jsonObject.getString("eventLocation"));
                jsonAll.setLocation(jsonObject.getString("location"));
                jsonAll.setEventContent(jsonObject.getString("eventContent"));
                jsonAll.setIsNot(jsonObject.getString("isNot"));
                jsonAll.setEventName(jsonObject.getString("eventName"));


                DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd ");
                Date date = null;
                try {
                    date = dateF.parse(jsonObject.getString("eventTime"));
                } catch (ParseException e) {
                    Log.i("日期錯誤", e.getMessage());
                }
                jsonAll.setEventTime(dateF.format(date));

                data.add(jsonAll);
            }

        } catch (JSONException e) {
            Log.i("JSON問題", e.getMessage());
        }
   CenterAdapter centerAdapter=new CenterAdapter();
        listBabyCenter.setAdapter(centerAdapter);

    }
}

    public  class  CenterAdapter extends BaseAdapter{
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

            if (convertView==null){
                convertView=inflater.inflate(R.layout.list_babycenter,null);
                tag.babySitter=(TextView)convertView.findViewById(R.id.babySitter);
                tag.buildTime=(TextView)convertView.findViewById(R.id.buildTime);
                convertView.setTag(tag);
            }else{
                tag = (ViewTag)convertView.getTag();
            }
            tag.babySitter.setText(data.get(position).getEventLevel());
            tag.buildTime.setText(data.get(position).getEventTime());

            return convertView;
        }


    public class  ViewTag{
        private TextView babySitter;
        private TextView buildTime;
    }

    }




}


