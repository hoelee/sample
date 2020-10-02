package com.hoelee.demo.demo.entity;

import com.hoelee.demo.demo.exception.ExceptionJSONConversion;

import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Class desc:</p>
 * 
 * 
 * @version        v2, 2020-10-02 11:47:11AM
 * @author         hoelee
 */
public class Post {

    private final Logger  log         = LoggerFactory.getLogger(this.getClass());
    // All the comments for this post
    private List<Comment> commentList = new LinkedList<>();
    private int           userId;
    private int           id;
    private String        title;
    private String        body;

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Constructor desc:</p>
     * 
     */
    public Post() {}

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Constructor desc:</p>
     * 
     * 
     * @param userId 
     * @param id 
     * @param title 
     * @param body 
     */
    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id     = id;
        this.title  = title;
        this.body   = body;
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * 
     * @return 
     */
    public int getUserId() {
        return userId;
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * 
     * 
     * @param userId 
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * 
     * @return 
     */
    public int getId() {
        return id;
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * 
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * 
     * @return 
     */
    public String getTitle() {
        return title;
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * 
     * 
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * 
     * @return 
     */
    public String getBody() {
        return body;
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * 
     * 
     * @param body 
     */
    public void setBody(String body) {
        this.body = body;
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * All the comments for this post
     * 
     * @return 
     */
    public List<Comment> getCommentList() {
        return commentList;
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * All the comments for this post
     * 
     * @param commentList 
     */
    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * 
     * 
     * @param jo 
     * 
     * @throws Exception 
     */
    public void fromJSONObject(JSONObject jo) throws Exception {
        try {
            if (jo.has("userId"))
                userId = jo.getInt("userId");
            if (jo.has("id"))
                id = jo.getInt("id");
            if (jo.has("title"))
                title = jo.getString("title");
            if (jo.has("body"))
                body = jo.getString("body");
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            throw new ExceptionJSONConversion(ex.getLocalizedMessage());
        }
    }

    /** hoelee v2 2020-10-02 11:47:11AM
     * <p>Method desc:</p>
     * 
     * @return 
     */
    public JSONObject toJSONObject() {
        JSONObject jo = new JSONObject();

        jo.put("userId", userId);
        jo.put("id", id);
        jo.put("title", title);
        jo.put("body", body);
        return jo;
    }
    
    public JSONObject toJSONObjectCustomResponse1(){
        JSONObject jo = new JSONObject();

        jo.put("post_id", id);
        jo.put("post_title", title);
        jo.put("post_body", body);
        jo.put("total_number_of_comments", commentList.size());
        return jo;
    }
    
}

//~ v2, 2020-10-02 11:47:11AM - Last edited by hoelee
