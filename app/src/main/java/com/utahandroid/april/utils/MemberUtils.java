package com.utahandroid.april.utils;

import com.google.gson.Gson;
import com.utahandroid.april.BuildConfig;
import com.utahandroid.april.objects.Member;
import com.utahandroid.april.objects.MemberResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mgarner on 4/11/2016.
 * This Utils class helps perform actions on members and their data.
 */
@SuppressWarnings("unused")
public class MemberUtils {
    private static final String KEY_MEMBERS = "membership";
    private static final String KEY_TOTAL_MEMBERS = "membership_total";

    /**
     * Formulates query to API with input page size.
     * @param callback How shall I respond to said query?
     * @return Callback
     */
    public static Callback getMembers(Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BuildConfig.API_HITPOINT)
                .build();
        client.newCall(request).enqueue(callback);
        return callback;
    }

    /**
     * Takes the response from a query to the Members API and parses it into Objects.
     * @param response The response from the API
     * @return MemberResponse
     */
    public static MemberResponse handleMemberResponse(Response response) {
        MemberResponse memberResponse = new MemberResponse();
        ArrayList<Member> members = new ArrayList<>();
        int totalMembers = 0;
        if (response != null && response.isSuccessful()) {
            JSONObject responseJson;
            try {
                responseJson = new JSONObject(response.body().string());
                JSONArray membersArray = responseJson.optJSONArray(KEY_MEMBERS);
                totalMembers = responseJson.optInt(KEY_TOTAL_MEMBERS);
                if (membersArray != null && membersArray.length() > 0) {
                    Gson gson = new Gson();
                    for (int i = 0; i < membersArray.length(); i++) {
                        Member member = gson.fromJson(membersArray.getJSONObject(i).toString(), Member.class);
                        members.add(member);
                    }
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        memberResponse.setMembers(members);
        memberResponse.setTotalMembers(totalMembers);
        return memberResponse;
    }

    /**
     * Get the number of members in the response.
     * @param response The response from the API.
     * @return int number of members.
     */
    public static int getTotalMembers(Response response) {
        if (response != null && response.isSuccessful()) {
            JSONObject responseJson;
            try {
                responseJson = new JSONObject(response.body().string());
                return responseJson.optInt(KEY_TOTAL_MEMBERS);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
