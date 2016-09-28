package PrivilegeWebServiceAccess;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import util.Util;

/**
 * Created by IBRAHIM on 24/09/2016.
 */
public class UpdatePrivilege extends AsyncTask<String, Void, String> {
    private Context context;
    // Progress Dialog
    private ProgressDialog pDialog;
    public UpdatePrivilege(Context context) {
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
        String idRole = arg0[1];
        String idRight = arg0[2];
        String description = arg0[3];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?idRole=" + URLEncoder.encode(idRole, "UTF-8");
            data += "&idRight=" + URLEncoder.encode(idRight, "UTF-8");
            data += "&description=" + URLEncoder.encode(description, "UTF-8");
            data += "&id=" + URLEncoder.encode(id, "UTF-8");

            link = Util.BASE_URL + "Privilege/update_privilege.php" + data;
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
