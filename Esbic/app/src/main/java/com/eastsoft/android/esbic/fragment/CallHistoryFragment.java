package com.eastsoft.android.esbic.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.eastsoft.android.esbic.R;


/**
 * Created by sofa on 2016/1/26.
 */
public class CallHistoryFragment extends Fragment implements AdapterView.OnItemClickListener{
    private View rootView;
    private ListView listView;
    //private List<Map<String,Object>> mapList;
    private String[] strings;
    private MyVolumeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.call_record,null);
        initData();
        return rootView;
    }

    private void initData(){
        listView=(ListView)rootView.findViewById(R.id.volume_setting);
        strings=new String[]{"铃声选择","音量设置","静音模式","短信铃声","震动模式"};
        adapter=new MyVolumeAdapter(this.getActivity(),strings);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
