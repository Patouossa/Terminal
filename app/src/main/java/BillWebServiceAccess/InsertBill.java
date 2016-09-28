package BillWebServiceAccess;

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
public class InsertBill  extends AsyncTask<String, Void, String> {
    private Context context;
    // Progress Dialog
    private ProgressDialog pDialog;
    public InsertBill(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Inserting Data...Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... arg0) {
        String amount = arg0[0];
        String note = arg0[1];
        String status = arg0[2];
        String insertUser = arg0[3];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?amount=" + URLEncoder.encode(amount, "UTF-8");
            data += "&note=" + URLEncoder.encode(note, "UTF-8");
            data += "&status=" + URLEncoder.encode(status, "UTF-8");
            data += "&insertUser=" + URLEncoder.encode(insertUser, "UTF-8");

            link = Util.BASE_URL + "Bill/insert_bill.php" + data;
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
