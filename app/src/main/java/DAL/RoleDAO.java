package DAL;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import BO.Role;
import RoleWebServiceAccess.DeleteRole;
import RoleWebServiceAccess.GetRoleById;
import RoleWebServiceAccess.GetRoles;
import RoleWebServiceAccess.InsertRole;
import RoleWebServiceAccess.UpdateRole;

/**
 * Created by IBRAHIM on 23/09/2016.
 */
public class RoleDAO {

    Context context;
    public RoleDAO(Context context){
        this.context = context;
    }

    public boolean insertRole(Role role){
        if(role != null){
            try{
                String result = new InsertRole(context).execute(role.getName()).get();
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

    public boolean updateRole(Role role){
        if(role != null){
            try{
                String result = new UpdateRole(context).execute(Integer.toString(role.getId()), role.getName()).get();
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

    public boolean deleteRole(int id){
        if(id > 0){
            try{
                String result = new DeleteRole(context).execute(Integer.toString(id)).get();
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

    public Role getRoleById(int id){
        if(id > 0){
            try{
                String result = new GetRoleById(context).execute(Integer.toString(id)).get();
                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        String query_result = jsonObj.getString("status");
                        if (query_result.equals("OK")) {
                            JSONObject data = jsonObj.getJSONObject("data");
                            Role role = new Role(data.getInt("id"), data.getString("name"));
                            return role;
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

    public Role[] getRoles(){
            try{
                String result = new GetRoles(context).execute().get();
                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        String query_result = jsonObj.getString("status");
                        if (query_result.equals("OK")) {
                            JSONArray datas = jsonObj.getJSONArray("data");
                            Role[] roles = new Role[datas.length()];
                            for(int i =0; i< datas.length(); i++){
                                JSONObject data = datas.getJSONObject(i);
                                Role role = new Role(data.getInt("id"), data.getString("name"));
                                roles[i] = role;
                            }

                            return roles;
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
