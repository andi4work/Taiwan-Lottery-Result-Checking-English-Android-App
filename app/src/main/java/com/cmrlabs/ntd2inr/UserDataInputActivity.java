package com.cmrlabs.ntd2inr;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class UserDataInputActivity extends AppCompatActivity {
    public static final String TAG = "TAG";

    private static final int GALLERY_INTENT_CODE = 1023;
    TextView fullNameAsperThePassport, email, phone, ntdAmount, indiaBankAccountNumber, getIndiaBankAccountHolderName, bankIfsc, passportId, arcId, message;
    String fullNameAsperThePassportS, emailS, phoneS, ntdAmountS, indiaBankAccountNumberS, getIndiaBankAccountHolderNameS, bankIfscS, passportIdS, arcIdS, messageS;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userId;
    Button save;
    Button resetPassLocal, changeProfileImage;
    ImageView profileImage;
    StorageReference storageReference;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        fullNameAsperThePassport = findViewById(R.id.tvFullName);
        email = findViewById(R.id.tvEmail);
        phone = findViewById(R.id.tvPhone);
        ntdAmount = findViewById(R.id.tvNtdAmount);
        indiaBankAccountNumber = findViewById(R.id.tvIndiaBankAccountNumber);
        getIndiaBankAccountHolderName = findViewById(R.id.tvIndiaBankAccountHolderName);
        bankIfsc = findViewById(R.id.tvBankIfsc);
        passportId = findViewById(R.id.tvPassportId);
        arcId = findViewById(R.id.tvArc);
        message = findViewById(R.id.tvMessage);
        save = findViewById(R.id.bSave);
        progressBar = findViewById(R.id.progressBar);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fullNameAsperThePassportS = fullNameAsperThePassport.getText().toString();
                emailS = email.getText().toString();
                phoneS = phone.getText().toString();
                ntdAmountS = ntdAmount.getText().toString();
                indiaBankAccountNumberS = indiaBankAccountNumber.getText().toString();
                getIndiaBankAccountHolderNameS = getIndiaBankAccountHolderName.getText().toString();
                bankIfscS = bankIfsc.getText().toString();
                passportIdS = passportId.getText().toString();
                arcIdS = arcId.getText().toString();
                messageS = message.getText().toString();

                if (TextUtils.isEmpty(fullNameAsperThePassportS)) {
                    fullNameAsperThePassport.setError("Full name is Required.");
                    return;
                }
                if (TextUtils.isEmpty(emailS)) {
                    email.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(phoneS)) {
                    phone.setError("Phone number is Required.");
                    return;
                }
                if (TextUtils.isEmpty(ntdAmountS)) {
                    ntdAmount.setError("NTD amount is Required.");
                    return;
                }
                if (TextUtils.isEmpty(indiaBankAccountNumberS)) {
                    indiaBankAccountNumber.setError("India Bank Account Number is Required.");
                    return;
                }
                if (TextUtils.isEmpty(getIndiaBankAccountHolderNameS)) {
                    getIndiaBankAccountHolderName.setError("Account holder name is Required.");
                    return;
                }
                if (TextUtils.isEmpty(bankIfscS)) {
                    bankIfsc.setError("IFSC is Required.");
                    return;
                }
                if (TextUtils.isEmpty(passportIdS)) {
                    passportId.setError("Passport ID is Required.");
                    return;
                }
                if (TextUtils.isEmpty(arcIdS)) {
                    arcId.setError("ARC/APRC ID is Required.");
                    return;
                }
                if (TextUtils.isEmpty(messageS)) {
                    message.setError("Message is Required.");
                    return;

                }
                progressBar.setVisibility(View.VISIBLE);
                userId = fAuth.getCurrentUser().getUid();

                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String, Object> user = new HashMap<>();

                user.put("fullNameAsperThePassport", fullNameAsperThePassportS);
                user.put("email", emailS);
                user.put("phone", phoneS);
                user.put("ntdAmount", ntdAmountS);
                user.put("indiaBankAccountNumber", indiaBankAccountNumberS);
                user.put("getIndiaBankAccountHolderName", getIndiaBankAccountHolderNameS);
                user.put("bankIfsc", bankIfscS);
                user.put("passportId", passportIdS);
                user.put("arcId", arcIdS);
                user.put("message", messageS);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user Profile is created for " + userId);
                        progressBar.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

    }

}
