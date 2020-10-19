package com.example.pertemuan_2_pmo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private int MY_REQUEST_CODE = 1 ;
    EditText etNamaFile, etIsiFile ;
    Button bt_buat_file, bt_read_form;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNamaFile = findViewById(R.id.nama_file);
        etIsiFile = findViewById(R.id.isi_file);
        bt_buat_file = findViewById(R.id.bt_buat_file);
        bt_read_form = findViewById(R.id.bt_read_form);

        bt_buat_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buatFile(v);
            }
        });

        bt_read_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bacaData(v);
            }
        });
    }

    public void buatFile(View view){
        if (isExternalStorageWriteable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            File textfile = new File(Environment.getExternalStorageDirectory(), etNamaFile.getText().toString());
            try {
                FileOutputStream fos = new FileOutputStream(textfile);
                fos.write(etIsiFile.getText().toString().getBytes());
                fos.close();

                Toast.makeText(this, "File Berhasil disimpan",Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "File tidak dapat disimpan", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(permission) !=PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{permission},
                        MY_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Permission diberikan", Toast.LENGTH_SHORT).show();
            }
            }
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check==PackageManager.PERMISSION_GRANTED);
    }



    private boolean isExternalStorageWriteable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return true;
        } else {
            return false;
        }
    }

    public void bacaData(View view){
        Intent intent = new Intent(MainActivity.this, ReadData.class);
        startActivity(intent);
    }


}