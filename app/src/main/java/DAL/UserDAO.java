package DAL;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import BO.Role;
import BO.User;
import RoleWebServiceAccess.DeleteRole;
import RoleWebServiceAccess.GetRoleById;
import RoleWebServiceAccess.GetRoles;
import RoleWebServiceAccess.InsertRole;
import RoleWebServiceAccess.UpdateRole;
import UserWebServiceAccess.DeleteUser;
import UserWebServiceAccess.GetUserById;
import UserWebServiceAccess.GetUsers;
import UserWebServiceAccess.InsertUser;
import UserWebServiceAccess.LoginUser;
import UserWebServiceAccess.UpdateUser;

/**
 * Created by IBRAHIM on 24/09/2016.
 */
public class UserDAO {

    Context context;
    public UserDAO(Context context){
        this.context = context;
    }

    public boolean insertUser(User user){
        if(user != null){
            try{
                String result = new InsertUser(context).execute(user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getSex(), user.getAddress(), Integer.toString(user.getIsWaiter()), Integer.toString(user.getIdRole()), Integer.toString(user.getInsertUser()), Integer.toString(user.getEditUser())).get();
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
                        Log.e("PaymentTerminal", ex.getMessage() + " | "+ result);
                    }
                }
            }
            catch (InterruptedException ex){
                Log.e("PaymentTerminal", ex.getMessage());
                ex.printStackTrace();
            }
            catch (ExecutionException ex){
                Log.e("PaymentTerminal", ex.getMessage());
                ex.printStackTrace();
            }
            return false;
        }
        else {
            return false;
        }
    }

    public boolean updateUser(User user){
        if(user != null){
            try{
                String result = new UpdateUser(context).execute(Integer.toString(user.getId()), user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getSex(), user.getAddress(), Integer.toString(user.getIsWaiter()), Integer.toString(user.getIdRole()), Integer.toString(user.getEditUser())).get();
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

    public boolean deleteUser(int id){
        if(id > 0){
            try{
                String result = new DeleteUser(context).execute(Integer.toString(id)).get();
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

    public User getUserById(int id){
        if(id > 0){
            try{
                String result = new GetUserById(context).execute(Integer.toString(id)).get();
                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        String query_result = jsonObj.getString("status");
                        if (query_result.equals("OK")) {
                            JSONObject data = jsonObj.getJSONObject("data");
                            String[] date = data.getString("insertDate").split("-");
                            String[] date2 = data.getString("editDate").split("-");
                            Date date_ = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                            Date date2_ = new Date(Integer.parseInt(date2[0]), Integer.parseInt(date2[1]), Integer.parseInt(date2[2]));
                            User user = new User(data.getInt("id"), data.getString("username"), data.getString("password"), data.getString("firstname"), data.getString("lastname"), data.getString("sex"), data.getString("address"), data.getInt("isWaiter"), data.getInt("idRole"), data.getInt("insertUser"), data.getInt("editUser"), date_, date2_);
                            return user;
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

    public User[] getUsers(){
        try{
            String result = new GetUsers(context).execute().get();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    String query_result = jsonObj.getString("status");
                    if (query_result.equals("OK")) {
                        JSONArray datas = jsonObj.getJSONArray("data");
                        User[] users = new User[datas.length()];
                        for(int i =0; i< datas.length(); i++){
                            JSONObject data = datas.getJSONObject(i);
                            String[] date = data.getString("insertDate").split("-");
                            String[] date2 = data.getString("editDate").split("-");
                            Date date_ = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                            Date date2_ = new Date(Integer.parseInt(date2[0]), Integer.parseInt(date2[1]), Integer.parseInt(date2[2]));
                            User user = new User(data.getInt("id"), data.getString("username"), data.getString("password"), data.getString("firstname"), data.getString("lastname"), data.getString("sex"), data.getString("address"), data.getInt("isWaiter"), data.getInt("idRole"), data.getInt("insertUser"), data.getInt("editUser"), date_, date2_);
                            users[i] = user;
                        }

                        return users;
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

    public User loginUser(String username, String password){
        if(username != "" && password != ""){
            try{
                String result = new LoginUser(context).execute(username, password).get();
                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        String query_result = jsonObj.getString("status");
                        if (query_result.equals("OK")) {
                            JSONObject data = jsonObj.getJSONObject("data");
                            String[] date = data.getString("insertDate").split("-");
                            String[] date2 = data.getString("editDate").split("-");
                            Date date_ = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                            Date date2_ = new Date(Integer.parseInt(date2[0]), Integer.parseInt(date2[1]), Integer.parseInt(date2[2]));
                            User user = new User(data.getInt("id"), data.getString("username"), data.getString("password"), data.getString("firstname"), data.getString("lastname"), data.getString("sex"), data.getString("address"), data.getInt("isWaiter"), data.getInt("idRole"), data.getInt("insertUser"), data.getInt("editUser"), date_, date2_);
                            return user;
                        } else if (query_result.equals("Error")) {
                            Log.e("PaymentTerminal", jsonObj.getString("error"));
                            return null;
                        }
                    }
                    catch (JSONException ex){
                        ex.printStackTrace();
                        Log.e("PaymentTerminal", ex.getMessage() + " | "+ result);
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
}
