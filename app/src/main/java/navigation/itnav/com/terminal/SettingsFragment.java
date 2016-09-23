package navigation.itnav.com.terminal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@ link  SettingsFragment. OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CustomAdapter adapter;
    private ListView listView;
    //private Context ctx;

   // private OnFragmentInteractionListener mListener;

    /*public SettingsFragment(Context ctx) {
        // Required empty public constructor
        this.ctx = ctx;
    }*/

    public SettingsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        listView = (ListView) rootView.findViewById(R.id.listViewSettings);
        final SettingItem[] list_values = new SettingItem[6];
        list_values[0] = new SettingItem("Paypal Set up", false, null, 101);
        list_values[1] = new SettingItem("Language", false, null, 102);
        list_values[2] = new SettingItem("Currency", true, "Pop-up when language set", 103);
        list_values[3] = new SettingItem("Tax Rate", false, null, 104);
        list_values[4] = new SettingItem("Pin Settings", true, "Enter PIN during payment", 105);
        list_values[5] = new SettingItem("UsersFragment", false, null, 106);
        adapter = new CustomAdapter(getContext(),R.layout.settings_list_item,R.id.txtSettingsItem, list_values);
        listView.setAdapter(adapter);
        //setListAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SettingItem s_item = (SettingItem) adapter.getItem(position);
                Intent i;
                switch (s_item.getCode()){
                    case 101:
                        //setTargetFragment(new PaymentFragment(), 110);
                        i = new Intent(getContext(), PaypalSetUpActivity.class);
                        startActivityForResult(i, 102);
                        break;
                    case 102:
                        i = new Intent(getContext(), LanguageActivity.class);
                        startActivity(i);
                        break;
                }

            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

}
