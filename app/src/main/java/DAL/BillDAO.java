package DAL;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import BO.Bill;
import BO.Role;
import BillWebServiceAccess.DeleteBill;
import BillWebServiceAccess.GetBillById;
import BillWebServiceAccess.GetBills;
import BillWebServiceAccess.InsertBill;
import BillWebServiceAccess.UpdateBill;
import RoleWebServiceAccess.DeleteRole;
import RoleWebServiceAccess.GetRoleById;
import RoleWebServiceAccess.InsertRole;
import RoleWebServiceAccess.UpdateRole;

/**
 * Created by IBRAHIM on 24/09/2016.
 */
public class BillDAO {
    Context context;
    public BillDAO(Context context){
        this.context = context;
    }

    public boolean insertBill(Bill bill){
        if(bill != null){
            try{
                String result = new InsertBill(context).execute(Double.toString(bill.getAmount()), bill.getNote(), bill.getStatus(), Integer.toString(bill.getInsertUser())).get();
                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        String query_result = jsonObj.getString("status");
                        if (query_result.equals("OK")) {
                            return true;
                        } else if (query_result.equals("Error")) {
                            Log.e("PaymentTerminal", jsonObj.getString("error"));
                            return false;
                        }
                    }
                    catch (JSONException ex){
                        ex.printStackTrace();
                    }
                }
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
            catch (ExecutionException ex){
                ex.printStackTrace();
            }
            return false;
        }
        else {
            return false;
        }
    }

    public boolean updateBill(Bill bill){
        if(bill != null){
            try{
                String result = new UpdateBill(context).execute(Integer.toString(bill.getId()), Double.toString(bill.getAmount()), bill.getNote(), bill.getStatus(), Integer.toString(bill.getInsertUser())).get();
                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        String query_result = jsonObj.getString("status");
                        if (query_result.equals("OK")) {
                            return true;
                        } else if (query_result.equals("Error")) {
                            Log.e("PaymentTerminal", jsonObj.getString("error"));
                            return false;
                        }
                    }
                    catch (JSONException ex){
                        ex.printStackTrace();
                    }
                }
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
            catch (ExecutionException ex){
                ex.printStackTrace();
            }
            return false;
        }
        else {
            return false;
        }
    }

    public boolean deleteBill(int id){
        if(id > 0){
            try{
                String result = new DeleteBill(context).execute(Integer.toString(id)).get();
                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        String query_result = jsonObj.getString("status");
                        if (query_result.equals("OK")) {
                            return true;
                        } else if (query_result.equals("Error")) {
                            Log.e("PaymentTerminal", jsonObj.getString("error"));
                            return false;
                        }
                    }
                    catch (JSONException ex){
                        ex.printStackTrace();
                    }
                }
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
            catch (ExecutionException ex){
                ex.printStackTrace();
            }
            return false;
        }
        else {
            return false;
        }
    }

    public Bill getBillById(int id){
        if(id > 0){
            try{
                String result = new GetBillById(context).execute(Integer.toString(id)).get();
                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        String query_result = jsonObj.getString("status");
                        if (query_result.equals("OK")) {
                            JSONObject data = jsonObj.getJSONObject("data");
                            Bill bill = new Bill(data.getInt("id"), data.getDouble("amount"), data.getString("note"), data.getString("status"), data.getInt("insertUser") , new Date(data.getString("insertDate")));
                            return bill;
                        } else if (query_result.equals("Error")) {
                            Log.e("PaymentTerminal", jsonObj.getString("error"));
                            return null;
                        }
                    }
                    catch (JSONException ex){
                        ex.printStackTrace();
                    }
                }
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
            catch (ExecutionException ex){
                ex.printStackTrace();
            }
            return null;
        }
        else {
            return null;
        }
    }

    public Bill[] getBills(){
        try{
            String result = new GetBills(context).execute().get();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    String query_result = jsonObj.getString("status");
                    if (query_result.equals("OK")) {
                        JSONArray datas = jsonObj.getJSONArray("data");
                        Bill[] bills = new Bill[datas.length()];
                        for(int i =0; i< datas.length(); i++){
                            JSONObject data = datas.getJSONObject(i);
                            Bill bill = new Bill(data.getInt("id"), data.getDouble("amount"), data.getString("note"), data.getString("status"), data.getInt("insertUser") , new Date(data.getString("insertDate")));
                            bills[i] = bill;
                        }

                        return bills;
                    } else if (query_result.equals("Error")) {
                        Log.e("PaymentTerminal", jsonObj.getString("error"));
                        return null;
                    }
                }
                catch (JSONException ex){
                    ex.printStackTrace();
                }
            }
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
        catch (ExecutionException ex){
            ex.printStackTrace();
        }
        return null;

    }
}

