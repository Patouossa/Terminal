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

import BO.User;
import DAL.UserDAO;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsename, edtPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsename = (EditText) findViewById(R.id.edtLoginUserName);
        edtPassword = (EditText) findViewById(R.id.edtLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPassword.getText() != null && edtUsename.getText() != null){
                    UserDAO userDAO = new UserDAO(LoginActivity.this);
                    User user = userDAO.loginUser(edtUsename.getText().toString(), edtPassword.getText().toString());
                    if(user != null){
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("User", user);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(getBaseContext(), "No account corresponds to yours details", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getBaseContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
