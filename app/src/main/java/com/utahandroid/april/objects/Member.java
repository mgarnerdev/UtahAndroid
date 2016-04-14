package com.utahandroid.april.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgarner on 4/11/2016.
 * Object that models Members retrieved from the Utah Android Members Test API. Implements Parcelable for use in intents.
 */
@SuppressWarnings("unused")
public class Member implements Parcelable {

    private static final String KEY_MEMBER_ID = "id";
    private static final String KEY_MEMBER_NAME = "name";
    private static final String KEY_MEMBER_URL = "url_member_profile";
    private static final String KEY_MEMBER_LOCATION = "location";
    private static final String KEY_MEMBER_DESCRIPTION_WILLING_TO_PRESENT = "description_willing_to_present";
    private static final String KEY_MEMBER_DESCRIPTION_WANT_TO_GET_FROM_GROUP = "description_want_to_get_from_group";
    private static final String KEY_MEMBER_DESCRIPTION_EXPERIENCE = "description_experience";
    private static final String KEY_MEMBER_DATE_LAST_ATTENDED = "date_last_attended";
    private static final String KEY_MEMBER_DATE_JOINED = "date_joined";

    /**
     * SAMPLE JSON
     * date_joined: "1/23/2015",
     * date_last_attended: "3/10/2016",
     * description_experience: "I have been developing Android apps since 2009 when I got my hands on my first Android phone which I received at Google I/O that year.",
     * description_want_to_get_from_group: "I hope to learn some new skills, sharpen the ones that I have, and make some friends.",
     * description_willing_to_present: "Absolutely",
     * id: 1,
     * location: "Lehi, UT",
     * name: "Dustin Graham",
     * url_member_profile: "http://www.meetup.com/Utah-County-Android-Developers/members/121354892/"
     */

    @SerializedName(KEY_MEMBER_ID)
    private String mId;
    @SerializedName(KEY_MEMBER_NAME)
    private String mName;
    @SerializedName(KEY_MEMBER_URL)
    private String mProfileUrl;
    @SerializedName(KEY_MEMBER_DATE_JOINED)
    private String mDateJoined;
    @SerializedName(KEY_MEMBER_DATE_LAST_ATTENDED)
    private String mDateLastAttended;
    @SerializedName(KEY_MEMBER_LOCATION)
    private String mLocation;
    @SerializedName(KEY_MEMBER_DESCRIPTION_EXPERIENCE)
    private String mDescriptionExperience;
    @SerializedName(KEY_MEMBER_DESCRIPTION_WANT_TO_GET_FROM_GROUP)
    private String mDescriptionWantToGetFromGroup;
    @SerializedName(KEY_MEMBER_DESCRIPTION_WILLING_TO_PRESENT)
    private String mDescriptionWillingToPresent;

    /**
     * Default constructor.
     */
    public Member() {

    }

    protected Member(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mProfileUrl = in.readString();
        mDateJoined = in.readString();
        mDateLastAttended = in.readString();
        mLocation = in.readString();
        mDescriptionExperience = in.readString();
        mDescriptionWantToGetFromGroup = in.readString();
        mDescriptionWillingToPresent = in.readString();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getProfileUrl() {
        return mProfileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        mProfileUrl = profileUrl;
    }

    public String getDateJoined() {
        return mDateJoined;
    }

    public void setDateJoined(String dateJoined) {
        mDateJoined = dateJoined;
    }

    public String getDateLastAttended() {
        return mDateLastAttended;
    }

    public void setDateLastAttended(String dateLastAttended) {
        mDateLastAttended = dateLastAttended;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getDescriptionExperience() {
        return mDescriptionExperience;
    }

    public void setDescriptionExperience(String descriptionExperience) {
        mDescriptionExperience = descriptionExperience;
    }

    public String getDescriptionWantToGetFromGroup() {
        return mDescriptionWantToGetFromGroup;
    }

    public void setDescriptionWantToGetFromGroup(String descriptionWantToGetFromGroup) {
        mDescriptionWantToGetFromGroup = descriptionWantToGetFromGroup;
    }

    public String getDescriptionWillingToPresent() {
        return mDescriptionWillingToPresent;
    }

    public void setDescriptionWillingToPresent(String descriptionWillingToPresent) {
        mDescriptionWillingToPresent = descriptionWillingToPresent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mProfileUrl);
        dest.writeString(mDateJoined);
        dest.writeString(mDateLastAttended);
        dest.writeString(mLocation);
        dest.writeString(mDescriptionExperience);
        dest.writeString(mDescriptionWantToGetFromGroup);
        dest.writeString(mDescriptionWillingToPresent);
    }
}
