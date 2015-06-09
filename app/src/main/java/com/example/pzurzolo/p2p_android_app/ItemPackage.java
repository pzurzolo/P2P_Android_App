package com.example.pzurzolo.p2p_android_app;

public class ItemPackage {
    private String itemPackageCertificate;
    private String itemPackageOrigin;
    private String itemPackageDestination;
    private String itemPackageInformation;
    private Boolean itemPackageDelivered;

    public ItemPackage (String cert, String origin, String destination,
                        String information) {
        itemPackageCertificate = cert;
        itemPackageOrigin = origin;
        itemPackageDestination = destination;
        itemPackageInformation = information;
        itemPackageDelivered = false;
    }

    public String getItemPackageCertificate() { return itemPackageCertificate; }
    public String getItemPackageOrigin() { return itemPackageOrigin; }
    public String getItemPackageDestination() { return itemPackageDestination; }
    public String getItemPackageInformation() { return itemPackageInformation; }
    public Boolean getItemPackageDelivered() { return itemPackageDelivered; }
    public void setItemPackageDelivered() {
        itemPackageDelivered = true;
    }
}
