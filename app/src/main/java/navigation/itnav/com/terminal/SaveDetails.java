package navigation.itnav.com.terminal;

/**
 * Created by IBRAHIM on 23/09/2016.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by IBRAHIM on 23/09/2016.
 */
public class SaveDetails  extends AsyncTask<String, Void, String> {
    private Context context;
    // Progress Dialog
    private ProgressDialog pDialog;
    private EditText ed_firt_name ;
    private EditText ed_last_name ;
    private EditText ed_card_number;
    private EditText ed_exp_month;
    private EditText ed_exp_year ;
    private EditText ed_cvv ;
    public SaveDetails(Context context, EditText ed_firt_name, EditText ed_last_name, EditText ed_card_number, EditText ed_exp_month, EditText ed_exp_year, EditText ed_cvv) {
        this.context = context;
        this.ed_card_number = ed_card_number;
        this.ed_cvv = ed_cvv;
        this.ed_exp_month = ed_exp_month;
        this.ed_exp_year = ed_exp_year;
        this.ed_firt_name = ed_firt_name;
        this.ed_last_name = ed_last_name;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Saving data...Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... arg0) {
        String first_name = arg0[0];
        String last_name = arg0[1];
        String card_number = arg0[2];
        String month = arg0[3];
        String year = arg0[4];
        String cvv = arg0[5];
        String card_type = arg0[6];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?first_name=" + URLEncoder.encode(first_name, "UTF-8");
            data += "&last_name=" + URLEncoder.encode(last_name, "UTF-8");
            data += "&card_number=" + URLEncoder.encode(card_number, "UTF-8");
            data += "&exp_month=" + URLEncoder.encode(month, "UTF-8");
            data += "&exp_year=" + URLEncoder.encode(year, "UTF-8");
            data += "&cvv=" + URLEncoder.encode(cvv, "UTF-8");
            data += "&card_type=" + URLEncoder.encode(card_type, "UTF-8");

            link = "http://192.168.173.1:82/terminal/save_card_details.php" + data;
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
        /*String jsonStr = result;
        //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("status");
                if (query_result.equals("OK")) {
                    //JSONArray data = jsonObj.getJSONArray("data");
                    //String[] time = data.getString(1).split(":");
                    //QrData qrdata = new QrData(data.getInt(0), new QrTime(new Time(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]))), data.getString(2).charAt(0), data.getInt(3), data.getBoolean(4));
                    ed_cvv.setText(null);
                    ed_card_number.setText(null);
                    ed_last_name.setText(null);
                    ed_firt_name.setText(null);
                    ed_exp_month.setText(null);
                    ed_exp_year.setText(null);
                    Intent i = new Intent(context, PaymentActivity.class);
                    context.startActivity(i);
                    Toast.makeText(context, "Data saved successfully.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("Error")) {
                    Toast.makeText(context, jsonObj.getString("error"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("JSON", e.getMessage() + " ||| " + jsonStr);
                Toast.makeText(context, "Error while parsing data."+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }*/
    }

}
