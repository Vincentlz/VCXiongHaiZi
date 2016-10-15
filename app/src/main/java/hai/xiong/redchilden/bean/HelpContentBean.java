package hai.xiong.redchilden.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LvYou on 2016/8/7.
 */
public class HelpContentBean implements Serializable{


    /**
     * help : [{"content":"本文档会结合工作与面试的常见问题来整理（每章最后一小节）。一般的面试题我会给出我自己的答案，但是我希望大家针对这些面试常见问题，能够按照自己的思路来整理答案。如果你们每天都有整理这些面试题，重点知识点。那么你们以后出去找工作肯定会非常顺利。","title":"如何配送"}]
     * response : help_detail
     */

    public String response;
    /**
     * content : 本文档会结合工作与面试的常见问题来整理（每章最后一小节）。一般的面试题我会给出我自己的答案，但是我希望大家针对这些面试常见问题，能够按照自己的思路来整理答案。如果你们每天都有整理这些面试题，重点知识点。那么你们以后出去找工作肯定会非常顺利。
     * title : 如何配送
     */

    public List<HelpBean> help;

    public static class HelpBean implements Serializable{
        public String content;
        public String title;
    }
}
