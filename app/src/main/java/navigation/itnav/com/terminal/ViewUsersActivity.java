package navigation.itnav.com.terminal;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import BO.User;
import DAL.UserDAO;

public class ViewUsersActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private ListView listView;
    private TextView editBtn;
    private TextView deleteBtn;
    private User[] users = null;
    final Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.listViewLanguage);
        loadUser();

    }

    public void loadUser(){
        UserDAO userDAO = new UserDAO(ViewUsersActivity.this);
        users = userDAO.getUsers();
        if(users != null && users.length > 0){
            final SettingItem[] list_values = new SettingItem[users.length];
            for (int i = 0; i < users.length; i++){
                list_values[i] = new SettingItem(users[i].getUsername(), false, null, users[i].getId());
            }
            adapter = new CustomAdapter(getBaseContext(),R.layout.settings_list_item,R.id.txtSettingsItem, list_values);
            listView.setAdapter(adapter);
            listView.setOnItemLongClickListener(optionListener);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_language, menu);
        return true;
    }

    private AdapterView.OnItemLongClickListener optionListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {

            final SettingItem itemSelected = adapter.getItem(i);

            //create an instance for dialog box
            final Dialog dialog = new Dialog(ViewUsersActivity.this);
            dialog.setTitle(itemSelected.getText());

            //set layout for dialog
            dialog.setContentView(R.layout.users_layout_options_dialog);
            editBtn = (TextView)dialog.findViewById(R.id.userEditOpt);
            deleteBtn = (TextView) dialog.findViewById(R.id.userDeleteOpt);

            editBtn.setOnClickListener(new View.OnClickListener() {
                Intent i;
                @Override
                public void onClick(View view) {

                    i = new Intent(ViewUsersActivity.this,EditUserActivity.class);
                    int id = itemSelected.getCode();
                    i.putExtra("Id", id);
                    startActivity(i);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final Dialog lg = new Dialog(ViewUsersActivity.this);
                    lg.setContentView(R.layout.users_layout_long_click_dialog);
                    lg.setTitle(itemSelected.getText());

                    TextView cancelBtn = (TextView)lg.findViewById(R.id.userBtnNo);
                    TextView yesBtn = (TextView)lg.findViewById(R.id.userBtnYes);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lg.dismiss();
                        }
                    });
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int id = itemSelected.getCode();
                            UserDAO userDAO = new UserDAO(ViewUsersActivity.this);
                            try {
                                if(!userDAO.deleteUser(id)){
                                    Toast.makeText(ViewUsersActivity.this,"An error occured during update", Toast.LENGTH_SHORT).show();
                                    lg.cancel();
                                }
                                else{
                                    loadUser();
                                    lg.cancel();
                                    dialog.cancel();
                                }

                            }catch (Exception e){
                                Toast.makeText(ViewUsersActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    lg.show();
                }
            });
            dialog.show();

            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
