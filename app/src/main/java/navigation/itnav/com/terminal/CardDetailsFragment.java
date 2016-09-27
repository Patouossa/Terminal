package navigation.itnav.com.terminal;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardDetailsFragment extends Fragment {

    private Context ctx = getContext();

    public CardDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_card_details, container, false);

        Button btnSave = (Button) rootView.findViewById(R.id.btnSaveCardDetails);
        final EditText ed_firt_name = (EditText) rootView.findViewById(R.id.edTxtFirstName);
        final EditText ed_last_name = (EditText) rootView.findViewById(R.id.edTxtLastName);
        final EditText ed_card_number = (EditText) rootView.findViewById(R.id.edTxtCardNumber);
        final EditText ed_exp_month = (EditText) rootView.findViewById(R.id.edTxtExpireryMonth);
        final EditText ed_exp_year = (EditText) rootView.findViewById(R.id.edTxtExpireryYear);
        final EditText ed_cvv = (EditText) rootView.findViewById(R.id.edTxtCVV);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_card_number.getText() != null && ed_cvv.getText() != null && ed_exp_month.getText() != null && ed_exp_year.getText() != null && ed_firt_name.getText() != null && ed_last_name.getText() != null) {
                    String first_name = ed_firt_name.getText().toString();
                    String last_name = ed_last_name.getText().toString();
                    String c_n = ed_card_number.getText().toString();
                    //int card_number = Integer.parseInt(c_n);
                    int exp_month = Integer.parseInt(ed_exp_month.getText().toString());
                    int exp_year = Integer.parseInt(ed_exp_year.getText().toString());
                    int cvv = Integer.parseInt(ed_cvv.getText().toString());
                    int errors = 0;
                    String[] card_types_names = {"Visa", "MasterCard", "Amex", "Discover", "Diners", "Diners"};
                    String[] card_types = new String[6];
                    //visa
                    card_types[0] = "^4\\d{12}(\\d\\d\\d){0,1}$";
                    //mastercard
                    card_types[1] = "^5[12345]\\d{14}$";
                    //amex
                    card_types[2] = "^3[47]\\d{13}$";
                    //discover
                    card_types[3] = "^6011\\d{12}$";
                    //diners
                    card_types[4] = "^30[012345]\\d{11}$";
                    //diners
                    card_types[5] = "^3[68]\\d{12}$";
                    String card_type = "None";
                    for (int i = 0; i < 6; i++) {
                        if (ed_card_number.getText().toString().matches(card_types[i])) {
                            card_type = card_types_names[i];
                        }
                    }
                    if (card_type.equalsIgnoreCase("None")) {
                        Toast.makeText(getContext(), "Invalid card number", Toast.LENGTH_LONG).show();
                        errors++;
                    } else {
                        String reverse_card_number = reverseString(ed_card_number.getText().toString());
                        int checksum = 0;
                        //Toast.makeText(MainActivity.this, "Reverse card number : "+ reverse_card_number, Toast.LENGTH_LONG).show();
                        for (int i = 0; i < reverse_card_number.length(); i++) {
                            int current_number = Integer.parseInt(Character.toString(reverse_card_number.charAt(i)));
                            if (i % 2 != 0) {
                                current_number *= 2;
                            }
                            checksum += current_number % 10;
                            if (current_number > 9) {
                                checksum += 1;
                            }
                        }
                        if (checksum % 10 == 0) {
                            //Toast.makeText(MainActivity.this, "Valid card number", Toast.LENGTH_LONG).show();
                            if (exp_month > 12) {
                                Toast.makeText(getContext(), "Expirery month must be between 1 and 12", Toast.LENGTH_LONG).show();
                                errors++;
                            } else if (ed_exp_year.getText().toString().length() < 4) {
                                Toast.makeText(getContext(), "Expirery year must be 4 numbers", Toast.LENGTH_LONG).show();
                                errors++;
                            } else if (ed_cvv.getText().toString().length() < 3) {
                                Toast.makeText(ctx, "CVV number must be at least 3 numbers", Toast.LENGTH_LONG).show();
                                errors++;
                            }
                            if (errors == 0) {
                                try{
                                    String result =  new SaveDetails(getActivity(), ed_firt_name, ed_last_name, ed_card_number, ed_exp_month, ed_exp_year, ed_cvv).execute(first_name, last_name, c_n, Integer.toString(exp_month), Integer.toString(exp_year), Integer.toString(cvv), card_type).get();
                                    String jsonStr = result;
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
                                                Intent i = new Intent(getActivity(), PaymentActivity.class);
                                                getActivity().startActivity(i);
                                                Toast.makeText(getActivity(), "Data saved successfully.", Toast.LENGTH_SHORT).show();
                                            } else if (query_result.equals("Error")) {
                                                Toast.makeText(getActivity(), jsonObj.getString("error"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d("JSON", e.getMessage() + " ||| " + jsonStr);
                                            Toast.makeText(getActivity(), "Error while parsing data."+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (InterruptedException ex){

                                }
                                catch (ExecutionException ex){

                                }
                            }
                        } else {
                            Toast.makeText(ctx, "Invalid card number", Toast.LENGTH_LONG).show();
                            errors++;
                        }

                    }


                } else {
                    Toast.makeText(ctx, "Please fill in all fields", Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }
    public String reverseString(String string){
        String revstring = "";
        for(int i=string.length(); i == 0; i--){
            revstring += string.charAt(i);
        }
        return revstring;
    }

}
