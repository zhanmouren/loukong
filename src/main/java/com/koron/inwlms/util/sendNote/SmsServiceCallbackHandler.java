package com.koron.inwlms.util.sendNote;


/**
 * SmsServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */


/**
 *  SmsServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class SmsServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public SmsServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public SmsServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for getSpecialDownSmsResult method
     * override this method for handling normal response from getSpecialDownSmsResult operation
     */
    public void receiveResultgetSpecialDownSmsResult(
        SmsServiceStub.GetSpecialDownSmsResultResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getSpecialDownSmsResult operation
     */
    public void receiveErrorgetSpecialDownSmsResult(Exception e) {
    }

    /**
     * auto generated Axis2 call back method for rspUpSms method
     * override this method for handling normal response from rspUpSms operation
     */
    public void receiveResultrspUpSms(
        SmsServiceStub.RspUpSmsResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from rspUpSms operation
     */
    public void receiveErrorrspUpSms(Exception e) {
    }

    /**
     * auto generated Axis2 call back method for insertDownSms method
     * override this method for handling normal response from insertDownSms operation
     */
    public void receiveResultinsertDownSms(
        SmsServiceStub.InsertDownSmsResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from insertDownSms operation
     */
    public void receiveErrorinsertDownSms(Exception e) {
    }

    /**
     * auto generated Axis2 call back method for checkpass method
     * override this method for handling normal response from checkpass operation
     */
    public void receiveResultcheckpass(
        SmsServiceStub.CheckpassResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from checkpass operation
     */
    public void receiveErrorcheckpass(Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getUpSms method
     * override this method for handling normal response from getUpSms operation
     */
    public void receiveResultgetUpSms(
        SmsServiceStub.GetUpSmsResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getUpSms operation
     */
    public void receiveErrorgetUpSms(Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getDownSmsResult method
     * override this method for handling normal response from getDownSmsResult operation
     */
    public void receiveResultgetDownSmsResult(
        SmsServiceStub.GetDownSmsResultResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getDownSmsResult operation
     */
    public void receiveErrorgetDownSmsResult(Exception e) {
    }

    /**
     * auto generated Axis2 call back method for connMas method
     * override this method for handling normal response from connMas operation
     */
    public void receiveResultconnMas(
        SmsServiceStub.ConnMasResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from connMas operation
     */
    public void receiveErrorconnMas(Exception e) {
    }
}

