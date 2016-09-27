package navigation.itnav.com.terminal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import BO.Config;
import DAL.ConfigDAO;

public class PaypalSetUpActivity extends AppCompatActivity {

    EditText edtPaypalEmail;
    Button btnCancel, btnSave;
    ConfigDAO configDAO;
    Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_set_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configDAO = new ConfigDAO(PaypalSetUpActivity.this);
        config = configDAO.getConfigById(1);
        edtPaypalEmail = (EditText) findViewById(R.id.edtxtPaypalEmail);
        btnSave = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        if(config != null){
            edtPaypalEmail.setText(config.getPaypal_email());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPaypalEmail.getText() != null){
                    String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
                    if(edtPaypalEmail.getText().toString().matches(regex)){
                        config.setPaypal_email(edtPaypalEmail.getText().toString());
                        if(configDAO.updateConfig(config)){
                            Toast.makeText(PaypalSetUpActivity.this, "Paypal email saved successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        else {
                            Toast.makeText(PaypalSetUpActivity.this, "An error occured while saving paypal email", Toast.LENGTH_LONG).show();
                        }

                    }
                    else {
                        Toast.makeText(PaypalSetUpActivity.this, "Please enter a valid email address", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(PaypalSetUpActivity.this, "Please enter an email address", Toast.LENGTH_LONG).show();
                }
            }
        });
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
