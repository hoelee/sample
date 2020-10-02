package com.hoelee.demo.demo.helper;

import com.hoelee.demo.demo.entity.Comment;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * Class desc:</p>
 *
 * @version v2, 2020-10-02 02:48:54PM
 * @author hoelee
 */
public class ListHelper {

    /**
     * hoelee v2 2020-10-02 02:48:54PM
     * <p>
     * Method desc:</p>
     * Search same Comment in give 2 Comment LinkedList
     *
     * @param l1
     * @param l2
     * @return
     */
    public static List findSameCommentInList(List l1, List l2) {
        // If l2 empty no need find
        if (l2.isEmpty())
            return l1;
        
        List result = new LinkedList<>();

        for (int a = 0; a < l1.size(); a++) {
            Comment l1Obj = (Comment) l1.get(a);
            boolean gotSame = false;

            for (int b = 0; b < l2.size(); b++) {
                Comment l2Obj = (Comment) l2.get(b);

                if (l1Obj == l2Obj) {
                    gotSame = true;
                    break;
                }
            }

            if (gotSame) {
                result.add(l1Obj);
            }
        }

        return result;
    }
}

//~ v2, 2020-10-02 02:48:54PM - Last edited by hoelee
