package zhuj.android.utils.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.util.concurrent.ExecutionException;

import zhuj.android.utils.Androids;
import zhuj.android.utils.Views;
import zhuj.android.utils.log.Logger;

public class GlideUtils {
    static boolean isShowDebug = false;

    private GlideUtils() {
    }

    private static final RequestOptions REQUEST_OPTIONS_NULL = new RequestOptions();
    public static final RequestOptions REQUEST_OPTIONS_BASE = new RequestOptions();

    private static final RequestListener<Drawable> requestListenerDrawable = new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            if (isShowDebug)
                Logger.d("GlideException e=%s, Object model=%s, Target<Drawable> target=%s, boolean isFirstResource=%s",
                        e != null ? e.getMessage() : null, model, target, isFirstResource);
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            if (isShowDebug)
                Logger.d("Drawable resource=%s, Object model=%s, Target<Drawable> target=%s, DataSource dataSource=%s, boolean isFirstResource=%s",
                        resource, model, target, dataSource, isFirstResource);
            return false;
        }
    };

    public static void loadImageView(ImageView imageView, Object url, @Nullable RequestOptions requestOptions) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions == null ? REQUEST_OPTIONS_NULL : requestOptions)
                .into(imageView);
    }

    public static void loadTextViewDrawable(TextView view, int direction, String url, RequestOptions requestOptions) {
        Glide.with(view.getContext())
                .load(url)
                .apply(requestOptions == null ? REQUEST_OPTIONS_NULL : requestOptions)
                .into(new CustomViewTarget<TextView, Drawable>(view) {
                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {
                        Views.setTextViewCompoundDrawables(view, placeholder, direction);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        Views.setTextViewCompoundDrawables(view, errorDrawable, direction);
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        Views.setTextViewCompoundDrawables(view, resource, direction);
                    }
                });
    }

    public static void loadMaterialButton(MaterialButton view, String url, RequestOptions requestOptions) {
        Glide.with(view.getContext())
                .load(url)
                .apply(requestOptions == null ? REQUEST_OPTIONS_NULL : requestOptions)
                .into(new CustomViewTarget<MaterialButton, Drawable>(view) {
                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {
                        view.setIcon(placeholder);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        view.setIcon(errorDrawable);
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        view.setIcon(resource);
                    }
                });
    }

    public static void loadViewBackground(View view, String url, RequestOptions requestOptions) {
        Glide.with(view.getContext())
                .load(url)
                .apply(requestOptions == null ? REQUEST_OPTIONS_NULL : requestOptions)
                .into(new CustomViewTarget<View, Drawable>(view) {
                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {
                        view.setBackground(placeholder);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        view.setBackground(errorDrawable);
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        view.setBackground(resource);
                    }
                });
    }

    // *****************   ????????????   ******************
    public static void download(String url, DownloadFileListener downloadFileListener) {
        download(Androids.getApp(), url, downloadFileListener);
    }

    public static void download(Context context, String url, DownloadFileListener downloadFileListener) {
        RequestBuilder<File> requestBuilder = Glide.with(context).download(url);

        final FutureTarget<File> fileFutureTarget = requestBuilder.submit();
        Androids.runOnWorkThread(new Runnable() {
            @Override
            public void run() {
                try {
                    downloadFileListener.onSuccess(fileFutureTarget.get());
                } catch (ExecutionException | InterruptedException e) {
                    downloadFileListener.onError(url, e);
                }
            }
        });
    }

    public static abstract class DownloadFileListener {
        public abstract void onSuccess(File file);

        public void onError(String uri, Exception e) {
            Logger.d("Glide DownloadFileListener", uri + " -> request error: " + e.getMessage());
        }
    }

}
