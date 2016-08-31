package com.example.oxionaz.mvpproject.view.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.R;

import java.util.List;

public class RepoAdapter extends BaseAdapter<Repository, RepoAdapter.ViewHolder> {

    private ViewHolder.ClickListener clickListener;

    public RepoAdapter(List<Repository> repoList, ViewHolder.ClickListener clickListener){
        super(repoList);
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
        return new ViewHolder(view, clickListener, data);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repository item = data.get(position);
        holder.id.setText(String.valueOf(item.getId()));
        holder.name.setText(item.getName());
        holder.lang.setText(item.getLanguage());
        holder.url.setText(item.getHtml_url());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView id, name, lang, url;
        private ClickListener clickListener;
        private List<Repository> data;

        public ViewHolder(View itemView, ClickListener clickListener, List<Repository> repoList){
            super(itemView);
            this.clickListener = clickListener;
            this.data = repoList;
            itemView.setOnClickListener(this);
            id = (TextView) itemView.findViewById(R.id.id);
            name = (TextView) itemView.findViewById(R.id.name);
            lang = (TextView) itemView.findViewById(R.id.language);
            url = (TextView) itemView.findViewById(R.id.url);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(data.get(getAdapterPosition()));
        }

        public interface ClickListener{
            void onItemClick(Repository repository);
        }
    }
}
