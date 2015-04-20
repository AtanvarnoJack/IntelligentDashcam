package com.idashcam.intelligentdashcam.View.Popup.Adapter.Map;

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
 * Created by alexandre on 17/04/2015.
 */
public class AdapterFragSettingList extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> list;
    private String newValue;

    public AdapterFragSettingList( Context context, List<String> list, String newValue) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.newValue = newValue;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView Select;
        TextView Name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView ==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragments_selector,null);

            holder.Name = (TextView) convertView.findViewById(R.id.txt_fragments_list);
            holder.Select = (ImageView) convertView.findViewById(R.id.imgv_fragments_list);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.Name.setText(list.get(position));

        if (!newValue.isEmpty()) {
            if (newValue.equals(list.get(position))) {
                holder.Select.setImageResource(R.drawable.cheked2);
            } else {
                holder.Select.setImageResource(R.drawable.uncheked2);
            }
        }
        return convertView;
    }
}
