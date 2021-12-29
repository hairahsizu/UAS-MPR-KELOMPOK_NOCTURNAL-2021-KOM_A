package com.sayyid.uasmpr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.sayyid.uasmpr.R;
import com.sayyid.uasmpr.models.ResponseDataItem;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<ResponseDataItem> responseDataItemList;
    private List<ResponseDataItem> backup;
    private final Context context;
    private ResponseDataItem responseDataItem;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, ResponseDataItem responseDataItem, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    public DataAdapter(Context context) {
        this.context = context;
        responseDataItemList = new ArrayList<>();
        backup = new ArrayList<>();
    }

    private void add(ResponseDataItem item) {
        responseDataItemList.add(item);
        backup.add(item);
        notifyItemInserted(responseDataItemList.size() - 1);
        notifyDataSetChanged();
    }

    public void addAll(List<ResponseDataItem> responseDataItemList) {
        for (ResponseDataItem responseDataItem : responseDataItemList) {
            add(responseDataItem);
        }
        notifyDataSetChanged();
    }

    public void remove(int item) {
        responseDataItemList.remove(item);
        notifyItemRemoved(item);
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private void remove(ResponseDataItem item) {
        int position = responseDataItemList.indexOf(item);
        if (position > -1) {
            responseDataItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private ResponseDataItem getItem() {
        return responseDataItemList.get(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        responseDataItem = responseDataItemList.get(position);
        holder.bind(responseDataItem, position);
    }

    @Override
    public int getItemCount() {
        return responseDataItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView latlng,capital,countryName,TimeZone;
        private ImageView imageView;
        private View parentView;

        public ViewHolder(@NonNull View view) {
            super(view);
            latlng = view.findViewById(R.id.lat_lng);
            capital = view.findViewById(R.id.capital);
            countryName = view.findViewById(R.id.country_name);
            imageView = view.findViewById(R.id.image);
            parentView = view.findViewById(R.id.lyt_parent);
            TimeZone = view.findViewById(R.id.time_zone);
        }

        public void bind(final ResponseDataItem responseDataItem, final int position) {

            countryName.setText(MessageFormat.format("{0} ({1})",
                    responseDataItem.getName(), responseDataItem.getCountryCode()));
            latlng.setText(responseDataItem.getLatlng().toString());
            capital.setText(responseDataItem.getCapital());
            TimeZone.setText(responseDataItem.getTimezones().get(0).toString());

            imageView.setImageDrawable(setRandomImageText(position,
                    String.valueOf(countryName.getText().charAt(0))));

            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(itemView, responseDataItem, position);
                }
            });

        }

    }

    public static TextDrawable setRandomImageText(int position, String text){
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(position);
        String firstLetter = String.valueOf(text);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);


        return drawable;
    }

}

