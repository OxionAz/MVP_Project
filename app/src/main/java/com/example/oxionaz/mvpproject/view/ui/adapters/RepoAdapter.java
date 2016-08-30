package com.example.oxionaz.mvpproject.view.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.R;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private List<Repository> repoList;
    private ViewHolder.ClickListener clickListener;

    public RepoAdapter(List<Repository> repoList, ViewHolder.ClickListener clickListener){
        this.repoList = repoList;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
        return new ViewHolder(item, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repository item = repoList.get(position);
        holder.id.setText(String.valueOf(item.getId()));
        holder.name.setText(item.getName());
        holder.lang.setText(item.getLanguage());
        holder.url.setText(item.getHtml_url());
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView id, name, lang, url;
        private ClickListener clickListener;

        public ViewHolder(View itemView, ClickListener clickListener){
            super(itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
            id = (TextView) itemView.findViewById(R.id.id);
            name = (TextView) itemView.findViewById(R.id.name);
            lang = (TextView) itemView.findViewById(R.id.language);
            url = (TextView) itemView.findViewById(R.id.url);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition());
        }

        public interface ClickListener{
            void onItemClick(int position);
        }
    }
}
