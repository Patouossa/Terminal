package navigation.itnav.com.terminal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import BO.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    User user;
    private UserCustomAdapter adapter;
    private ListView listView;


    public UsersFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);

        listView = (ListView) rootView.findViewById(R.id.listViewUsers);
        final UserItem[] list_values = new UserItem[2];
        list_values[0] = new UserItem("Create User", false, null, 101);
        list_values[1] = new UserItem("View Users", false, null, 102);
        adapter = new UserCustomAdapter(getContext(),R.layout.users_list_item,R.id.txtUsersItem, list_values);
        listView.setAdapter(adapter);
        //setListAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserItem u_item = (UserItem) adapter.getItem(position);
                Intent i;
                switch (u_item.getCode()){
                    case 101:
                        i = new Intent(getContext(), CreateUserActivity.class);
                        i.putExtra("User", user);
                        startActivityForResult(i, 102);
                        break;
                    case 102:
                        i = new Intent(getContext(), ViewUsersActivity.class);
                        startActivity(i);
                        break;
                }

            }
        });

        return rootView;
    }

}
