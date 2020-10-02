package com.hoelee.demo.demo.controller;

import com.hoelee.demo.demo.entity.Comment;
import com.hoelee.demo.demo.entity.Post;
import com.hoelee.demo.demo.helper.InternetHelper;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
public class TestController {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //
    @Autowired
    private InternetHelper internetHelper;
    
    @ResponseBody
    @GetMapping({"/1"})
    private String testSorting() {
        List<Comment> commentList  = internetHelper.readAllComments();
        List<Post>    postList     = new LinkedList<>();
        Post          post1        = new Post();
        List<Comment> commentList1 = new LinkedList<>();

        commentList1.add(new Comment());
        commentList1.add(new Comment());
        commentList1.add(new Comment());
        post1.setCommentList(commentList1);

        Post          post2        = new Post();
        List<Comment> commentList2 = new LinkedList<>();

        commentList2.add(new Comment());
        commentList2.add(new Comment());
        post2.setCommentList(commentList2);

        Post          post3        = new Post();
        List<Comment> commentList3 = new LinkedList<>();

        commentList3.add(new Comment());
        post3.setCommentList(commentList3);
        postList.add(post1);
        postList.add(post2);
        postList.add(post3);
        Collections.sort(postList,
                         new Comparator<Post>() {

                             @Override
                             public int compare(Post post1, Post post2) {
                                 int post1CommentSize = post1.getCommentList().size();
                                 int post2CommentSize = post2.getCommentList().size();

                                 return post2CommentSize - post1CommentSize;
                             }

                         });

        JSONArray root = new JSONArray();

        for(int a = 0; a < postList.size(); a++) {
            root.put(postList.get(a).toJSONObjectCustomResponse1());
        }

        return root.toString();
    }

    @GetMapping("/2")
    private String testThymeleaf() {
        return "testing.html";
    }
}
