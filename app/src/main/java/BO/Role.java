package BO;

import java.io.Serializable;

/**
 * Created by IBRAHIM on 23/09/2016.
 */
public class Role implements Serializable {
    private int id;
    private String name;

    public Role(){

    }

    public Role(int id, String name){
        this.id = id;
        this.name = name;
    }
    public Role(String name){
        this.id = 0;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
