package UserWebServiceAccess;

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
public class UpdateUser extends AsyncTask<String, Void, String> {
    private Context context;
    // Progress Dialog
    private ProgressDialog pDialog;
    public UpdateUser(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Updating Data...Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        //pDialog.show();
    }

    @Override
    protected String doInBackground(String... arg0) {
        String id = arg0[0];
        String username = arg0[1];
        String password = arg0[2];
        String firstname = arg0[3];
        String lastname = arg0[4];
        String sex = arg0[5];
        String address = arg0[6];
        String isWaiter = arg0[7];
        String idRole = arg0[8];
        String editUser = arg0[9];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?username=" + URLEncoder.encode(username, "UTF-8");
            data += "&password=" + URLEncoder.encode(password, "UTF-8");
            data += "&firstname=" + URLEncoder.encode(firstname, "UTF-8");
            data += "&lastname=" + URLEncoder.encode(lastname, "UTF-8");
            data += "&sex=" + URLEncoder.encode(sex, "UTF-8");
            data += "&address=" + URLEncoder.encode(address, "UTF-8");
            data += "&isWaiter=" + URLEncoder.encode(isWaiter, "UTF-8");
            data += "&idRole=" + URLEncoder.encode(idRole, "UTF-8");
            data += "&editUser=" + URLEncoder.encode(editUser, "UTF-8");
            data += "&id=" + URLEncoder.encode(id, "UTF-8");

            link = "http://192.168.173.1:82/terminal/User/update_user.php" + data;
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


