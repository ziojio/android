package com.zhuj.comutils.image;

import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.io.File;

public interface AlbumLoader {

    AlbumLoader DEFAULT = new AlbumLoader() {
        @Override
        public void load(ImageView imageView, File albumFile) {
            load(imageView, albumFile.getPath());
        }

        @Override
        public void load(ImageView imageView, String url) {
            Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
        }
    };

    /**
     * Load a preview of the album file.
     *
     * @param imageView {@link ImageView}.
     * @param albumFile the media object may be a picture or video.
     */
    void load(ImageView imageView, File albumFile);

    /**
     * Load thumbnails of pictures or videos, either local file or remote file.
     *
     * @param imageView {@link ImageView}.
     * @param url       The url of the file, local path or remote path.
     */
    void load(ImageView imageView, String url);

}