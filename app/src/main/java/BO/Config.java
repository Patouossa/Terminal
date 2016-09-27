package BO;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by IBRAHIM on 23/09/2016.
 */
public class Config implements Serializable {
    private int id, enablePin;
    private String language, currency;
    private double taxRate;
    private String paypal_email;
    public Config(){

    }
    public Config(int id, String language, String currency, double taxRate, int enablePin, String paypal_email){
        this.id = id;
        this.language = language;
        this.currency = currency;
        this.taxRate = taxRate;
        this.enablePin = enablePin;
        this.paypal_email = paypal_email;
    }
    public Config(String language, String currency, double taxRate, int enablePin, String paypal_email){
        this.id = 0;
        this.language = language;
        this.currency = currency;
        this.taxRate = taxRate;
        this.enablePin = enablePin;
        this.paypal_email = paypal_email;
    }

    public int getId() {
        return id;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public int getEnablePin() {
        return enablePin;
    }

    public String getLanguage() {
        return language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setEnablePin(int enablePin) {
        this.enablePin = enablePin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getPaypal_email() {
        return paypal_email;
    }

    public void setPaypal_email(String paypal_email) {
        this.paypal_email = paypal_email;
    }
}
