package com.example.oxionaz.mvpproject.view.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.oxionaz.mvpproject.R;
import com.raizlabs.android.dbflow.structure.Model;
import java.util.List;

public abstract class BaseSimpleAdapter
        <T extends Model> extends RecyclerView.Adapter<BaseSimpleAdapter.BaseViewHolder> {

    protected List<T> data;

    public BaseSimpleAdapter(List<T> data) {
        this.data = data;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected List<T> getData() {
        return data;
    }

    protected static class BaseViewHolder extends RecyclerView.ViewHolder {

        protected TextView text;

        public BaseViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text);
        }
    }

}