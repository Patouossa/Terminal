package BO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IBRAHIM on 23/09/2016.
 */
public class User implements Serializable {
    String username, password, firstname, lastname, sex, address;
    private int id, isWaiter, idRole, insertUser, editUser;
    private Date inserDate, editDate;

    public User(){

    }

    public User(int id,String username, String password, String firstname, String lastname, String sex, String address, int isWaiter, int idRole, int insertUser, int editUser, Date inserDate, Date editDate){
        this.address = address;
        this.editDate = editDate;
        this.editUser = editUser;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.address = address;
        this.isWaiter = isWaiter;
        this.idRole = idRole;
        this.inserDate = inserDate;
        this.insertUser = insertUser;
        this.id = id;
    }

    public User(String username, String password, String firstname, String lastname, String sex, String address, int isWaiter, int idRole, int insertUser, int editUser, Date inserDate, Date editDate){
        this.address = address;
        this.editDate = editDate;
        this.editUser = editUser;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.address = address;
        this.isWaiter = isWaiter;
        this.idRole = idRole;
        this.inserDate = inserDate;
        this.insertUser = insertUser;
        this.id = 0;
    }

    public void setInsertUser(int insertUser) {
        this.insertUser = insertUser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getEditDate() {
        return editDate;
    }

    public Date getInserDate() {
        return inserDate;
    }

    public int getEditUser() {
        return editUser;
    }

    public int getId() {
        return id;
    }

    public int getIdRole() {
        return idRole;
    }

    public int getInsertUser() {
        return insertUser;
    }

    public int getIsWaiter() {
        return isWaiter;
    }

    public String getAddress() {
        return address;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public String getUsername() {
        return username;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public void setEditUser(int editUser) {
        this.editUser = editUser;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public void setInserDate(Date inserDate) {
        this.inserDate = inserDate;
    }

    public void setIsWaiter(int isWaiter) {
        this.isWaiter = isWaiter;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
