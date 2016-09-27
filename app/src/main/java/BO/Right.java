package BO;

import java.io.Serializable;

/**
 * Created by IBRAHIM on 23/09/2016.
 */
public class Right implements Serializable {
    private int id;
    private String code, description;

    public Right(){

    }
    public Right(int id, String code, String description){
        this.id = id;
        this.description = description;
        this.code = code;
    }

    public Right(String code, String description){
        this.id = 0;
        this.description = description;
        this.code = code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
