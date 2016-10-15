package hai.xiong.redchilden.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/8/7.
 */
public class ProductLists {

    /**
     * listCount : 8
     * productlist : [{"commentcount":1,"id":3,"marketprice":79,"userName":"伊利QQ星","pic":"/images/12.jpg","price":100,"sales":18},{"commentcount":23,"id":2,"marketprice":79,"userName":"三鹿    ","pic":"/images/11.jpg","price":78,"sales":100},{"commentcount":55555,"id":4,"marketprice":79,"userName":"特仑苏  ","pic":"/images/13.jpg","price":78,"sales":100},{"commentcount":11,"id":5,"marketprice":79,"userName":"伊利金装","pic":"/images/14.jpg","price":78,"sales":100},{"commentcount":22453,"id":1020,"marketprice":79,"userName":"你妹牌饼干 ","pic":"/images/16.jpg","price":78,"sales":100},{"commentcount":232,"id":1021,"marketprice":79,"userName":"七度空间 ","pic":"/images/10.jpg","price":78,"sales":100},{"commentcount":23,"id":10211,"marketprice":79,"userName":"护舒宝   ","pic":"/images/1.bmp","price":78,"sales":100},{"commentcount":22453,"id":101211,"marketprice":79,"userName":"可比克","pic":"/images/10.jpg","price":78,"sales":100}]
     * response : category_productlist
     */

    public int listCount;
    public String response;
    /**
     * commentcount : 1
     * id : 3
     * marketprice : 79
     * userName : 伊利QQ星
     * pic : /images/12.jpg
     * price : 100
     * sales : 18
     */

    public ArrayList<ProductlistBean> productlist;

    public static class ProductlistBean {
        public int commentcount;
        public int id;
        public int marketprice;
        public String name;
        public String pic;
        public int price;
        public int sales;
    }
}
