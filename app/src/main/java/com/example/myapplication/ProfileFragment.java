package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.example.myapplication.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView imgQRCode = rootView.findViewById(R.id.qr_code);

        String ID = "199854";

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{

            BitMatrix bitMatrix = multiFormatWriter.encode(ID, BarcodeFormat.QR_CODE, 200, 200);

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            imgQRCode.setImageBitmap(bitmap);

        }catch(WriterException e){
            throw new RuntimeException(e);
        }
        return rootView;
    }
}