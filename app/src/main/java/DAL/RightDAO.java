package DAL;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import BO.Right;
import BO.Role;
import RightWebServiceAccess.DeleteRight;
import RightWebServiceAccess.GetRightById;
import RightWebServiceAccess.GetRights;
import RightWebServiceAccess.InsertRight;
import RightWebServiceAccess.UpdateRight;
import RoleWebServiceAccess.DeleteRole;
import RoleWebServiceAccess.GetRoleById;
import RoleWebServiceAccess.GetRoles;
import RoleWebServiceAccess.InsertRole;
import RoleWebServiceAccess.UpdateRole;

/**
 * Created by IBRAHIM on 24/09/2016.
 */
public class RightDAO {

    Context context;
    public RightDAO(Context context){
        this.context = context;
    }

    public boolean insertRight(Right right){
        if(right != null){
            try{
                String result = new InsertRight(context).execute(right.getCode(), right.getDescription()).get();
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

    public boolean updateRight(Right right){
        if(right != null){
            try{
                String result = new UpdateRight(context).execute(Integer.toString(right.getId()), right.getCode(), right.getDescription()).get();
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

    public boolean deleteRight(int id){
        if(id > 0){
            try{
                String result = new DeleteRight(context).execute(Integer.toString(id)).get();
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

    public Right getRightById(int id){
        if(id > 0){
            try{
                String result = new GetRightById(context).execute(Integer.toString(id)).get();
                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        String query_result = jsonObj.getString("status");
                        if (query_result.equals("OK")) {
                            JSONObject data = jsonObj.getJSONObject("data");
                            Right right = new Right(data.getInt("id"), data.getString("code"), data.getString("description"));
                            return right;
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

    public Right[] getRights(){
        try{
            String result = new GetRights(context).execute().get();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    String query_result = jsonObj.getString("status");
                    if (query_result.equals("OK")) {
                        JSONArray datas = jsonObj.getJSONArray("data");
                        Right[] rights = new Right[datas.length()];
                        for(int i =0; i< datas.length(); i++){
                            JSONObject data = datas.getJSONObject(i);
                            Right right = new Right(data.getInt("id"), data.getString("code"), data.getString("description"));
                            rights[i] = right;
                        }

                        return rights;
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

