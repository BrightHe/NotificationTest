package com.example.hzh.notificationtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button send_notice;
    private String channelId = "com.hzh.myNotice";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        send_notice = (Button)findViewById( R.id.send_notice );
        send_notice.setOnClickListener( this );
        //需要进行版本判定，和创建通知通道
//        send_notice.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent( MainActivity.this,NotificationActivity.class );
//                PendingIntent pendingIntent = PendingIntent.getActivity( MainActivity.this,0,intent,0 );//获取PendingIntent实例
//                NotificationManager manager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
//                Notification notification = new NotificationCompat.Builder( MainActivity.this,channelId )
//                        .setContentTitle( "this is content title" )
//                        .setContentText( "this is content text" )
//                        .setWhen( System.currentTimeMillis() )
//                        .setSmallIcon( R.mipmap.ic_launcher )
//                        .setLargeIcon( BitmapFactory.decodeResource( getResources(),R.mipmap.ic_launcher ) )
//                        .setContentIntent( pendingIntent )
//                        .build();
//                manager.notify(1, notification );
//            }
//        } );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_notice:
                Intent intent = new Intent( MainActivity.this,NotificationActivity.class );
                PendingIntent pendingIntent = PendingIntent.getActivity( MainActivity.this,0,intent,0 );
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = null;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //创建通道
                    NotificationChannel channel = new NotificationChannel(channelId, "simple", NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(channel);

                    notification = new NotificationCompat.Builder(MainActivity.this, channelId)
                            .setContentTitle("This is content title")
                            //缩略文本
                            .setContentText("Learn how to build notification ,send and sync data," +
                                    "and use voice actions.Get the official Android IDE and developer tools to build apps for Android.")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                            .setContentIntent( pendingIntent )
                            .setAutoCancel( true )//跳入具体页面后，通知自动取消
//                            .setSound( Uri.parse("/system/media/audio/ringtones/Luna.ogg") )//播放音频
//                            .setVibrate( new long[]{0,1000,1000,1000} )//震动，每隔一秒
                            .setLights( Color.GREEN,1000,1000 )
                            .setDefaults( NotificationCompat.DEFAULT_ALL )
                            //长文本可以展开
                            .setStyle( new NotificationCompat.BigTextStyle(  ).bigText( "Learn how to build notification ,send and sync data," +
                                            "and use voice actions.Get the official Android IDE and developer tools to build apps for Android." ) )//长文本
                            //会重写上面的setStyle()
                            .setStyle( new NotificationCompat.BigPictureStyle(  ).bigPicture( BitmapFactory.decodeResource( getResources(),R.drawable.sign_pic ) ) )//大图片
                            //相比于其他通知的优先级，最高
                            .setPriority( NotificationCompat.PRIORITY_MAX )//设置优先级
                            .build();
                } else{
                    notification = new NotificationCompat.Builder(MainActivity.this, channelId)
                            .setContentTitle("This is content title")
                            .setContentText("This is content text")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                            .setContentIntent( pendingIntent )
                            .setAutoCancel( true )//跳入具体页面后，通知自动取消
//                            .setSound( Uri.parse("/system/media/audio/ringtones/Luna.ogg") )//播放音频
//                            .setVibrate( new long[]{0,1000,1000,1000} )//震动，每隔一秒
                            .setLights( Color.RED,1000,1000 )
                            .build();
                }
                manager.notify(1, notification);
                break;
            default:
                break;
        }
    }
}
