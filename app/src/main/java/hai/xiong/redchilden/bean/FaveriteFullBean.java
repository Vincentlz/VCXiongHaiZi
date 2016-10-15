package hai.xiong.redchilden.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LvYou on 2016/8/7.
 */
public class FaveriteFullBean implements Serializable{

    /**
     * listCount : 8
     * productlist : [{"commentcount":1,"id":3,"isgift":false,"marketprice":79,"userName":"伊利QQ星","pic":"/images/12.jpg","price":100},{"commentcount":23,"id":2,"isgift":true,"marketprice":79,"userName":"三鹿    ","pic":"/images/11.jpg","price":78},{"commentcount":11,"id":5,"isgift":false,"marketprice":79,"userName":"伊利金装","pic":"/images/14.jpg","price":78},{"commentcount":232,"id":1021,"isgift":false,"marketprice":79,"userName":"七度空间 ","pic":"/images/10.jpg","price":78},{"commentcount":22453,"id":101211,"isgift":true,"marketprice":79,"userName":"可比克","pic":"/images/10.jpg","price":78},{"commentcount":22453,"id":1020,"isgift":true,"marketprice":79,"userName":"你妹牌饼干 ","pic":"/images/16.jpg","price":78},{"commentcount":55555,"id":4,"isgift":false,"marketprice":79,"userName":"特仑苏  ","pic":"/images/13.jpg","price":78},{"commentcount":0,"id":0,"isgift":true,"marketprice":0,"userName":"QQ糖","pic":"/images/10.jpg","price":0}]
     * response : favorites
     */

    public int listCount;
    public String response;
    /**
     * commentcount : 1
     * id : 3
     * isgift : false
     * marketprice : 79
     * userName : 伊利QQ星
     * pic : /images/12.jpg
     * price : 100
     */

    public List<ProductlistBean> productlist;

    public static class ProductlistBean implements Serializable{
        public int commentcount;
        public int id;
        public boolean isgift;
        public int marketprice;
        public String name;
        public String pic;
        public int price;
    }
}
