package navigation.itnav.com.terminal;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.magnetic.MagneticCard;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.Date;

import BO.Bill;
import BO.User;
import DAL.BillDAO;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {
    EditText edt;
    Button btnOk;
    double amount;
    Handler handler;
    Thread readThread;
    TextView tv;

    /***
     * Added by Armand Ndjock - 27/09/2016
     * Start
     */
    Context c;
    User user;
    private PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(
            "ASAk9rZ6e_kG-uAhA75hNgRRYMArkPfNqsyWMhTzabqXcTFdaGfu6pffIX9kQbtZaTlBp8Xv9t_FPcyv"
    );
    private final static String CURRENCY = "EUR";

    /**
     * End
     */

    public PaymentFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        c = getActivity();
        edt = (EditText)rootView.findViewById(R.id.edt_payment_amount);
        btnOk = (Button) rootView.findViewById(R.id.btnOkPay);
        tv = (TextView) rootView.findViewById(R.id.txtStripe);

        /**
         * Added by Armand - 27/09/2016 - Get the user data
         */
        Bundle b = getArguments();
        user = (User) b.getSerializable("user");
        /**
         * End
         */

        handler = new Handler()
        {

            @Override
            public void handleMessage(Message msg)
            {
                String[] TracData = (String[])msg.obj;
                for(int i=0; i<3; i++){
                    if(TracData[i] != null){
                        switch (i)
                        {
                            case 0:
                                //Toast.makeText(getContext(),TracData[i], Toast.LENGTH_LONG).show();
                                //editText1.setText(TracData[i]);
                                break;
                            case 1:
                                String data = TracData[i].substring(1, 17);
                                Toast.makeText(getContext(),data, Toast.LENGTH_LONG).show();
                                //editText2.setText(TracData[i]);
                                break;
                            case 2:
                                Toast.makeText(getContext(),TracData[i], Toast.LENGTH_LONG).show();
                                //editText3.setText(TracData[i]);
                                break;
                        }

                    }
                }
            }

        };
        try {
            /*MagneticCard.open();*/
        } catch (Exception e) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("Error");
            alertDialog.setMessage("An error occured while opening the magnetic stripe : "+ e.getMessage());
            alertDialog.setPositiveButton("Okay",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt.getText() != null && edt.getText().toString() != "") {
                    try{

                        tv.setTextColor(Color.WHITE);
                        tv.setText("Please stripe the credit card to continue");

                        /**
                         * Added by Armand Ndjock - 27/09/2016
                         * Start
                         */
                        amount = Double.parseDouble(edt.getText().toString().trim());
                        PayPalPayment payment = new PayPalPayment(new BigDecimal(amount), CURRENCY, "Order", PayPalPayment.PAYMENT_INTENT_SALE);
                        Intent i = new Intent(c, com.paypal.android.sdk.payments.PaymentActivity.class);
                        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                        i.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payment);
                        startActivityForResult(i, 0);
                        /**
                         * End
                         */

                        /*readThread = new ReadThread();
                        readThread.start();*/
                    }catch (NumberFormatException e){
                        tv.setTextColor(Color.WHITE);
                        tv.setText("");
                        Toast.makeText(getContext(), "Please enter a valid amount !", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    tv.setTextColor(Color.WHITE);
                    tv.setText("");
                    Toast.makeText(getContext(), "Please enter the amount !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    /**
     * Added by Armand - 27/09/2016 - Handling result coming from Paypal-PaymentActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        BillDAO dao = new BillDAO(c);
        Bill bill = new Bill();
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("PayPalPayment", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.
                    // Maybe Ibrahim will add something there
                    // For now I save the bill

                    bill.setAmount(amount);
                    bill.setInsertDate(new Date());
                    bill.setInsertUser(user.getId());
                    bill.setNote(confirm.toJSONObject().toString(4));
                    bill.setStatus("Payment successfully completed");

                    boolean insertedOnServer = dao.insertBill(bill);

                    Toast.makeText(c, "Payment successfully completed - "+insertedOnServer, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Log.e("PayPalPayment", "an extremely unlikely failure occurred: ", e);
                    Toast.makeText(c, "An extremely unlikely failure occurred", Toast.LENGTH_LONG).show();
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("PayPalPayment", "The user canceled.");
            Toast.makeText(c, "Payment canceled by the user", Toast.LENGTH_LONG).show();
        }
        else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("PayPalPayment", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            Toast.makeText(c, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.", Toast.LENGTH_LONG).show();
            bill.setAmount(amount);
            bill.setInsertDate(new Date());
            bill.setInsertUser(user.getId());
            bill.setNote("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            bill.setStatus("Payment failed");
        }
    }

    /*@Override
    public void onDestroy() {

        if (readThread != null)
        {
            readThread.interrupt();
        }
        MagneticCard.close();
        super.onDestroy();

    }*/

    private class ReadThread extends Thread
    {
        String[] TracData = null;

        @Override
        public void run()
        {
            MagneticCard.startReading();
            while (!Thread.interrupted()){
                try{
                    TracData = MagneticCard.check(1000);
                    handler.sendMessage(handler.obtainMessage(1, TracData));
                    MagneticCard.startReading();
                }catch (TelpoException e){
                }
            }
        }

    }
}
