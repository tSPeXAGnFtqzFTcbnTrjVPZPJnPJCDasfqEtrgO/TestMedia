package com.example.phamngocan.testmediaapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;

import com.example.phamngocan.testmediaapp.adapter.SearchAdapter;
import com.example.phamngocan.testmediaapp.function.MusicPlayer;
import com.example.phamngocan.testmediaapp.function.RxSearch;
import com.example.phamngocan.testmediaapp.function.ShowLog;
import com.example.phamngocan.testmediaapp.loader.PlaylistLoader;
import com.example.phamngocan.testmediaapp.loader.PlaylistSongLoader;
import com.example.phamngocan.testmediaapp.model.Playlist;
import com.example.phamngocan.testmediaapp.model.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

interface X {
    static Observable<char[]> search(char[] cs) {
        return Observable.defer(() -> Observable.just(cs));
    }

    static Observable<String> search1(String s) {
        return Observable.defer(() -> Observable.just(s));
    }
}



public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static String PACKAGE_NAME;
    public static Context context;
    private final int REQUEST_PERMISSION_READ_EXTERNAL = 123;

    @BindView(R.id.list_search)
    RecyclerView listSearch;
    ArrayList<String> searchs = new ArrayList<>();
    SearchAdapter adapterSearch;
    @BindView(R.id.searchView)
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        PACKAGE_NAME = getPackageName();
        context = getApplicationContext();
        //prepareInstance();

        init();

        getListMp3()
                .subscribeOn(Schedulers.io())
                .map(o -> {
                    Log.d("AAA", "thread" + Thread.currentThread().getName());
                    return ScanFileMp3.queryFileExternal(getApplicationContext());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Song>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Song> songs) {

                        Instance.baseSong.addAll(songs);
                        Instance.songList.addAll(songs);
                        Instance.songShuffleList.addAll(Instance.songList);
                        Collections.shuffle(Instance.songShuffleList);

                        searchs.clear();
                        for (Song song : songs) {
                            searchs.add(song.getNameEn());
                        }
                        adapterSearch.notifyDataSetChanged();

                        loadPlaylist();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        //                initDatabase();
                        //              loadPlaylist();
                        action();
                        setUpSearch();

                    }
                });


//        action();
//        setUpSearch();


    }

    private void setUpSearch() {
        RxSearch.fromSearchView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                //.filter(s -> !s.isEmpty())
                .distinctUntilChanged()
                .switchMap((Function<String, ObservableSource<String>>) s -> Observable.just(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        ShowLog.logInfo("query search", s);
                        adapterSearch.getFilter().filter(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        ShowLog.logInfo("searchview", "complete");
                    }
                });
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapterSearch = new SearchAdapter(Instance.songList, getApplicationContext());
        listSearch.setLayoutManager(layoutManager);
        listSearch.setAdapter(adapterSearch);
    }

    private void action() {


        //Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        Intent intent = new Intent(MainActivity.this, ListMusicActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        String s = "abx";

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            arrayList.add(i);
        }

        Observable.fromIterable(arrayList)
                .map(integer -> String.valueOf(integer.intValue()))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("AAA", "map covert arr: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        X.search(s.toCharArray())
                .flatMap(new Function<char[], ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(char[] chars) throws Exception {
                        return Observable.fromIterable(new ArrayList<>(Arrays.asList(chars)));
                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        X.search1(s)
                .flatMap(s1 -> Observable.fromIterable(Arrays.asList(s1.split(""))))
                .map(s1 -> s1.concat("x"))
                .observeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("AAA", "spilit: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private Observable<Integer> getANumberObservable() {
        return Observable.defer((Callable<ObservableSource<Integer>>) () -> Observable.just(1334));
    }

    private Observable<Object> getListMp3() {
        return Observable.defer(() -> Observable.just(1));
    }

    private void prepareInstance() {
        Instance.songList.clear();
        Instance.songList.addAll(ScanFileMp3.queryFileExternal(getApplicationContext()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int check = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            if (check == PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READ_EXTERNAL);
            }
        } else {
            Instance.songList.addAll(ScanFileMp3.queryFileInternal(getApplicationContext()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_READ_EXTERNAL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Instance.songList = ScanFileMp3.queryFileInternal(getApplicationContext());
                }
                break;
            }
        }
    }

    private void initDatabase() {
        String name = "playlist1";
        long state = MusicPlayer.createPlaylist(getApplicationContext(), name);

        long[] ids = new long[Instance.songList.size()];
        ShowLog.logInfo("size ids", ids.length);
        for (int i = 0; i < Instance.songList.size(); i++) {
            ids[i] = Instance.songList.get(i).getId();
        }

        //MusicPlayer.addToPlaylist(getApplicationContext(), ids, 127);
        if (state != -1) {
            ShowLog.logInfo("create playlist", "success");
            //MusicPlayer.addToPlaylist(getApplicationContext(), ids, 127);
        } else {
            ShowLog.logInfo("create playlist", "fail");
        }
    }

    private void loadPlaylist() {
        ArrayList<Playlist> playlists = PlaylistLoader.load(getApplicationContext());
        if (playlists != null) {
            Instance.playlists.addAll(playlists);
            for (Playlist playlist : playlists) {
                ArrayList<Song> songs = PlaylistSongLoader.getSongFromPlaylist(getApplicationContext(), playlist.getmId());
                ShowLog.logInfo("size " + playlist.getmName(), playlist.getmCount());
                for (Song song : songs) {
                    ShowLog.logInfo(playlist.getmName(), song.getNameVi());
                }
            }
        } else {
            ShowLog.logInfo("playlist", "null");
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapterSearch.getFilter().filter(newText);
        return false;
    }
}
