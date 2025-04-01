package com.example.myfirstapplication.data.repository.token;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TokenRepository {
    private static final String PREF_FILE_NAME = "encrypted_prefs";
    private static final String KEY_JWT_TOKEN = "jwt_token";
    private final SharedPreferences encryptedPrefs;

    @Inject
    public TokenRepository(Context context) {
        SharedPreferences prefs;
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            prefs = EncryptedSharedPreferences.create(
                    context,
                    PREF_FILE_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Error initializing EncryptedSharedPreferences", e);
        }
        this.encryptedPrefs = prefs;
    }

    public void saveToken(String token) {
        encryptedPrefs.edit().putString(KEY_JWT_TOKEN, token).apply();
    }

    public String getToken() {
        return encryptedPrefs.getString(KEY_JWT_TOKEN, null);
    }

    public void clearToken() {
        encryptedPrefs.edit().remove(KEY_JWT_TOKEN).apply();
    }
}
