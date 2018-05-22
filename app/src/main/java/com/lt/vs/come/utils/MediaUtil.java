package com.lt.vs.come.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.lt.vs.come.entrys.MediaItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 金色VS时空 on 2017/9/29.
 */

public class MediaUtil {
    public static List<MediaItem> getMusicData(final Context context){
        final List<MediaItem> oList=new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                super.run();
                ContentResolver resolver=context.getContentResolver();
                //查询手机内的音乐文件
                Cursor cursor=resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null
                        ,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                if (cursor!=null){
                    while(cursor.moveToNext()){
                        MediaItem music=new MediaItem();
                        String name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                        String author=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                        String path=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                        long duration=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                        if (author.equals("<unknown>")){
                            author="未知艺术家";
                        }
                        music.setName(name);
                        music.setAuthor(author);
                        music.setPath(path);
                        music.setTotaltime(duration);

                        if (duration>20000){
                            oList.add(music);
                        }
                    }

                    cursor.close();

                }
            }
        }.start();

        return oList;
    }

    public static List<MediaItem> getMovieData(final Context context){
        List<MediaItem> oList=new ArrayList<>();
        /*new Thread(){
            @Override
            public void run() {
                super.run();*/
                ContentResolver resolver=context.getContentResolver();
                //查询手机内的视频文件
                Cursor cursor=resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null,null,null
                        ,MediaStore.Video.Media.DEFAULT_SORT_ORDER);
                if (cursor!=null){
                    while(cursor.moveToNext()){
                        MediaItem music=new MediaItem();
                        String name=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                        String author=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ARTIST));
                        String path=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                        long duration=cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));

                        music.setName(name);
                        music.setAuthor(author);
                        music.setPath(path);
                        music.setTotaltime(duration);

                        if (duration>20000){
                            oList.add(music);
                        }
                        Log.e("======",oList.size()+"");
                    }
                    cursor.close();

                }

        /*    }
        }.start();*/
        return oList;
    }
}
