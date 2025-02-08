package com.tweetscan.raj;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterService {

    @Value("${twitter.api.url}")
    private String twitterApiUrl;

    @Value("${twitter.bearer.token}")
    private String bearerToken;

    private final OkHttpClient httpClient = new OkHttpClient();

    public List<com.tweetscan.raj.Tweet> getTweets(String userId) {
        List<com.tweetscan.raj.Tweet> tweets = new ArrayList<>();

        String url = twitterApiUrl + "/" + userId + "/tweets";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + bearerToken)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Failed to fetch tweets: " + response);
                return tweets;
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray data = jsonResponse.optJSONArray("data");

            if (data != null) {
                for (int i = 0; i < data.length(); i++) {
                    JSONObject tweetObj = data.getJSONObject(i);
                    String id = tweetObj.getString("id");
                    String contents = tweetObj.getString("text");
                    String createdAt = tweetObj.optString("created_at", "N/A");

                    tweets.add(new Tweet(id, contents, createdAt));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tweets;
    }
}
