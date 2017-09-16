package com.example.lenovo.versionupdate;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private String url = "http://125.39.134.47/r/a.gdown.baidu.com/data/wisegame/7c28ac069399b336/kuaishou_4812.apk";
    private Button btn;
    private Callback.Cancelable cancelable;//请求任务对象
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        setOnclick();
        initProgerss();
    }

    private void initProgerss() {
        progress = new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setButton(ProgressDialog.BUTTON_NEGATIVE, "暂停", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //取消下载任务
                cancelable.cancel();

            }
        });
    }

    private void setOnclick() {
         btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager manage=getPackageManager();
                try {
                    PackageInfo packageInfo = manage.getPackageInfo(getPackageName(),0);
                    //得到当前版本号
                    int versionCode = packageInfo.versionCode;
                   //进行网络请求，请求版本对象信息
                    Version version=new Version();
                    version.setUrl(url);
                    //如果版本号大于当前App版本号，下载服务器的数据
                    if(versionCode < version.getVersioncode())
                    {
                       //下载服务器Apk
                        downloadApk();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void install(File file) {
        //调用系统安装器
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()), "application/vnd.android.package-archive");
        startActivity(intent);
    }
    private void downloadApk() {
        RequestParams param=new RequestParams(url);
        param.setAutoRename(true);//设置是否支持断点下载
        param.setCancelFast(true);//设置是否立即取消
        cancelable= x.http().get(param, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                progress.dismiss();
                install(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @ Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                progress.show();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if(isDownloading)
                {
                    progress.setMax((int) total);
                    progress.setProgress((int) current);

                }
            }
        });
    }
}
