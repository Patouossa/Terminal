package navigation.itnav.com.terminal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class LanguageActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private ListView listView;
    final Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.listViewLanguage);
        final SettingItem[] list_values = new SettingItem[2];
        list_values[0] = new SettingItem("English (United Kingdom)", false, null, 101);
        list_values[1] = new SettingItem("German", false, null, 102);
        adapter = new CustomAdapter(getBaseContext(),R.layout.settings_list_item,R.id.txtSettingsItem, list_values);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_language, menu);
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
