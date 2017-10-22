package test.bwie.com.lianxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by dell on 2017/10/21.
 */

public class SecondActivity extends Activity {

    private SwipeRefreshLayout swiperefreshlayout;
    private RecyclerView recyclerViewview;
    private LinearLayoutManager linearLayoutManager;
    private ImageLoader imageLoader;
    List<Bean1.DataBean> data=new ArrayList<>();
    //    private String path="http://apiv3.yangkeduo.com/v5/newlist?page=1&size=20";
    private String path = "http://120.27.23.105/product/getProducts?pscid=";
    private int p=1;
    private Myadapter myadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity);
        swiperefreshlayout = findViewById(R.id.swipe);
        recyclerViewview = findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewview.setLayoutManager(linearLayoutManager);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                p++;
                getDates();
                myadapter.notifyDataSetChanged();
                swiperefreshlayout.setRefreshing(false);
            }
        });


        recyclerViewview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if(lastVisibleItemPosition==data.size()-1){
                    p++;
                    getDates();
                    myadapter.notifyDataSetChanged();
                }
            }
        });






        getDates();


    }

    public void getDates() {
        OkHttp.getAsync(path+p, new OkHttp.DataCallBack() {



            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
//               Gowu gowu = gson.fromJson(result, Gowu.class);
                Bean1 bean1 = gson.fromJson(result, Bean1.class);
                List<Bean1.DataBean> data = bean1.getData();
                myadapter = new Myadapter(data);
                recyclerViewview.setAdapter(myadapter);

            }
        });


    }

    class Myadapter extends RecyclerView.Adapter {
        List<Bean1.DataBean> data;
        private MyViewHolder myviewholder;
        private final DisplayImageOptions options;

        public Myadapter(List<Bean1.DataBean> data) {
            this.data = data;
            imageLoader = ImageLoader.getInstance();
            File file = new File(Environment.getExternalStorageDirectory(), "Bwei");
            if (!file.exists())
                file.mkdirs();
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(SecondActivity.this)
                    .diskCache(new UnlimitedDiskCache(file))
                    .build();
            imageLoader.init(configuration);
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .cacheOnDisk(true)
                    .build();
        }

        @Override
        public int getItemViewType(int position) {
            if (position % 4 == 1) {
                return 0;
            } else {
                return 1;
            }


        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == 0) {
                View view2 = LayoutInflater.from(SecondActivity.this).inflate(R.layout.item2, parent, false);
                return new MyViewHolder2(view2);
            } else {
                View view1 = LayoutInflater.from(SecondActivity.this).inflate(R.layout.item, parent, false);
                return new MyViewHolder(view1);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            int itemViewType = getItemViewType(position);
            switch (itemViewType) {
                case 0:
                    MyViewHolder2 holder2 = (MyViewHolder2) holder;
                    holder2.textf1.setText(data.get(position).getTitle());
                    String pic = data.get(position).getImages();
                    String[] split = pic.split("\\|");
                    getimage(split[0], holder2.imaged1);
                    holder2.textf1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(SecondActivity.this, ThreeActivity.class);
                            intent.putExtra("name1",data.get(position).getTitle());
                            startActivity(intent);
                        }
                    });
                    break;
                case 1:
                    myviewholder = (MyViewHolder) holder;
                    myviewholder.textView.setText(data.get(position).getTitle());
                    String pic1 = data.get(position).getImages();
                    String[] split1 = pic1.split("\\|");
                    getimage(split1[0], myviewholder.imageView);
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            private final TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imaged);
                textView = itemView.findViewById(R.id.textf);
            }
        }

        class MyViewHolder2 extends RecyclerView.ViewHolder {


            private final ImageView imaged1;
            private final TextView textf1;

            public MyViewHolder2(View itemView) {
                super(itemView);
                imaged1 = itemView.findViewById(R.id.imaged1);
                textf1 = itemView.findViewById(R.id.textf1);
            }
        }


    }

    public void getimage(String path, ImageView imageView) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
        ImageLoader.getInstance().displayImage(path, imageView, options);


    }


}
