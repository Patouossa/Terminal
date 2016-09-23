package navigation.itnav.com.terminal;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateUserActivity extends AppCompatActivity {
    Button setRoleBtn;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setRoleBtn = (Button) findViewById(R.id.btnSetRole);

        //setting onclick listener for set role button
        setRoleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create an instance for dialog box
                final Dialog dialog = new Dialog(CreateUserActivity.this);
                dialog.setTitle("User Role");

                //set layout for dialog
                dialog.setContentView(R.layout.users_layout_set_role_dialog);
//                RadioButton rb1 = (RadioButton)findViewById(R.id.userRadioBtn);
//                RadioButton rb2 = (RadioButton)findViewById(R.id.adminRadioBtn);
//                RadioButton rb3 = (RadioButton)findViewById(R.id.superAdminRadioBtn);
                dialog.show();
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
