package com.example.oxionaz.mvpproject.view.ui.adapters;

import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import java.util.List;

public class BranchAdapter extends BaseSimpleAdapter<Branch> {

    public BranchAdapter(List<Branch> data) {
        super(data);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        String text = data.get(position).getName();
        holder.text.setText(text);
    }
}