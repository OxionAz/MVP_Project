package com.example.oxionaz.mvpproject.view;

import com.example.oxionaz.mvpproject.model.db.models.Info;
import java.util.List;

public interface View {
    void showList(List<Info> RepoList);
    String getUserName();
}
