package com.hoelee.demo.demo.controller;

import com.hoelee.demo.demo.entity.Comment;
import com.hoelee.demo.demo.entity.Post;
import com.hoelee.demo.demo.helper.InternetHelper;
import com.hoelee.demo.demo.helper.ListHelper;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>Class desc:</p>
 *
 * @version        v2, 2020-09-30 03:02:39AM
 * @author         hoelee
 */
@Controller
@RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //
    @Autowired
    private InternetHelper internetHelper;

    @ResponseBody
    @GetMapping({"", "/"})
    private String home() {
        return "Hello World";
    }

    @ResponseBody
    @GetMapping("/post/topComment")
    private String question1TopCommentEndPoint() {
        List<Post>    postList    = internetHelper.readAllPosts();
        List<Comment> commentList = internetHelper.readAllComments();

        // Distribute respective Comment into each Post
        for(int a = 0; a < postList.size(); a++) {
            Post          post                   = postList.get(a);
            List<Comment> commentListCurrentPost = new LinkedList<>();

            for(int b = 0; b < commentList.size(); b++) {
                Comment comment = commentList.get(b);

                if (comment.getPostId() == post.getId())
                    commentListCurrentPost.add(comment);
            }

            post.setCommentList(commentListCurrentPost);
        }

        // Sort Post by highest number of comment
        Collections.sort(postList,
                         new Comparator<Post>() {

                             @Override
                             public int compare(Post post1, Post post2) {
                                 int post1CommentSize = post1.getCommentList().size();
                                 int post2CommentSize = post2.getCommentList().size();

                                 return post2CommentSize - post1CommentSize;
                             }

                         });

        // Response JSONArray
        JSONArray root = new JSONArray();

        for(int a = 0; a < postList.size(); a++) {
            root.put(postList.get(a).toJSONObjectCustomResponse1());
        }

        return root.toString();
    }

    /** hoelee v2 2020-10-02 12:40:33PM
     * <p>Method desc:</p>
     *
     *
     * @param postId
     * @param commentId
     * @param name
     * @param email
     * @param body
     * @param operator - provide 'and', 'or'
     * @return
     */
    @ResponseBody
    @GetMapping("/search")
    private String question2SearchComment(                                            //
            @RequestParam(value = "postId", required = false) String postId,          //
            @RequestParam(value = "commentId", required = false) String commentId,    //
            @RequestParam(value = "name", required = false) String name,              //
            @RequestParam(value = "email", required = false) String email,            //
            @RequestParam(value = "body", required = false) String body,              //
            @RequestParam(value = "operator", required = false) String operator       //
            ) {
        List<Comment> commentList = internetHelper.readAllComments();

        // Default no filter handler
        if ((postId == null) && (commentId == null) && (name == null) && (email == null) && (body == null)) {

            // Response JSONArray
            JSONArray root = new JSONArray();

            for(int a = 0; a < commentList.size(); a++) {
                root.put(commentList.get(a).toJSONObject());
            }

            return root.toString();
        }

        // Check operator
        boolean operatorAnd = false;
        boolean operatorOr  = false;

        if (!((operator == null) || operator.isEmpty())) {
            operator = operator.toLowerCase();

            if (operator == "and")
                operatorAnd = true;
            if (operator == "or")
                operatorOr = true;
        } else {

            // Default Operator
            operatorOr = true;
        }

        // Start the search
        if (operatorOr) {

            // Do operator OR
            List<Comment> commentListPostId    = new LinkedList<>();
            List<Comment> commnetListCommentId = new LinkedList<>();
            List<Comment> commentListName      = new LinkedList<>();
            List<Comment> commentListEmail     = new LinkedList<>();
            List<Comment> commentListBody      = new LinkedList<>();
            List<Comment> commentListResult    = new LinkedList<>();

            if (!((postId == null) || postId.isEmpty())) {

                // Remove unrelated post id
                for(int a = 0; a < commentList.size(); a++) {
                    Comment comment   = commentList.get(a);
                    int     postIdInt = Integer.parseInt(postId);

                    if (comment.getPostId() == postIdInt)
                        commentListPostId.add(comment);
                }
            }

            if (!((commentId == null) || commentId.isEmpty())) {

                // Remove unrelated comment id
                for(int a = 0; a < commentList.size(); a++) {
                    Comment comment      = commentList.get(a);
                    int     commentIdInt = Integer.parseInt(commentId);

                    if (comment.getId() == commentIdInt)
                        commnetListCommentId.add(comment);
                }
            }

            if (!((name == null) || name.isEmpty())) {

                // Remove unrelated comment id
                for(int a = 0; a < commentList.size(); a++) {
                    Comment comment = commentList.get(a);

                    if (comment.getName().contains(name))
                        commentListName.add(comment);
                }
            }

            if (!((email == null) || email.isEmpty())) {

                // Remove unrelated comment id
                for(int a = 0; a < commentList.size(); a++) {
                    Comment comment = commentList.get(a);

                    if (comment.getEmail().contains(email))
                        commentListEmail.add(comment);
                }
            }

            if (!((body == null) || body.isEmpty())) {

                // Remove unrelated comment id
                for(int a = 0; a < commentList.size(); a++) {
                    Comment comment = commentList.get(a);

                    if (comment.getBody().contains(body))
                        commentListBody.add(comment);
                }
            }

            // Remove duplicate Comment
            Set<Comment> commentSetResult = new HashSet<>();

            commentListResult.addAll(commentListPostId);
            commentListResult.addAll(commnetListCommentId);
            commentListResult.addAll(commentListName);
            commentListResult.addAll(commentListEmail);
            commentListResult.addAll(commentListBody);

            for(int a = 0; a < commentListResult.size(); a++) {
                commentSetResult.add(commentListResult.get(a));
            }

            // Response JSONArray
            JSONArray root = new JSONArray();

            for(Comment cmt : commentSetResult) {
                root.put(cmt.toJSONObject());
            }

            return root.toString();
        } else {

            // Do operator AND
            List<Comment> commentTotal = new LinkedList<>();

            if (!((postId == null) || postId.isEmpty())) {
                List<Comment> commentListResult = new LinkedList<>();

                // Remove unrelated post id
                for(int a = 0; a < commentList.size(); a++) {
                    Comment comment   = commentList.get(a);
                    int     postIdInt = Integer.parseInt(postId);

                    if (comment.getPostId() == postIdInt)
                        commentListResult.add(comment);
                }

                // Find out union result save to commentSetTotal
                if (commentTotal.isEmpty()) {
                    commentTotal.addAll(commentListResult);
                } else {
                    List<Comment> commentTotalNew = new LinkedList<>();

                    for(int a = 0; a < commentTotal.size(); a++) {
                        Comment cmt1 = commentTotal.get(a);

                        for(int b = 0; b < commentListResult.size(); b++) {
                            Comment cmt2 = commentListResult.get(b);

                            if (cmt1 == cmt2)
                                commentTotalNew.add(cmt2);
                        }
                    }

                    commentTotal = commentTotalNew;
                }
            }

            if (!((commentId == null) || commentId.isEmpty())) {
                List<Comment> commentListResult = new LinkedList<>();

                // Remove unrelated post id
                for(int a = 0; a < commentList.size(); a++) {
                    Comment comment      = commentList.get(a);
                    int     commentIdInt = Integer.parseInt(commentId);

                    if (comment.getId() == commentIdInt)
                        commentListResult.add(comment);
                }

                // Find out union result save to commentSetTotal
                if (commentTotal.isEmpty()) {
                    commentTotal.addAll(commentListResult);
                } else {
                    List<Comment> commentTotalNew = new LinkedList<>();

                    for(int a = 0; a < commentTotal.size(); a++) {
                        Comment cmt1 = commentTotal.get(a);

                        for(int b = 0; b < commentListResult.size(); b++) {
                            Comment cmt2 = commentListResult.get(b);

                            if (cmt1 == cmt2)
                                commentTotalNew.add(cmt2);
                        }
                    }

                    commentTotal = commentTotalNew;
                }
            }

            if (!((name == null) || name.isEmpty())) {
                List<Comment> commentListResult = new LinkedList<>();

                // Remove unrelated post id
                for(int a = 0; a < commentList.size(); a++) {
                    Comment comment = commentList.get(a);

                    if (comment.getName().contains(name))
                        commentListResult.add(comment);
                }

                // Find out union result save to commentSetTotal
                if (commentTotal.isEmpty()) {
                    commentTotal.addAll(commentListResult);
                } else {
                    List<Comment> commentTotalNew = new LinkedList<>();

                    for(int a = 0; a < commentTotal.size(); a++) {
                        Comment cmt1 = commentTotal.get(a);

                        for(int b = 0; b < commentListResult.size(); b++) {
                            Comment cmt2 = commentListResult.get(b);

                            if (cmt1 == cmt2)
                                commentTotalNew.add(cmt2);
                        }
                    }

                    commentTotal = commentTotalNew;
                }
            }

            if (!((email == null) || email.isEmpty())) {
                List<Comment> commentListResult = new LinkedList<>();

                // Remove unrelated post id
                for(int a = 0; a < commentList.size(); a++) {
                    Comment comment = commentList.get(a);

                    if (comment.getEmail().contains(email))
                        commentListResult.add(comment);
                }

                // Find out union result save to commentSetTotal
                if (commentTotal.isEmpty()) {
                    commentTotal.addAll(commentListResult);
                } else {
                    List<Comment> commentTotalNew = new LinkedList<>();

                    for(int a = 0; a < commentTotal.size(); a++) {
                        Comment cmt1 = commentTotal.get(a);

                        for(int b = 0; b < commentListResult.size(); b++) {
                            Comment cmt2 = commentListResult.get(b);

                            if (cmt1 == cmt2)
                                commentTotalNew.add(cmt2);
                        }
                    }

                    commentTotal = commentTotalNew;
                }
            }

            if (!((body == null) || body.isEmpty())) {
                List<Comment> commentListResult = new LinkedList<>();

                // Remove unrelated post id
                for(int a = 0; a < commentList.size(); a++) {
                    Comment comment = commentList.get(a);

                    if (comment.getBody().contains(body))
                        commentListResult.add(comment);
                }

                // Find out union result save to commentSetTotal
                if (commentTotal.isEmpty()) {
                    commentTotal.addAll(commentListResult);
                } else {
                    List<Comment> commentTotalNew = new LinkedList<>();

                    for(int a = 0; a < commentTotal.size(); a++) {
                        Comment cmt1 = commentTotal.get(a);

                        for(int b = 0; b < commentListResult.size(); b++) {
                            Comment cmt2 = commentListResult.get(b);

                            if (cmt1 == cmt2)
                                commentTotalNew.add(cmt2);
                        }
                    }

                    commentTotal = commentTotalNew;
                }
            }

            // Response JSONArray
            JSONArray root = new JSONArray();

            for(Comment cmt : commentTotal) {
                root.put(cmt.toJSONObject());
            }

            return root.toString();
        }
    }
}

//~ v2, 2020-10-02 03:53:15PM - Last edited by hoelee
