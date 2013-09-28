package com.tropicdreams.lyrify.adapter;

import java.util.ArrayList;
import java.util.HashMap;






import com.tropicdreams.lyrify.R;
import com.tropicdreams.lyrify.fragments.TrackList;
import com.tropicdreams.lyrify.image.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TrackAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public TrackAdapter(Activity a, ArrayList<HashMap<String, String>> trackListItems) {
        activity = a;
        data=trackListItems;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.track_list_row, null);

        TextView name=(TextView)vi.findViewById(R.id.list_name);
        TextView album=(TextView)vi.findViewById(R.id.list_vic);
        ImageView image=(ImageView)vi.findViewById(R.id.list_image);
        
        name.setText(data.get(position).get(TrackList.KEY_TRACK_NAME));
        album.setText(data.get(position).get(TrackList.KEY_ARTIST_NAME));
        imageLoader.DisplayImage(data.get(position).get(TrackList.KEY_ICON), image);
        return vi;
    }
}