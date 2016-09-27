package BO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IBRAHIM on 23/09/2016.
 */
public class Bill implements Serializable {
    private int id, insertUser;
    private double amount;
    private String note, status;
    private Date insertDate;

    public Bill(){

    }

    public Bill(int id, double amount, String note, String status, int insertUser, Date insertDate){
        this.id = id;
        this.amount = amount;
        this.note = note;
        this.status = status;
        this.insertDate = insertDate;
        this.insertUser = insertUser;
    }

    public Bill(double amount, String note, String status, int insertUser, Date insertDate){
        this.id = 0;
        this.amount = amount;
        this.note = note;
        this.status = status;
        this.insertDate = insertDate;
        this.insertUser = insertUser;
    }

    public int getId() {
        return id;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public double getAmount() {
        return amount;
    }

    public int getInsertUser() {
        return insertUser;
    }

    public String getNote() {
        return note;
    }

    public String getStatus() {
        return status;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public void setInsertUser(int insertUser) {
        this.insertUser = insertUser;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
