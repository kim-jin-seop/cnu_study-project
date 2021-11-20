package com.example.kimjinseop.termp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;

import static com.example.kimjinseop.termp.alone.main;
import static com.example.kimjinseop.termp.alone.markerOptions;
import static com.example.kimjinseop.termp.alone.storelatlng;
import static com.example.kimjinseop.termp.alone.titlename;
import static com.example.kimjinseop.termp.alone_list.bt;
import static com.example.kimjinseop.termp.alone_list.cont;
import static com.example.kimjinseop.termp.alone_list.ismodify;


public class add_alone extends AppCompatActivity implements View.OnClickListener{
    private TextView tvStatus;
    private Button btnLoad;
    private EditText ImageName,ImageTitle;
    private Button btnSave;
    private ImageView ivImage;
    private ImageView ivCroppedImage;
    private LatLng tlatlng;
    static String name;
    private String imagename;
    private boolean isload;

    //private MarkerOptions mark;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        ImageName = (EditText) findViewById(R.id.ImageName);
        btnSave = (Button) findViewById(R.id.btnSave);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ImageTitle = (EditText) findViewById(R.id.ImageTitle);
       //ivCroppedImage = (ImageView) findViewById(R.id.ivCroppedImage);
        tlatlng = storelatlng;
        if(ismodify){
                ImageTitle.setText(titlename);
        ImageName.setText(cont);
        ivImage.setImageBitmap(bt);
        tvStatus.setText("");
        }

        btnLoad.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }
    public static int RESULT_LOAD_IMAGE = 1;

    @Override
    public void onClick(View v) {
        if(v instanceof Button){
            Button tmpBtn = (Button) v;
            if(tmpBtn.getId() == R.id.btnLoad){
                Intent tmpl = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(tmpl, RESULT_LOAD_IMAGE);
            }
            else if(tmpBtn.getId() == R.id.btnSave){
                String entire = Environment.getExternalStorageDirectory().getAbsolutePath();
                entire += "/junior";
                File directory = new File(entire);
                if(!directory.exists()){
                    directory.mkdir();
                }

                alone add = (alone) main;
                if(ismodify){
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    path += "/junior/alone/"+titlename+".jpeg";
                    final File file = new File(path);
                    file.delete();
                    add.dbdelete(tlatlng, titlename);
//                    Marker mark = delmarker;
//                    mark.remove();
//                    delmarker = null;
//                    ismodify = false;
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons()));
                    markerOptions.position(storelatlng);
                }

                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                path += "/junior/alone";
                File file = new File(path);

                if(!file.exists()){
                    file.mkdir();
                }
//                Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();


                //name = ImageName.getText().toString();
                name = ImageTitle.getText().toString();
                path += "/"+name+".jpeg";

                if(isload) {
                    ivImage.buildDrawingCache();
                    Bitmap bitmap = ivImage.getDrawingCache();
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(path);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                markerOptions.title(name);


                add.addmark(markerOptions);
                if(ismodify){
                    add.deletemark();
                    ismodify = false;
                }
//                mMap.addMarker(markerOptions).showInfoWindow();

                add.dbinsert(tlatlng, name,ImageName.getText().toString());

                Toast.makeText(getApplicationContext(), name + " 다이어리 추가완료", Toast.LENGTH_SHORT).show();
                storelatlng = null;
                titlename = "";
                cont = "";

                finish();
            }
        }
    }

    public Bitmap resizeMapIcons(){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.maker);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 150, 150, false);
        return resizedBitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            Bitmap imagebit = null;

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,null,null,null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            imagebit = BitmapFactory.decodeFile(picturePath,options);

            cursor.close();

//            ivImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            if(imagebit!=null) {
                ivImage.setImageBitmap(imagebit);
            }
            imagename = getImageNameToUri(data.getData());
            tvStatus.setText("");
        }
    }
    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);
        isload = true;
        return imgName;
    }

}
