package com.example.garimapeti.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garimapeti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class AuthFragment extends Fragment {

    EditText phoneEditText;
    Button otpButton;
    EditText otpEditText;
    Button otpSubmitButton;
    TextView resendTextView;
    ProgressBar progressBar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Long timeOutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    String phoneNumber;


    public AuthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        otpButton = view.findViewById(R.id.otpButton);
        otpEditText = view.findViewById(R.id.otpEditText);
        otpSubmitButton = view.findViewById(R.id.otpSubmitButton);
        resendTextView = view.findViewById(R.id.resendTextView);
        progressBar = view.findViewById(R.id.progressBar);

        otpEditText.setVisibility(View.INVISIBLE);
        otpSubmitButton.setVisibility(View.INVISIBLE);
        resendTextView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        //FirebaseAuth.getInstance().getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);

        otpButton.setOnClickListener(view1 -> {
            phoneNumber = phoneEditText.getText().toString();
            if(phoneNumber.length()==10){
                phoneEditText.setEnabled(false);
                phoneNumber = "+91"+phoneNumber;
                sendOTP(phoneNumber, false);
                otpButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
            else {
                Toast.makeText(getContext(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            }
        });

        otpSubmitButton.setOnClickListener(view12 -> {
            String otp = otpEditText.getText().toString();
            if(otp.length()==6){
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                submitReport(credential);
                progressBar.setVisibility(View.VISIBLE);
            }
            else {
                Toast.makeText(getContext(), "Enter Correct OTP", Toast.LENGTH_SHORT).show();
            }
        });

        resendTextView.setOnClickListener(view13 -> {
            sendOTP(phoneNumber,true);
        });

        return view;
    }

    void sendOTP(String phoneNumber, boolean isResend){
        startResendTimer();
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeOutSeconds, TimeUnit.SECONDS)
                        .setActivity(getActivity())
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                submitReport(phoneAuthCredential);
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                //Log.i("Err", e.getMessage());
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                Toast.makeText(getContext(), "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                otpEditText.setVisibility(View.VISIBLE);
                                resendTextView.setVisibility(View.VISIBLE);
                                otpSubmitButton.setVisibility(View.VISIBLE);
                            }
                        });

        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    private void startResendTimer() {
        resendTextView.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeOutSeconds--;
                if(getActivity() == null)
                    return;
                getActivity().runOnUiThread(() -> {
                    resendTextView.setText("Resend OTP in "+timeOutSeconds+" seonds");
                });

                if(timeOutSeconds<=0){
                    timeOutSeconds=60L;
                    timer.cancel();
                    if(getActivity() == null)
                        return;
                    getActivity().runOnUiThread(() -> {
                        resendTextView.setText("Resend OTP");
                        resendTextView.setEnabled(true);
                    });
                }
            }
        },0,1000);
    }

    private void submitReport(PhoneAuthCredential phoneAuthCredential) {
        Bundle b = new Bundle();
        b.putString("phone", phoneNumber);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                progressBar.setVisibility(View.GONE);
                ReportFragment reportFragment = new ReportFragment();
                reportFragment.setArguments(b);
                loadFragment(reportFragment);
            }
            else {
                Toast.makeText(getContext(), "Verification Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment).addToBackStack("abc");
        ft.commit();
    }
}