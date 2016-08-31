package com.example.oxionaz.mvpproject.view.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.oxionaz.mvpproject.R;
import com.raizlabs.android.dbflow.structure.Model;
import java.util.List;

public abstract class BaseAdapter
        <T extends Model, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> data;

    public BaseAdapter(List<T> data){
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected List<T> getData(){
        return data;
    }

}
