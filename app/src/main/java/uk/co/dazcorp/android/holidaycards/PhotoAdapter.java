package uk.co.dazcorp.android.holidaycards;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import uk.co.dazcorp.android.holidaycards.data.Photo;

/**
 * Created by gentd on 22/04/2015.
 */
public class PhotoAdapter extends ArrayAdapter<Photo> {

    public PhotoAdapter(Context context, int resource,
            List<Photo> objects) {
        super(context, resource, objects);
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        Photo photo = getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_photo, parent, false);
            holder.photo = (ImageView) convertView.findViewById(R.id.item_photo_imageview);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.get().load(photo.url).into(holder.photo);

        return convertView;
    }

    static class ViewHolder {

        public ImageView photo;
    }
}
