package www.khh.domain.babytest.babysitter;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import www.khh.domain.babytest.Fragment_Main;
import www.khh.domain.babytest.MainActivity;
import www.khh.domain.babytest.R;
import www.khh.domain.babytest.apputility.AppAdapter;
import www.khh.domain.babytest.apputility.AppUtility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ByuPermisson#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ByuPermisson extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnEnter_Buy;
    private Button btnBack_Buy;
    private Spinner spinBuy;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ByuPermisson.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ByuPermisson newInstance(String param1, String param2) {
        Fragment_ByuPermisson fragment = new Fragment_ByuPermisson();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_ByuPermisson() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_fragment__byu_permisson, container, false);


     LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View layout = layoutInflater.inflate(R.layout.list_dialog_buypermission, null);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(layout)
                .setCancelable(false)
                .create();
        dialog.show();

        btnEnter_Buy=(Button)layout.findViewById(R.id.btnEnter_Buy);
        btnBack_Buy=(Button)layout.findViewById(R.id.btnBack_Buy);
        spinBuy=(Spinner)layout.findViewById(R.id.spinBuy);
        spinBuy.setAdapter(AppAdapter.spinBuyAdapter(getActivity()));


       //TODO 按鈕:購買
        btnEnter_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BaseAdapter adapter=AppAdapter.spinBuyAdapter(getActivity());
                String url = null;

                url = String.format(AppUtility.HOST+AppUtility.BuyPermission,
                        AppUtility.PREFER_UserID,
                        spinBuy.getSelectedItemPosition());

                 BuyPermissionTask buyPermissionTask=new BuyPermissionTask();
                buyPermissionTask.execute(url);


            }
        });
                //取消
                btnBack_Buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       dialog.dismiss();
                        Fragment_MainB fragment_mainb=Fragment_MainB.newInstance("","");
                        fragmentManager=getFragmentManager();
                        fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containBaby,fragment_mainb);
                        fragmentTransaction.commit();
                    }
                });






    return view;
    }

    public class BuyPermissionTask extends AsyncTask<String,Void,String>{
        private ProgressDialog dialog;
        @Override
        protected String doInBackground(String... params) {
            Log.i("URL", params[0]);
            return AppUtility.streamToString(AppUtility.openConnection(params[0], "GET"),"UTF-8");
        }

        //事前
        @Override
        protected void onPreExecute() {
            dialog = AppUtility.createDialog(getActivity(),"上傳中...");

            dialog.show();
        }

        //事後
        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();

            if (s.equals("Success")) {
                Toast.makeText(getActivity(), "購買成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "購買失敗", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
