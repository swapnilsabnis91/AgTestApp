package com.agstar.testapp.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agstar.testapp.R;
import com.agstar.testapp.api.RequestHandler;
import com.agstar.testapp.api.event.ApiResponseMessageEvent;
import com.agstar.testapp.api.event.ExceptionEvent;
import com.agstar.testapp.api.model.response.Post;
import com.agstar.testapp.ui.adapter.TimelineAdapter;
import com.agstar.testapp.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment {
    private static final String TAG = "TimelineActivity";
    String sampleData = "[{\"commentCount\":574,\"likesCount\":547,\"postDescription\":\"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\\u0027s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\\n\\n\",\"postImageUrl\":\"https://images.pexels.com/photos/132037/pexels-photo-132037.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500\",\"postTitle\":\"Just Clicked!!!\",\"timestamp\":1567928906673,\"user\":{\"userId\":1,\"userImageUrl\":\"https://images.pexels.com/photos/2881622/pexels-photo-2881622.jpeg?auto\\u003dcompress\\u0026cs\\u003dtinysrgb\\u0026dpr\\u003d1\\u0026w\\u003d500\",\"userName\":\"Sam Jackson\"}},{\"commentCount\":574,\"likesCount\":547,\"postDescription\":\"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\\u0027s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\\n\\n\",\"postImageUrl\":\"https://images.pexels.com/photos/2774140/pexels-photo-2774140.jpeg?auto\\u003dcompress\\u0026cs\\u003dtinysrgb\\u0026dpr\\u003d1\\u0026w\\u003d500\",\"postTitle\":\"Just Clicked!!!\",\"timestamp\":1567928906673,\"user\":{\"userId\":2,\"userImageUrl\":\"https://images.pexels.com/photos/2881622/pexels-photo-2881622.jpeg?auto\\u003dcompress\\u0026cs\\u003dtinysrgb\\u0026dpr\\u003d1\\u0026w\\u003d500\",\"userName\":\"Sam S.\"}},{\"commentCount\":874,\"likesCount\":347,\"postDescription\":\"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\\u0027s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\\n\\n\",\"postImageUrl\":\"https://images.pexels.com/photos/2774140/pexels-photo-2774140.jpeg?auto\\u003dcompress\\u0026cs\\u003dtinysrgb\\u0026dpr\\u003d1\\u0026w\\u003d500\",\"postTitle\":\"Just Clicked!!!\",\"timestamp\":1567928906673,\"user\":{\"userId\":3,\"userImageUrl\":\"https://images.pexels.com/photos/2881622/pexels-photo-2881622.jpeg?auto\\u003dcompress\\u0026cs\\u003dtinysrgb\\u0026dpr\\u003d1\\u0026w\\u003d500\",\"userName\":\"Samule K\"}},{\"commentCount\":554,\"likesCount\":947,\"postDescription\":\"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\\u0027s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\\n\\n\",\"postImageUrl\":\"https://images.pexels.com/photos/2873895/pexels-photo-2873895.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500\",\"postTitle\":\"Just Clicked!!!\",\"timestamp\":1567928906673,\"user\":{\"userId\":4,\"userImageUrl\":\"https://images.pexels.com/photos/2881622/pexels-photo-2881622.jpeg?auto\\u003dcompress\\u0026cs\\u003dtinysrgb\\u0026dpr\\u003d1\\u0026w\\u003d500\",\"userName\":\"Sam Jackson\"}},{\"commentCount\":574,\"likesCount\":547,\"postDescription\":\"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\\u0027s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\\n\\n\",\"postImageUrl\":\"https://images.pexels.com/photos/2873992/pexels-photo-2873992.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500\",\"postTitle\":\"Just Clicked!!!\",\"timestamp\":1567928906673,\"user\":{\"userId\":5,\"userImageUrl\":\"https://images.pexels.com/photos/2881622/pexels-photo-2881622.jpeg?auto\\u003dcompress\\u0026cs\\u003dtinysrgb\\u0026dpr\\u003d1\\u0026w\\u003d500\",\"userName\":\"John D\"}}]";
    Type listType = new TypeToken<ArrayList<Post>>() {
    }.getType();
    Context mContext;
    Gson gson = new Gson();
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        initUi();


        List<Post> postList = gson.fromJson(sampleData, listType);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        TimelineAdapter adapter = new TimelineAdapter(postList, getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private void initUi() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Processing......");
        dialog.setCancelable(false);
        mContext = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtils.isNetworkAvailable(mContext)) {
            if (dialog != null && !dialog.isShowing())
                dialog.show();
            RequestHandler.getInstance().getMyActivities();
        } else {
            Toast.makeText(mContext, R.string.internet_error, Toast.LENGTH_SHORT).show();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ApiResponseMessageEvent.LoadActivities activities) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        //Process Activity Response to update ui on main thread
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ExceptionEvent.NetworkException e) {
        Log.e(TAG, "Error : " + e.toString());
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ExceptionEvent.ApiException e) {
        Log.e(TAG, "Error : " + e.toString());
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
