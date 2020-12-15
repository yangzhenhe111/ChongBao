package com.example.pet.forum;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.MainActivity;
import com.example.pet.R;

import com.example.pet.other.Cache;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PublishActivity extends AppCompatActivity {


    private Button btn_back;
    private Button btn_publish;
    private EditText et_title;
    private EditText et_text;
    private RelativeLayout add_image;
    private TextView tv_add_topic;
    private Button btn_topic;
    private ImageView upload;
    private String id;
    private String name;
    private Uri baseUri;
    private File file;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what){
                case 1:
                    Toast.makeText(PublishActivity.this,"发布成功",Toast.LENGTH_LONG);
                    break;
                case 2:
                    Toast.makeText(PublishActivity.this,"发布失败",Toast.LENGTH_LONG);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        upload = findViewById(R.id.img_upload);
        btn_back = findViewById(R.id.btn_publish_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PublishActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_publish = findViewById(R.id.btn_publish);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimg(v);
                publishPost();
                Intent intent = new Intent();
                intent.setClass(PublishActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        et_title = findViewById(R.id.et_title);
        et_text = findViewById(R.id.et_text);
        add_image = findViewById(R.id.add_image);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请权限部分
                String write=Manifest.permission.WRITE_EXTERNAL_STORAGE;
                String read=Manifest.permission.READ_EXTERNAL_STORAGE;

                final String[] WriteReadPermission = new String[] {write, read};

                int checkWrite= ContextCompat.checkSelfPermission(PublishActivity.this,write);
                int checkRead= ContextCompat.checkSelfPermission(PublishActivity.this,read);
                int ok= PackageManager.PERMISSION_GRANTED;

                if (checkWrite!= ok && checkRead!=ok){
                    //申请权限，读和写都申请一下，不然容易出问题
                    ActivityCompat.requestPermissions(PublishActivity.this,WriteReadPermission,1);
                }else openAlbum();
            }
        });
        tv_add_topic = findViewById(R.id.btn_add_topic);
        tv_add_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PublishActivity.this,TopicActivity.class);
                startActivity(intent);
            }
        });
        btn_topic = findViewById(R.id.btn_topic);
        btn_topic.setText(getIntent().getStringExtra("topic"));
    }

    public void publishPost(){
        new Thread(){
            @Override
            public void run() {
                int userid = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String time = simpleDateFormat.format(date);
                String title = et_title.getText().toString();
                String text = et_text.getText().toString();
                int number_like = 0;
                int number_comment = 0;
                int number_forward = 0;
                String topic = getIntent().getStringExtra("topic");
                try {
                    URL url = new URL(Cache.MY_URL+"AddPostServlet?user_id="+userid+"&post_time="+time+"&post_topic="+topic+"&post_title="+title+"&post_text="+text+"&number_like="+number_like+"&number_comment="+number_comment+"&number_forward="+number_forward+"&name="+name);
                    InputStream inputStream = url.openStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String isPublish = bufferedReader.readLine();
                    Log.e("返回的东西", isPublish);
                    if (isPublish.equals("true")){
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        handler.sendMessage(message);
                    }else{
                        Message message = handler.obtainMessage();
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                    bufferedReader.close();
                    inputStream.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        //把所有照片显示出来
        intent.setType("image/*");
        startActivityForResult(intent,123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            openAlbum();
        else Toast.makeText(this, "你拒绝了打开相册的权限", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //当选择完相片，就会回到这里，然后相片的相关信息会保存在data中，后面想办法取出来
        if (requestCode == 123) {
            //通过getData方法取得它的图片地址，后面的操作都是对这个地址的解析
            Uri uri = data.getData();
            //选择了一张在相册中id为26的照片，它的uri地址如下：
            //uri=content://com.android.providers.media.documents/document/image%3A26
            if (DocumentsContract.isDocumentUri(this, uri)) {

                //判断是document类型的图片，所以获取它的doc id
                String docId = DocumentsContract.getDocumentId(uri);//docId=image:26
                //docId是将该资源的关键信息提取出来，比如该资源是一张id为26的image

                //获取它的uri的已解码的授权组成部分，来判断这张图片是在相册文件夹下还是下载文件夹下
                String uri_getAuthority = uri.getAuthority();
                //在相册文件夹的照片标识字段如下
                //uri_getAuthority=com.android.providers.media.documents

                //注意这里的字符串很容易写错，providers和documents都是有带s的
                if ("com.android.providers.media.documents".equals(uri_getAuthority)) {
                    //当判断该照片在相册文件夹下时，使用字符串的分割方法split将它id取出来
                    id = docId.split(":")[1];//id="26"
                    baseUri = Uri.parse("content://media/external/images/media");
                    upload.setImageURI(Uri.withAppendedPath(baseUri, id));
                    name = getFileRealNameFromUri(this,Uri.withAppendedPath(baseUri,id));
                    file = new File(getFilePathForN(this,Uri.withAppendedPath(baseUri,id)));
                    Log.e("图片file",file+"");
                    Log.e("图片地址",Uri.withAppendedPath(baseUri,id).toString());
                    Log.e("图片名字",name);
//                    Log.e("图片地址",baseUri+id);
                    //直接传入Uri地址，该地址为content://media/external/images/media/26
                }
            }

        }
    }
    //通过uri找到文件的真实名字
    public static String getFileRealNameFromUri(Context context, Uri fileUri) {
        if (context == null || fileUri == null) return null;
        DocumentFile documentFile = DocumentFile.fromSingleUri(context, fileUri);
        if (documentFile == null) return null;
        return documentFile.getName();
    }
    //uri转file获取文件的真实路径
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static String getFilePathForN(Context context, Uri uri) {
        try {
            Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String name = (returnCursor.getString(nameIndex));
            File file = new File(context.getFilesDir(), name);
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            returnCursor.close();
            inputStream.close();
            outputStream.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void uploadimg(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UploadUtil.getInstance().upload(Cache.MY_URL+"PostUploadServlet?name="+name,file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
