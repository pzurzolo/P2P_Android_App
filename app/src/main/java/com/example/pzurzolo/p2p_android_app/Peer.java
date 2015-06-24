package com.example.pzurzolo.p2p_android_app;

import java.util.ArrayList;
import java.util.List;

public class Peer {

    private String peerCertificate;
    private String peerUsername;
    private String peerPassword;
    private List items;

    public Peer(String cert, String username, String pwd) {
        peerCertificate = cert;
        peerUsername = username;
        peerPassword = pwd;
        items = new ArrayList();
    }

    public String getPeerCertificate() { return peerCertificate; }
    public String getPeerUsername() { return  peerUsername; }
    public String getPeerPassword() { return  peerPassword; }

    public void addPackageToList(ItemPackage newItem) {
        //TODO: Need to add in checks to make sure item being added is valid.
        items.add(newItem);
    }
    public void removePackageFromList(ItemPackage item) {
        items.remove(item);
    }
}
