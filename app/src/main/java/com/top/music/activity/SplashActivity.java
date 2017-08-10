package com.top.music.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.top.music.R;
import com.top.music.bean.AlbumInfo;
import com.top.music.bean.ArtistInfo;
import com.top.music.bean.MusicInfo;
import com.top.music.utils.AlbumUtils;
import com.top.music.utils.ArtistUtils;
import com.top.music.utils.MusicUtils;
import com.top.music.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.top.music.utils.BasicUtils.mT;


public class SplashActivity extends Activity {

    private static final long WAITING_TIME = 3000;
    private static final String TAG = SplashActivity.class.getSimpleName();
    public static final String MY_MUSIC_TAG = "MY_MUSIC_TAG" ;
    public static final String MY_ARTIST_TAG = "MY_ARTIST_TAG" ;
    public static final String MY_ALBUM_TAG = "MY_ALBUM_TAG" ;
    public static final String MY_FAVORITE_TAG = "MY_FAVORITE_TAG";

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, mPermissionGrant);
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant);

        afterHavingPermission(); // 调到mainactivity里去
    }

    // 权限获取好之后，要进行的方法
    private void afterHavingPermission () {

       mT(this,"afterHavingPermission");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            /*获取、
            *        - 我的音乐，专辑，文件夹。。。  媒体内容提供者*/
            // 获取我的音乐列表
            final List<MusicInfo> musicList = MusicUtils.querySystemMusic(SplashActivity.this);
            final List<AlbumInfo> albumList = AlbumUtils.querySystemAlbums(SplashActivity.this);
            final List<ArtistInfo> artistList = ArtistUtils.querySystemAlbums(SplashActivity.this);
            // 从本应用数据库获取收藏过的音乐文件
            final List<MusicInfo> favoriteMusicList = MusicUtils.queryFavoriteMusic(SplashActivity.this);
            for (int i = 0; i < musicList.size(); i++) {
                for (int j = 0; j < favoriteMusicList.size(); j++) {
                    if (musicList.get(i)._id == favoriteMusicList.get(j)._id) {
                        musicList.get(i).setFavorite(1);
                    }
                }
            }
        /*过一段时间调到mainactivity里去*/
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putParcelableArrayListExtra(MY_MUSIC_TAG, (ArrayList<? extends Parcelable>) musicList);
                intent.putParcelableArrayListExtra(MY_ARTIST_TAG, (ArrayList<? extends Parcelable>) artistList);
                intent.putParcelableArrayListExtra(MY_ALBUM_TAG, (ArrayList<? extends Parcelable>) albumList);
                intent.putParcelableArrayListExtra(MY_FAVORITE_TAG, (ArrayList<? extends Parcelable>) favoriteMusicList);
                startActivity(intent); /* 调到 主页去 */
                overridePendingTransition(R.anim.home_enter,R.anim.splash_exit);
                finish();  /* 毁灭当前activity */
            }
        },WAITING_TIME);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }


    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    Toast.makeText(SplashActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    Toast.makeText(SplashActivity.this, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Toast.makeText(SplashActivity.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    Toast.makeText(SplashActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    Toast.makeText(SplashActivity.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    Toast.makeText(SplashActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    Toast.makeText(SplashActivity.this, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    Toast.makeText(SplashActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    Toast.makeText(SplashActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
