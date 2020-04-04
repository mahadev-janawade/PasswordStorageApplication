package com.example.maddy.multiapps;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maddy on 3/27/2020.
 */

public class CursorUserDetails {

    public List<UserDetails> userDetails;
    public List<UserDetailsViewId> userDetailsViewIds;
    public List<Integer> editId;
    public List<Integer> deleteId;
    public List<Integer> viewId;


    public CursorUserDetails()
    {
        userDetails = new ArrayList<UserDetails>();
        userDetailsViewIds = new ArrayList<UserDetailsViewId>();
        editId = new ArrayList<Integer>();
        deleteId = new ArrayList<Integer>();
        viewId = new ArrayList<Integer>();

    }

    public void addDetails(UserDetails userDetails,UserDetailsViewId userDetailsViewId) {
        this.userDetails.add(userDetails);
        this.userDetailsViewIds.add(userDetailsViewId);
    }

    public void addIds(Integer editId, Integer deleteId, Integer viewId)
    {
        this.editId.add(editId);
        this.deleteId.add(deleteId);
        this.viewId.add(viewId);
    }

}
