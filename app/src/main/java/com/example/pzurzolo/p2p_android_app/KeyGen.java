package com.example.pzurzolo.p2p_android_app;

import android.util.Log;

import org.spongycastle.jce.ECNamedCurveTable;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.Enumeration;

public class KeyGen {

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    public KeyPair generateKeyPair()
    {
        ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("prime256v1");
        KeyPairGenerator g = null;

        try {

            g = KeyPairGenerator.getInstance("EC", "BC");
            g.initialize(ecGenSpec, new SecureRandom());

        } catch (Exception e) {
            Log.e("KeyGen", e.getMessage());
        }

        KeyPair pair = g.generateKeyPair();

        return pair;
    }

}
