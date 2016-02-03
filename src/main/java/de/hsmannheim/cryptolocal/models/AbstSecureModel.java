package de.hsmannheim.cryptolocal.models;

public abstract class AbstSecureModel{

    /*
     * JWS Signature
     * */
    String signature;

    /*
     * Hash value
     * */
    String hashValue;


    public String getSignature(){
        return this.signature;
    }

    public void setSignature( String signature ){
        this.signature = signature;
    }

    public String getHashValue(){
        return this.hashValue; 
    }

    public void setHashValue( String hashValue ){
        this.hashValue = hashValue; 
    }
}
