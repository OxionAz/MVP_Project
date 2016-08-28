package com.example.oxionaz.mvpproject.view.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oxionaz.mvpproject.model.db.models.Info;
import com.example.oxionaz.mvpproject.model.rest.models.Repo;
import com.example.oxionaz.mvpproject.R;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private List<Info> repoList;

    public RepoAdapter(List<Info> repoList){
        this.repoList = repoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Info item = repoList.get(position);
        holder.id.setText(String.valueOf(item.getId()));
        holder.name.setText(item.getName());
        holder.lang.setText(item.getLanguage());
        holder.url.setText(item.getHtml_url());
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView id, name, lang, url;

        public ViewHolder(View itemView){
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            name = (TextView) itemView.findViewById(R.id.name);
            lang = (TextView) itemView.findViewById(R.id.language);
            url = (TextView) itemView.findViewById(R.id.url);
        }
    }
}
