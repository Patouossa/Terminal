package BillWebServiceAccess;

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
public class UpdateBill  extends AsyncTask<String, Void, String> {
    private Context context;
    // Progress Dialog
    private ProgressDialog pDialog;
    public UpdateBill(Context context) {
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
        String amount = arg0[1];
        String note = arg0[2];
        String status = arg0[3];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?amount=" + URLEncoder.encode(amount, "UTF-8");
            data += "&note=" + URLEncoder.encode(note, "UTF-8");
            data += "&status=" + URLEncoder.encode(status, "UTF-8");
            data += "&id=" + URLEncoder.encode(id, "UTF-8");

            link = "http://192.168.173.1:82/terminal/Bill/update_bill.php" + data;
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

