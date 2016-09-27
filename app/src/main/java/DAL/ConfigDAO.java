package DAL;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import BO.Config;
import BO.Role;
import ConfigWebServiceAccess.DeleteConfig;
import ConfigWebServiceAccess.GetConfigById;
import ConfigWebServiceAccess.GetConfigs;
import ConfigWebServiceAccess.InsertConfig;
import ConfigWebServiceAccess.UpdateConfig;
import RoleWebServiceAccess.DeleteRole;
import RoleWebServiceAccess.GetRoleById;
import RoleWebServiceAccess.GetRoles;
import RoleWebServiceAccess.InsertRole;
import RoleWebServiceAccess.UpdateRole;

/**
 * Created by IBRAHIM on 24/09/2016.
 */
public class ConfigDAO  {

    Context context;
    public ConfigDAO(Context context){
        this.context = context;
    }

    public boolean insertConfig(Config config){
        if(config != null){
            try{
                String result = new InsertConfig(context).execute(config.getLanguage(), config.getCurrency(), Double.toString(config.getTaxRate()), Integer.toString(config.getEnablePin()), config.getPaypal_email()).get();
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

    public boolean updateConfig(Config config){
        if(config != null){
            try{
                String result = new UpdateConfig(context).execute(Integer.toString(config.getId()), config.getLanguage(), config.getCurrency(), Double.toString(config.getTaxRate()), Integer.toString(config.getEnablePin()), config.getPaypal_email()).get();
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
                        Log.e("PaymentTerminal", ex.getMessage() + " | " + result);
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

    public boolean deleteConfig(int id){
        if(id > 0){
            try{
                String result = new DeleteConfig(context).execute(Integer.toString(id)).get();
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

    public Config getConfigById(int id){
        if(id > 0){
            try{
                String result = new GetConfigById(context).execute(Integer.toString(id)).get();
                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        String query_result = jsonObj.getString("status");
                        if (query_result.equals("OK")) {
                            JSONObject data = jsonObj.getJSONObject("data");
                            Config config = new Config(data.getInt("id"), data.getString("language"), data.getString("currency"), data.getDouble("taxRate"), data.getInt("enablePin"), data.getString("paypal_email"));
                            return config;
                        } else if (query_result.equals("Error")) {
                            Log.e("PaymentTerminal", jsonObj.getString("error"));
                            return null;
                        }
                    }
                    catch (JSONException ex){
                        ex.printStackTrace();
                        Log.e("PaymentTerminal", ex.getMessage() + " | " + result);
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

    public Config[] getConfigs(){
        try{
            String result = new GetConfigs(context).execute().get();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    String query_result = jsonObj.getString("status");
                    if (query_result.equals("OK")) {
                        JSONArray datas = jsonObj.getJSONArray("data");
                        Config[] configs = new Config[datas.length()];
                        for(int i =0; i< datas.length(); i++){
                            JSONObject data = datas.getJSONObject(i);
                            Config config = new Config(data.getInt("id"), data.getString("language"), data.getString("currency"), data.getDouble("taxRate"), data.getInt("enablePin"), data.getString("paypal_email"));
                            configs[i] = config;
                        }

                        return configs;
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
