package com.example.oxionaz.mvpproject.model.parselable;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;

import java.util.ArrayList;
import java.util.List;

public class InfoP implements Parcelable {

    public List<Branch> branchList;
    public List<Contributor> contributorList;

    public InfoP(List<Branch> branchList, List<Contributor> contributorList){
        this.branchList = branchList;
        this.contributorList = contributorList;
    }

    public InfoP(Parcel in) {
        this.branchList = in.readArrayList(null);
        this.contributorList = in.readArrayList(null);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(branchList);
        parcel.writeList(contributorList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InfoP> CREATOR = new Creator<InfoP>() {
        @Override
        public InfoP createFromParcel(Parcel in) {
            return new InfoP(in);
        }

        @Override
        public InfoP[] newArray(int size) {
            return new InfoP[size];
        }
    };
}
