package www.khh.domain.babytest.apputility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.khh.domain.babytest.R;

/**
 * Created by User on 2016/2/17.
 */
public class AppAdapter {

    public static BaseAdapter spinBuyAdapter(Context context){
SpinBuyAdapter spinBuyAdapter=new SpinBuyAdapter(context);
        return spinBuyAdapter;
    }



    private static  class SpinBuyAdapter extends BaseAdapter{
        private LayoutInflater inflater;

        public SpinBuyAdapter(Context context){
            inflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {

            if(position == 0){
                return "楊寶寶";
            }else if(position == 1){
                return "練寶寶";
            }else{
                return "王寶寶";
            }
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewTag tag = new ViewTag();
            String [] babyName={"楊寶寶","練寶寶","王寶寶"};
            if(convertView==null){
                convertView=inflater.inflate(R.layout.spin_buy,null);
                tag.StxtBuy=(TextView)convertView.findViewById(R.id.StxtBuy);
                convertView.setTag(tag);
            }else{
                tag = (ViewTag)convertView.getTag();

            }

            tag.StxtBuy.setText(babyName[position]);
            return convertView;
        }



        private class  ViewTag{
            private TextView StxtBuy;
        }
    }
}



