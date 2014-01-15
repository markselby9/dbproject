/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bll;

/**
 *
 * @author fengchaoyi
 */
public class Fenshu {
    private String koufen;
    private String time;
    private String LicenceID;
    private String OfficeID;
    private String reason;
    private String shengyufen;
    private Fenshu next;
    
    public Fenshu(){
        
    }

    /**
     * @return the koufen
     */
    public String getKoufen() {
        return koufen;
    }

    /**
     * @param koufen the koufen to set
     */
    public void setKoufen(String koufen) {
        this.koufen = koufen;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the LicenceID
     */
    public String getLicenceID() {
        return LicenceID;
    }

    /**
     * @param LicenceID the LicenceID to set
     */
    public void setLicenceID(String LicenceID) {
        this.LicenceID = LicenceID;
    }

    /**
     * @return the OfficeID
     */
    public String getOfficeID() {
        return OfficeID;
    }

    /**
     * @param OfficeID the OfficeID to set
     */
    public void setOfficeID(String OfficeID) {
        this.OfficeID = OfficeID;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the shengyufen
     */
    public String getShengyufen() {
        return shengyufen;
    }

    /**
     * @param shengyufen the shengyufen to set
     */
    public void setShengyufen(String shengyufen) {
        this.shengyufen = shengyufen;
    }

    /**
     * @return the next
     */
    public Fenshu getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(Fenshu next) {
        this.next = next;
    }
}
