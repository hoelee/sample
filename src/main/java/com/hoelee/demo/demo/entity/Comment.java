package com.hoelee.demo.demo.entity;

import com.hoelee.demo.demo.exception.ExceptionJSONConversion;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Class desc:</p>
 * 
 * 
 * @version        v2, 2020-10-01 06:30:52PM
 * @author         hoelee
 */
public class Comment {
    
    private final Logger       log = LoggerFactory.getLogger(this.getClass());
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    public Comment(){
    }
    
    public Comment(int postId, int id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }
    
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void fromJSONObject(JSONObject jo) throws Exception{
        try{
            if(jo.has("postId"))
                postId = jo.getInt("postId");
            if (jo.has("id"))
                id = jo.getInt("id");
            if (jo.has("name"))
                name = jo.getString("name");
            if (jo.has("email"))
                email = jo.getString("email");
            if (jo.has("body"))
                body = jo.getString("body");
        }catch(Exception ex){
            log.error(ex.getLocalizedMessage());
            throw new ExceptionJSONConversion(ex.getLocalizedMessage());
        }
    }
    
    public JSONObject toJSONObject(){
        JSONObject jo = new JSONObject();
        jo.put("postId", postId);
        jo.put("id", id);
        jo.put("name", name);
        jo.put("email", email);
        jo.put("body", body);
        return jo;
    }
    
}

//~ v2, 2020-10-01 06:30:52PM - Last edited by hoelee
