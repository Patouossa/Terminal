package ConfigWebServiceAccess;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by IBRAHIM on 24/09/2016.
 */
public class UpdateConfig  extends AsyncTask<String, Void, String> {
    private Context context;
    // Progress Dialog
    private ProgressDialog pDialog;
    public UpdateConfig(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Updating Data...Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... arg0) {
        String id = arg0[0];
        String language = arg0[1];
        String currency = arg0[2];
        String taxRate = arg0[3];
        String enablePin = arg0[4];
        String paypa_email = arg0[5];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?language=" + URLEncoder.encode(language, "UTF-8");
            data += "&currency=" + URLEncoder.encode(currency, "UTF-8");
            data += "&taxRate=" + URLEncoder.encode(taxRate, "UTF-8");
            data += "&enablePin=" + URLEncoder.encode(enablePin, "UTF-8");
            data += "&id=" + URLEncoder.encode(id, "UTF-8");
            data += "&paypal_email=" + URLEncoder.encode(paypa_email, "UTF-8");

            link = "http://192.168.173.1:82/terminal/Config/update_config.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // dismiss the dialog after getting all products
        pDialog.dismiss();

    }
}
