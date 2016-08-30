package com.example.oxionaz.mvpproject.model.sources.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchDTO {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("commitDTO")
    @Expose
    private CommitDTO commitDTO;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The commitDTO
     */
    public CommitDTO getCommitDTO() {
        return commitDTO;
    }

    /**
     * 
     * @param commitDTO
     *     The commitDTO
     */
    public void setCommitDTO(CommitDTO commitDTO) {
        this.commitDTO = commitDTO;
    }

}
