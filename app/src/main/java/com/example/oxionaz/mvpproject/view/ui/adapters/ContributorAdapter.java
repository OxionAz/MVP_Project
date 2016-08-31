package com.example.oxionaz.mvpproject.view.ui.adapters;

import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import java.util.List;

public class ContributorAdapter extends BaseSimpleAdapter<Contributor> {

    public ContributorAdapter(List<Contributor> data) {
        super(data);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        String text = data.get(position).getLogin();
        holder.text.setText(text);
    }
}
