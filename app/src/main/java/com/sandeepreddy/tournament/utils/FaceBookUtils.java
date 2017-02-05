package com.sandeepreddy.tournament.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sandeepreddy.tournament.R;
import com.sandeepreddy.tournament.TournamentApplication;
import com.sandeepreddy.tournament.db.User;
import com.sandeepreddy.tournament.db.UserDao;
import com.sandeepreddy.tournament.dto.FBUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FaceBookUtils {

    public static void updateCurrentUser(final Context context) {
        final UserDao userDao = TournamentApplication.getDaoSession().getUserDao();
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null) {
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me?fields=picture.type(large),name,id",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            JSONObject responseJSONObject = response.getJSONObject();

                            FBUser selectedUser = new Gson().fromJson(responseJSONObject.toString(), FBUser.class);
                            User user = new User();
                            user.setName(selectedUser.getName());
                            user.setFbId(selectedUser.getId());
                            user.setImageUrl(selectedUser.getPicture().getPictureData().getUrl());
                            user.setPinned(true);
                            user.setColor(ThemeColor.getRandomThemeColor().name());

                            long id = userDao.insert(user);
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                            sharedPreferencesEditor.putLong(context.getString(R.string.userId), id);
                            sharedPreferencesEditor.apply();
                        }
                    }
            ).executeAsync();
        }
    }

    public static void updateUserFriends(final Context context) {
        final UserDao userDao = TournamentApplication.getDaoSession().getUserDao();
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null) {
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/taggable_friends?limit=5000&fields=picture.type(large),name,id",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            JSONObject responseJSONObject = response.getJSONObject();
                            Type listType = new TypeToken<List<FBUser>>() {
                            }.getType();

                            List<FBUser> fbFriendList = new ArrayList<>();
                            try {
                                fbFriendList = new Gson().fromJson(responseJSONObject.getString("data"), listType);
                                Log.d("Sandeep", fbFriendList.size() + "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            List<User> userList = new ArrayList<>();
                            for (FBUser selectedUser : fbFriendList) {
                                User user = new User();
                                user.setName(selectedUser.getName());
                                user.setFbId(selectedUser.getId());
                                user.setImageUrl(selectedUser.getPicture().getPictureData().getUrl());
                                user.setPinned(false);
                                user.setColor(ThemeColor.getRandomThemeColor().name());

                                userList.add(user);
                            }


                            userDao.deleteAll();

                            userDao.insertInTx(userList);

                            updateCurrentUser(context);
                        }
                    }
            ).executeAsync();
        }
    }
}
