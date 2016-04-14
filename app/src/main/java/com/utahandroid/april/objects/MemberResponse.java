package com.utahandroid.april.objects;

import java.util.ArrayList;

/**
 * Created by mgarner on 4/12/2016.
 * Helps facilitate communication and retrieval of information from the API. OkHttp only allows one looksie into the response body string.
 * This helps store that response body so that the total number of members and the members themselves can both be retrieved.
 */
public class MemberResponse {

    private ArrayList<Member> mMembers;
    private int mTotalMembers = 0;

    public MemberResponse(){

    }

    public void setMembers(ArrayList<Member> members) {
        mMembers = members;
    }

    public void setTotalMembers(int totalMembers) {
        mTotalMembers = totalMembers;
    }

    public ArrayList<Member> getMembers() {
        return mMembers;
    }

    public int getTotalMembers() {
        return mTotalMembers;
    }
}
