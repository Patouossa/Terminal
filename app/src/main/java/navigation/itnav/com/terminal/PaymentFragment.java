package navigation.itnav.com.terminal;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.magnetic.MagneticCard;


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

        edt = (EditText)rootView.findViewById(R.id.edt_payment_amount);
        btnOk = (Button) rootView.findViewById(R.id.btnOkPay);
        tv = (TextView) rootView.findViewById(R.id.txtStripe);

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
            MagneticCard.open();
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
                        amount = Double.parseDouble(edt.getText().toString());
                        readThread = new ReadThread();
                        readThread.start();
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
