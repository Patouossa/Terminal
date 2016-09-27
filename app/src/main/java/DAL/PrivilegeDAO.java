package DAL;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import BO.Privilege;
import BO.Role;
import PrivilegeWebServiceAccess.DeletePrivilege;
import PrivilegeWebServiceAccess.GetPrivilegeById;
import PrivilegeWebServiceAccess.GetPrivileges;
import PrivilegeWebServiceAccess.InsertPrivilege;
import PrivilegeWebServiceAccess.UpdatePrivilege;
import RoleWebServiceAccess.DeleteRole;
import RoleWebServiceAccess.GetRoleById;
import RoleWebServiceAccess.GetRoles;
import RoleWebServiceAccess.InsertRole;
import RoleWebServiceAccess.UpdateRole;

/**
 * Created by IBRAHIM on 24/09/2016.
 */
public class PrivilegeDAO {

    Context context;
    public PrivilegeDAO(Context context){
        this.context = context;
    }

    public boolean insertPrivilege(Privilege privilege){
        if(privilege != null){
            try{
                String result = new InsertPrivilege(context).execute(Integer.toString(privilege.getIdRole()), Integer.toString(privilege.getIdRight()), privilege.getDescription()).get();
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

    public boolean updatePrivilege(Privilege privilege){
        if(privilege != null){
            try{
                String result = new UpdatePrivilege(context).execute(Integer.toString(privilege.getId()), Integer.toString(privilege.getIdRole()), Integer.toString(privilege.getIdRight()), privilege.getDescription()).get();
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

    public boolean deletePrivilege(int id){
        if(id > 0){
            try{
                String result = new DeletePrivilege(context).execute(Integer.toString(id)).get();
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

    public Privilege getPrivilegeById(int id){
        if(id > 0){
            try{
                String result = new GetPrivilegeById(context).execute(Integer.toString(id)).get();
                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        String query_result = jsonObj.getString("status");
                        if (query_result.equals("OK")) {
                            JSONObject data = jsonObj.getJSONObject("data");
                            Privilege privilege = new Privilege(data.getInt("id"), data.getInt("idRole"), data.getInt("idRight"), data.getString("description"));
                            return privilege;
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

    public Privilege[] getPrivileges(){
        try{
            String result = new GetPrivileges(context).execute().get();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    String query_result = jsonObj.getString("status");
                    if (query_result.equals("OK")) {
                        JSONArray datas = jsonObj.getJSONArray("data");
                        Privilege[] privileges = new Privilege[datas.length()];
                        for(int i =0; i< datas.length(); i++){
                            JSONObject data = datas.getJSONObject(i);
                            Privilege privilege = new Privilege(data.getInt("id"), data.getInt("idRole"), data.getInt("idRight"), data.getString("description"));
                            privileges[i] = privilege;
                        }

                        return privileges;
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
