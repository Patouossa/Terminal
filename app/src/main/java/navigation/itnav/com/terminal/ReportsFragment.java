package navigation.itnav.com.terminal;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CustomAdapter adapter;
    private ListView listView;


    public ReportsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if(getArguments() != null){
            this.ctx = (Context) getArguments().get("Context");
        }*/

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_settings, container, false);
        View rootView = inflater.inflate(R.layout.fragment_reports, container, false);

        listView = (ListView) rootView.findViewById(R.id.listViewReports);
        final SettingItem[] list_values = new SettingItem[1];
        list_values[0] = new SettingItem("Payment Transactions", false, null, 101);
        adapter = new CustomAdapter(getContext(),R.layout.reports_list_item,R.id.txtReportsItem, list_values);
        listView.setAdapter(adapter);
        //setListAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SettingItem s_item = (SettingItem) adapter.getItem(position);
                switch (s_item.getCode()) {
                    case 101:
                        //replace the toast message with action
                        Toast.makeText(getContext(),"Still in process",Toast.LENGTH_LONG).show();
                        break;
                }

            }
        });

        return rootView;
    }
}
