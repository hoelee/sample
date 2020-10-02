package com.hoelee.demo.demo.helper;

import com.hoelee.demo.demo.entity.Comment;
import com.hoelee.demo.demo.entity.Post;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Class desc:</p>
 *
 *
 * @version        v2, 2020-09-30 03:14:36AM
 * @author         hoelee
 */
@Component
public class InternetHelper {

    private final Logger       log = LoggerFactory.getLogger(this.getClass());
    private HttpServletRequest request;

    /** hoelee v2 2020-10-01 06:10:46PM
     * <p>Method desc:</p>
     *
     *
     * @param request
     */
    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /** hoelee v2 2020-10-01 06:10:46PM
     * <p>Method desc:</p>
     *
     * @return
     */
    public List<Comment> readAllComments() {
        String epReadComments = "https://jsonplaceholder.typicode.com/comments";
        String response       = synchronizeRequestGetMethod(epReadComments);

        try {
            JSONArray     ja          = new JSONArray(response);
            List<Comment> commentList = new LinkedList<>();

            for(int a = 0; a < ja.length(); a++) {
                JSONObject jo      = ja.getJSONObject(a);
                Comment    comment = new Comment();

                comment.fromJSONObject(jo);
                commentList.add(comment);
            }

            return commentList;
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            return null;
        }
    }

    /** hoelee v2 2020-10-01 06:10:46PM
     * <p>Method desc:</p>
     *
     * @return
     */
    public List<Post> readAllPosts() {
        String epReadPosts = "https://jsonplaceholder.typicode.com/posts";
        String response    = synchronizeRequestGetMethod(epReadPosts);

        try {
            JSONArray  ja       = new JSONArray(response);
            List<Post> postList = new LinkedList<>();

            for(int a = 0; a < ja.length(); a++) {
                JSONObject jo   = ja.getJSONObject(a);
                Post       post = new Post();

                post.fromJSONObject(jo);
                postList.add(post);
            }

            return postList;
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            return null;
        }
    }

    /** hoelee v2 2020-10-01 06:10:46PM
     * <p>Method desc:</p>
     *
     *
     * @param postId
     * @return
     */
    public Post readPostById(int postId) {
        String epReadPost = "https://jsonplaceholder.typicode.com/posts/" + postId;
        String response   = synchronizeRequestGetMethod(epReadPost);

        try {
            JSONObject jo   = null;
            Post       post = new Post();

            jo = new JSONObject(response);

            post.fromJSONObject(jo);
            return post;
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            return null;
        }
    }

    /** hoelee v2 2020-10-01 06:10:46PM
     * <p>Method desc:</p>
     *
     *
     * @param commentId
     * @return
     */
    public Comment readCommentById(int commentId) {
        String epReadComment = "https://jsonplaceholder.typicode.com/comments/" + commentId;
        String response      = synchronizeRequestGetMethod(epReadComment);

        try {
            JSONObject jo      = new JSONObject(response);
            Comment    comment = new Comment();

            comment.fromJSONObject(jo);
            return comment;
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            return null;
        }
    }

    /** hoelee v2 2020-09-30 03:14:36AM
     * <p>Method desc:</p>
     *
     *
     * @param url
     * @return
     */
    private String synchronizeRequestGetMethod(String url) {
        CookieJar cookieJar = new CookieJar() {

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                // Save Cookies
                String urlString = url.toString();

                for(Cookie cookie : cookies) {
                    String cookieString = cookie.toString();
                }
            }
            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {

                // Load new cookies
                ArrayList<Cookie> cookies = new ArrayList<>();
                Cookie            cookie  = new Cookie.Builder().hostOnlyDomain(url.host()).name("key").value("value").build();

                cookies.add(cookie);
                return cookies;
            }
        };
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        // Add Parameters
        //urlBuilder.addQueryParameter("key", "value");
        String       finalUrl      = urlBuilder.build().toString();
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()    //
                .connectTimeout(60, TimeUnit.SECONDS).cookieJar(cookieJar).build();
        Request      request       = new Request.Builder().url(finalUrl).build();
        Call         call          = mOkHttpClient.newCall(request);
        Response     response      = null;
        String       body          = null;

        try {
            response = call.execute();

            if (response.isSuccessful())
                body = response.body().string();
            else
                log.warn("Http Get Request not success with URL: " + url);
        } catch (IOException e) {
            log.error("Http GET Request failed with URL: " + url + " because of " + e.getLocalizedMessage());
        } finally {
            try {
                response.close();
            } catch (Exception ex) {}
        }

        return body;
    }
}

//~ v2, 2020-10-02 11:37:44AM - Last edited by hoelee
