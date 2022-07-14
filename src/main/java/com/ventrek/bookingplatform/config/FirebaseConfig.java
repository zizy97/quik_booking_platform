package com.ventrek.bookingplatform.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ventrek.bookingplatform.security.models.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;


@Configuration
public class FirebaseConfig {

    @Autowired
    private SecurityProperties secProps;

    @Primary
    @Bean
    public FirebaseApp getfirebaseApp() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/firebase.json");
        FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(inputStream))
                .setDatabaseUrl("https://ventrek-506cc-default-rtdb.firebaseio.com")
                .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();
    }

    @Bean
    public FirebaseAuth getAuth() throws IOException {
        return FirebaseAuth.getInstance(getfirebaseApp());
    }

    @Bean
    public FirebaseDatabase firebaseDatabase() throws IOException {
        return FirebaseDatabase.getInstance();
    }

    @Bean
    public Firestore getDatabase() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/firebase.json");
        FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
        return firestoreOptions.getService();
    }

    @Bean
    public FirebaseMessaging getMessaging() throws IOException {
        return FirebaseMessaging.getInstance(getfirebaseApp());
    }

    @Bean
    public FirebaseRemoteConfig getRemoteConfig() throws IOException {
        return FirebaseRemoteConfig.getInstance(getfirebaseApp());
    }
}
