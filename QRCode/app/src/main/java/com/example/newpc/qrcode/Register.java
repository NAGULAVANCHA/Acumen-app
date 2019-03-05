package com.example.newpc.qrcode;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

public class Register extends Fragment implements View.OnClickListener,View.OnKeyListener{
    EditText text;
    EditText text2;
    EditText text3;
    Button gen_btn;
    String text2Qr;
    ImageView img1;
    String name=null;
    String email=null;
    String password=null;

    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }



        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registering...");
        progressDialog.show();
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        text2Qr = _nameText.getText().toString().trim()+"  "+_emailText.getText().toString().trim()+" "+_passwordText.getText().toString().trim();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Intent in1 = new Intent(getContext(), DisplayActivity.class);
            in1.putExtra("image",byteArray);
            startActivity(in1);
        }
        catch (WriterException e){
            e.printStackTrace();
        }

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 8000);

    }


    public void onSignupSuccess() {

    }

    public void onSignupFailed() {
        Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

            name = _nameText.getText().toString();
            email = _emailText.getText().toString();
            password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 4) {
            _nameText.setError("at least 4 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register,container,false);
                    _nameText = (EditText)view.findViewById(R.id.input_name);
        _emailText = (EditText)view.findViewById(R.id.input_email);
        _passwordText = (EditText)view.findViewById(R.id.input_password);
        ButterKnife.inject(getActivity());
            gen_btn = (Button)view.findViewById(R.id.btn_signup);
            _signupButton= (Button)view.findViewById(R.id.btn_signup);
        _signupButton.setOnClickListener(this);
        LinearLayout gg=(LinearLayout)view.findViewById(R.id.lin);
        gg.setOnClickListener(this);
         img1=(ImageView)view.findViewById(R.id.image111);
        ScrollView sc=(ScrollView)view.findViewById(R.id.scrollview);
        sc.setOnClickListener(this);
        img1.setOnClickListener(this);
        return  view;
    }
    @Override
    public void onClick(View v){
if(v.getId()==R.id.btn_signup){
    signup();
}else if(v.getId()==R.id.lin||v.getId()==R.id.image111){
    InputMethodManager inputMethodManager=(InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
    Log.i("onclicked","onscreen");
        }
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
            signup();
        }


        return false;
    }
}
