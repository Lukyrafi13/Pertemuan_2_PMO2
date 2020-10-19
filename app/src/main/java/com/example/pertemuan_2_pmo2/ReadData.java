package com.example.pertemuan_2_pmo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadData extends AppCompatActivity {

    EditText etNamaFile;
    EditText etIsiFile;
    Button bt_read_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        etNamaFile = findViewById(R.id.nama_file);
        etIsiFile = findViewById(R.id.et_isi_file);
        bt_read_data = findViewById(R.id.bt_read_data);

        bt_read_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bacaData(v);
            }
        });
    }

    public void bacaData(View view){
        if (isExternalStorageReadable()){
            StringBuilder stringBuilder = new StringBuilder();

            try {
                File textfile = new File(Environment.getExternalStorageDirectory(),
                        etNamaFile.getText().toString());
                FileInputStream fis = new FileInputStream(textfile);
                if (fis != null) {
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(isr);

                    String line = null;

                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line + "\n");
                    }

                    fis.close();
                }
                etIsiFile.setText(stringBuilder);
            }catch (IOException e){
                Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Tidak dapat membuat file", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isExternalStorageReadable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
        Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())){
            return true;
        }else {
            return false;
        }
    }
}