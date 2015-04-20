package com.idashcam.intelligentdashcam.View.Popup.Adapter.Video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.idashcam.intelligentdashcam.R;

import java.util.List;

/**
 * Created by alexandre on 01/04/2015.
 */
public class AdapterFragSettingVideoQualitySelector extends BaseAdapter {

    private String newQuality =null;
    private LayoutInflater inflater;
    private List<String> listQuality;

    @Override
    public int getCount() {
        return listQuality.size();
    }

    @Override
    public Object getItem(int position) {
        return listQuality.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setNewQuality(String newQuality) {
        this.newQuality = newQuality;
    }

    private class ViewHolder{
        ImageView Select;
        TextView QualityName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView ==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragments_selector,null);

            holder.QualityName = (TextView) convertView.findViewById(R.id.txt_fragments_list);
            holder.Select = (ImageView) convertView.findViewById(R.id.imgv_fragments_list);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.QualityName.setText(listQuality.get(position));

        if (!newQuality.isEmpty()) {
            if (newQuality.equals(listQuality.get(position))) {
                holder.Select.setImageResource(R.drawable.cheked2);
            } else {
                holder.Select.setImageResource(R.drawable.uncheked2);
            }
        }
        return convertView;
    }

    public AdapterFragSettingVideoQualitySelector(Context context,List<String> listQuality, String actualQuality) {
        inflater = LayoutInflater.from(context);
        this.listQuality = listQuality;
        this.newQuality = actualQuality;
    }
}
