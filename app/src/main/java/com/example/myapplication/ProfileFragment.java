package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ProfileFragment extends Fragment {

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView txtNombre, txtPuntos;
        Activity menuActivity = getActivity();

        if (menuActivity != null) {
            Intent intent = menuActivity.getIntent();

            if (intent != null){
                int id = intent.getIntExtra("id", -1);
                String nombre = intent.getStringExtra("nombre");
                String apellido = intent.getStringExtra("apellido");
                int puntos = intent.getIntExtra("puntos", -1);
                if (id != -1){
                    ImageView imgQRCode = rootView.findViewById(R.id.qr_code);

                    String ID = String.valueOf(id);

                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    try{

                        BitMatrix bitMatrix = multiFormatWriter.encode(ID, BarcodeFormat.QR_CODE, 200, 200);

                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                        imgQRCode.setImageBitmap(bitmap);

                    }catch(WriterException e){
                        throw new RuntimeException(e);
                    }
                    txtNombre = rootView.findViewById(R.id.txtName);
                    txtPuntos = rootView.findViewById(R.id.txtPoints);

                    txtNombre.setText(nombre+" "+apellido);
                    txtPuntos.setText("Points: "+puntos);
                }
                }
            }
        return rootView;
    }

}