package brayan0428.androidgooglesheet.com.android_googlesheet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import brayan0428.androidgooglesheet.com.android_googlesheet.Configuration.Variables;

public class AddUserActivity extends AppCompatActivity{
    Bitmap xBitMap;
    ImageView imgUser;
    ImageButton imageButton;
    RequestQueue request;
    EditText names,lastnames,address,email;
    Button addUser,cancelUser;
    String image = "";
    String idUser = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        imgUser = findViewById(R.id.imgUser);
        imageButton = findViewById(R.id.btnImage);
        names = findViewById(R.id.names);
        lastnames = findViewById(R.id.lastanames);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        addUser = findViewById(R.id.saveUser);
        cancelUser = findViewById(R.id.cancelUser);
        request = Volley.newRequestQueue(getApplicationContext());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EscogerImagen();
            }
        });
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(image.toString().trim().equals("") && idUser.equals("")){
                    mostrarMensaje("Debe seleccionar una imagen");
                    return;
                }
                if(names.getText().toString().trim().equals("")){
                    mostrarMensaje("Debe ingresar el nombre");
                }
                if(lastnames.getText().toString().trim().equals("")){
                    mostrarMensaje("Debe ingresar los apellidos");
                    return;
                }
                saveUser();
            }
        });
        cancelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retornar();
            }
        });

        //Obtengo datos para actualizar usuario
        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            idUser = datos.getString("Id");
            if (!idUser.equals("")){
                names.setText(datos.getString("Names"));
                lastnames.setText(datos.getString("LastNames"));
                address.setText(datos.getString("Address"));
                email.setText(datos.getString("Email"));
                Picasso.get()
                        .load(datos.getString("Image"))
                        //.placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_background)
                        .into(imgUser);
            }
        }
    }

    private void EscogerImagen(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), 1);
    }

    //Tamaño imagen seleccionada
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);

    }

    //Decodificar la imagen
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri filePath = data.getData();
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                xBitMap = getResizedBitmap(bitmap,250);
                image = getStringImage(bitmap);
                imgUser.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void saveUser(){
        final ProgressDialog progressDialog = ProgressDialog.show(this,"Guardando Información","Espere un momento...",false,false);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Variables.URL_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Guardado exitosamente",Toast.LENGTH_LONG).show();
                Retornar();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                try {
                    if (idUser.equals("")){
                        params.put("action","insert");
                        params.put(Variables.ID,Variables.stringToMD5(email.getText().toString() + names.getText().toString()));
                    }else{
                        params.put("action","update");
                        params.put(Variables.ID,idUser);
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                params.put(Variables.NAMES,names.getText().toString());
                params.put(Variables.LASTNAMES,lastnames.getText().toString());
                params.put(Variables.ADDRESS,address.getText().toString());
                params.put(Variables.EMAIL,email.getText().toString());
                if (idUser.equals("") || (!idUser.equals("") && !image.equals(""))){
                    params.put(Variables.IMAGE,image);
                }
                return params;
            }
        };

        //Limite de 5 segundos para una respuesta
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        postRequest.setRetryPolicy(policy);

        request.add(postRequest);
    }

    private void Retornar(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void mostrarMensaje(String msn){
        Toast.makeText(getApplicationContext(),msn,Toast.LENGTH_LONG).show();
    }
}
