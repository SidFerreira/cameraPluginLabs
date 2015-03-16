package br.com.ferreiraz.fullcamera;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import us.feras.ecogallery.EcoGallery;

/**
 * Created by sidferreira on 16/03/15.
 */
public class FzPhotosAdapter extends BaseAdapter {
    protected Context context;
    protected List<ResultClass> items;

    public FzPhotosAdapter(List<ResultClass> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public int getCount() {
        return (items != null) ? items.size() : 0;
    }

    public Object getItem(int position) {
        return items.get(position).scaled;
    }

    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setAdjustViewBounds(true);
            EcoGallery.LayoutParams layoutParams = new EcoGallery.LayoutParams(EcoGallery.LayoutParams.WRAP_CONTENT, EcoGallery.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(items.get(position).getScaled());

        return imageView;
    }
}
