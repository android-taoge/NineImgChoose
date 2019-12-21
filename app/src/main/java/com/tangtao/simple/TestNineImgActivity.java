package com.tangtao.simple;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tangtao.nineimgchooselayout.R;
import com.tangtao.nineimglayoutlib.widget.BaseNineImgAdapter;
import com.tangtao.nineimglayoutlib.widget.NineImgLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class TestNineImgActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 1;
    private NineImgLayout nineLayout;

    private MyNineAdapter adapter;

    private List<String> imgs = new ArrayList<>();
    private RxPermissions permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_nine_img);

       /* for (int i = 0; i < 8; i++) {
            imgs.add("http://img1.imgtn.bdimg.com/it/u=4238142487,3274484296&fm=26&gp=0.jpg");
        }*/

        nineLayout = findViewById(R.id.nineLayout);
        //nineLayout.setLayoutManager(new GridLayoutManager(this,5));
        nineLayout.setGridManager(this);
        adapter = new MyNineAdapter(this, imgs, R.layout.item_nine_layout, R.layout.item_empty_layout, 9);
        nineLayout.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseNineImgAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint("CheckResult")
            @Override
            public void onItemClick(BaseNineImgAdapter adapter, View view, int position) {
                //Toast.makeText(TestNineImgActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                permissions = new RxPermissions(TestNineImgActivity.this);
                permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    chooseImg();
                                } else {
                                    Toast.makeText(TestNineImgActivity.this, "该功能需要获取内部存储权限！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        adapter.setOnItemChildClickListener(new BaseNineImgAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseNineImgAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_close:
                        adapter.remove(position);
                        Toast.makeText(TestNineImgActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        });


    }

    private void chooseImg() {
        Matisse.from(TestNineImgActivity.this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(1)
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .showPreview(false) // Default is `true`
                .forResult(REQUEST_CODE_CHOOSE);
    }


    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }
}
