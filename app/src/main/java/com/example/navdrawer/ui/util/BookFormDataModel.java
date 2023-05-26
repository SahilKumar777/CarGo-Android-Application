package com.example.navdrawer.ui.util;

import java.util.Date;

public class BookFormDataModel {
    private String from,to,returnTo;
    private String dateP,dateR;
    private String timeP,timeR;
    private boolean isReturn;

    public BookFormDataModel(){}

    public BookFormDataModel(String from,String to,String dateP,String timeP,boolean isReturn){
        this.from=from;
        this.to=to;
        this.dateP=dateP;
        this.timeP=timeP;
        this.isReturn=isReturn;
    }

    public BookFormDataModel(String from,String to,String dateP,String timeP,boolean isReturn,String returnTo,String dateR,String timeR){
        this.from=from;
        this.to=to;
        this.dateP=dateP;
        this.timeP=timeP;
        this.isReturn=isReturn;
        this.returnTo=returnTo;
        this.dateR=dateR;
        this.timeR=timeR;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDateP(String dateP) {
        this.dateP = dateP;
    }

    public void setTimeP(String timeP) {
        this.timeP = timeP;
    }

    public void setIsReturn(boolean isReturn) {
        this.isReturn = isReturn;
    }

    public void setReturnTo(String returnTo) {
        this.returnTo = returnTo;
    }

    public void setDateR(String dateR) {
        this.dateR = dateR;
    }

    public void setTimeR(String timeR) {
        this.timeR = timeR;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDateP() {
        return dateP;
    }

    public String getTimeP() {
        return timeP;
    }

    public boolean getIsReturn() {
        return isReturn;
    }

    public String getReturnTo() {
        return returnTo;
    }

    public String getDateR() {
        return dateR;
    }

    public String getTimeR() {
        return timeR;
    }
}
