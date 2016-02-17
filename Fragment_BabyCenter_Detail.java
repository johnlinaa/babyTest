package www.khh.domain.babytest;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import www.khh.domain.babytest.apputility.AppUtility;
import www.khh.domain.babytest.apputility.CenterList;
import www.khh.domain.babytest.apputility.JsonAll;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_BabyCenter_Detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_BabyCenter_Detail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView txtTitle;
    private TextView txtGoal;
    private TextView txtContent;
    private TextView txtLocation;
    private TextView txtTime;
    private Button btnBack;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_BabyCenter_Detail.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_BabyCenter_Detail newInstance(String param1, String param2) {
        Fragment_BabyCenter_Detail fragment = new Fragment_BabyCenter_Detail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_BabyCenter_Detail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            this.getActivity().setTitle("詳細托育中心紀錄");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__babycenter__detail, container, false);
        txtTitle=(TextView)view.findViewById(R.id.txtTitle);
        txtGoal=(TextView)view.findViewById(R.id.txtGoal);
        txtContent=(TextView)view.findViewById(R.id.txtContent);
        txtLocation=(TextView)view.findViewById(R.id.txtLocation);
        txtTime=(TextView)view.findViewById(R.id.txtTime);
        btnBack=(Button)view.findViewById(R.id.btnBack);



       JsonAll jsonAll=(JsonAll)getActivity().getIntent().getSerializableExtra("center_record");
        txtTitle.setText(jsonAll.getLocation());
        txtGoal.setText(jsonAll.getIsNot());
        txtContent.setText(jsonAll.getEventContent());
        txtLocation.setText(jsonAll.getEventLocation());
        txtTime.setText(jsonAll.getEventName());

        //TODO 按鈕:返回
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //移除該頁面
                
                fragmentTransaction.remove(AppUtility.curentFragment);
                fragmentTransaction.commit();

                ListView  listBabyCenter=(ListView)getActivity().findViewById(R.id.listBabyCenter);
                listBabyCenter.setVisibility(View.VISIBLE);

                Fragment_BabyCenterR fragment_babyCenterR=Fragment_BabyCenterR.newInstance("","");
                AppUtility.curentFragment=fragment_babyCenterR;

            }
        });


        return  view;
    }


}
