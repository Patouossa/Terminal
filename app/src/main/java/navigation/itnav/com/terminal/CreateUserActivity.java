package navigation.itnav.com.terminal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import BO.Role;
import BO.User;
import DAL.RoleDAO;
import DAL.UserDAO;

public class CreateUserActivity extends AppCompatActivity {
    Button setRoleBtn, saveBtn;
    EditText name, password, confirm_password;
    Role role = null;
    Role[] roles = null;
    private ProgressDialog pDialog;
    User connectedUser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        connectedUser = (User) getIntent().getExtras().get("User");
        setRoleBtn = (Button) findViewById(R.id.btnSetRole);
        saveBtn = (Button) findViewById(R.id.btnOk);
        //setting onclick listener for set role button
        setRoleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create an instance for dialog box
                final Dialog dialog = new Dialog(CreateUserActivity.this);
                dialog.setTitle("User Role");

                AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserActivity.this) ;
                builder.setMessage("User Role") ;
                //set layout for dialog
                final LinearLayout popup = new LinearLayout(getBaseContext());
                popup.setPadding(15,15,15,15);
                RoleDAO roleDAO = new RoleDAO(CreateUserActivity.this);
                if(roles == null){
                    roles = roleDAO.getRoles();
                }

                final RadioGroup radioGroup = new RadioGroup(getBaseContext());
                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                radioGroup.setLayoutParams(layoutParams);
                RadioGroup.LayoutParams layoutParams2 = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if(roles.length > 0 ){
                    for (int i =0; i < roles.length; i++){
                        RadioButton radioButton = new RadioButton(getBaseContext());
                        radioButton.setLayoutParams(layoutParams2);
                        radioButton.setText(roles[i].getName());
                        //radioButton.setId();
                        radioButton.setTextColor(Color.BLACK);
                        radioButton.setTag(roles[i]);
                        radioGroup.addView(radioButton);
                        if(role != null && role.getId() == roles[i].getId()){
                            radioButton.setChecked(true);
                        }
                    }
                }
                popup.addView(radioGroup);
                builder.setView(popup);
                /*radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_LONG).show();
                    }
                });*/
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if(radioGroup)
                        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                        //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_LONG).show();
                        if(radioButton != null){
                            role = (Role) radioButton.getTag();
                            setRoleBtn.setText("SET ROLE ("+ role.getName() +")");
                        }
                        else {
                            dialog.cancel();
                        }

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        arg0.cancel();
                    }
                }) ;
                builder.create().show() ;
                //dialog.setContentView(popup);
                /*RadioButton rb1 = (RadioButton)findViewById(R.id.userRadioBtn);
                RadioButton rb2 = (RadioButton)findViewById(R.id.adminRadioBtn);
                RadioButton rb3 = (RadioButton)findViewById(R.id.superAdminRadioBtn);*/

                //dialog.show();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new ProgressDialog(CreateUserActivity.this);
                pDialog.setMessage("Saving data...Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
                name = (EditText) findViewById(R.id.edtxtUserName);
                password = (EditText) findViewById(R.id.edtxtUserPass);
                confirm_password = (EditText) findViewById(R.id.edtxtUserConfirmPass);
                if(name != null && password != null && confirm_password != null && role != null){
                    if(password.length() < 8){
                        if(password.getText().toString().equals(confirm_password.getText().toString())){
                            UserDAO userDAO = new UserDAO(CreateUserActivity.this);
                            User user = new User(name.getText().toString(), password.getText().toString(), "" , "", "", "", 0, role.getId(), connectedUser.getId(), connectedUser.getId(), null, null);
                            if(userDAO.insertUser(user)){
                                pDialog.dismiss();
                                Intent i = new Intent(CreateUserActivity.this, ViewUsersActivity.class);
                                i.putExtra("User", connectedUser);
                                startActivity(i);
                            }
                            else{
                                pDialog.dismiss();
                                Toast.makeText(getBaseContext(), "An error occured during the creation of the user.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            pDialog.dismiss();
                            Toast.makeText(getBaseContext(), "Passwords dosn't match", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getBaseContext(), "Password length must be at least 8 caracters.", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    pDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public EditText getName() {
        return name;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paypal_set_up, menu);
        return true;
    }

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
