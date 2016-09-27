package BO;

import java.io.Serializable;

/**
 * Created by IBRAHIM on 23/09/2016.
 */
public class Privilege implements Serializable {
    private int id, idRole, idRight;
    private String description;

    public Privilege(){

    }
    public Privilege(int id, int idRole, int idRight, String description){
        this.id = id;
        this.idRight = idRight;
        this.idRole = idRole;
        this.description = description;
    }
    public Privilege(int idRole, int idRight, String description){
        this.id = 0;
        this.idRight = idRight;
        this.idRole = idRole;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdRight() {
        return idRight;
    }

    public int getIdRole() {
        return idRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdRight(int idRight) {
        this.idRight = idRight;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
}
