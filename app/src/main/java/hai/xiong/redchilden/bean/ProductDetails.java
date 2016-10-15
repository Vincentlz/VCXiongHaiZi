package hai.xiong.redchilden.bean;

import java.util.List;

/**
 * Created by dell on 2016/8/7.
 */
public class ProductDetails {
    @Override
    public String toString() {
        return "ProductDetails{" +
                "product=" + product +
                ", response='" + response + '\'' +
                '}';
    }

    /**
     * product : [{"available":false,"bigpic":["/images/lijingshan/product_01.jpg","/images/lijingshan/product_01.jpg","/images/lijingshan/product_01.jpg","/images/lijingshan/product_01.jpg","/images/lijingshan/product_01.jpg"],"brandid":2,"buylimit":"20","categoryid":1,"color":"","commentcount":23,"id":2,"inventoryarea":"","isgift":true,"ishotproduct":false,"islimitbuy":true,"isnewproduct":true,"lefttime":"3600","limitprice":20,"listfilter":"","marketprice":79,"userName":"三鹿    ","number":998,"pic":"/images/11.jpg","pics":["/images/lijingshan/product_04.jpg","/images/lijingshan/product_03.jpg"],"price":78,"productProm":[],"product_property":[{"id":1,"prodKey":"大小","prodValue":"M"},{"id":2,"prodKey":"颜色","prodValue":"蓝色"}],"productdesc":"此商品为测试商品,暂无商品描述信息","productprom":"","productproperty":"","sales":100,"score":111,"size":"","topicid":1}]
     * response : product
     */

    public String response;
    /**
     * available : false
     * bigpic : ["/images/lijingshan/product_01.jpg","/images/lijingshan/product_01.jpg","/images/lijingshan/product_01.jpg","/images/lijingshan/product_01.jpg","/images/lijingshan/product_01.jpg"]
     * brandid : 2
     * buylimit : 20
     * categoryid : 1
     * color :
     * commentcount : 23
     * id : 2
     * inventoryarea :
     * isgift : true
     * ishotproduct : false
     * islimitbuy : true
     * isnewproduct : true
     * lefttime : 3600
     * limitprice : 20
     * listfilter :
     * marketprice : 79
     * userName : 三鹿
     * number : 998
     * pic : /images/11.jpg
     * pics : ["/images/lijingshan/product_04.jpg","/images/lijingshan/product_03.jpg"]
     * price : 78
     * productProm : []
     * product_property : [{"id":1,"prodKey":"大小","prodValue":"M"},{"id":2,"prodKey":"颜色","prodValue":"蓝色"}]
     * productdesc : 此商品为测试商品,暂无商品描述信息
     * productprom :
     * productproperty :
     * sales : 100
     * score : 111
     * size :
     * topicid : 1
     */

    public List<ProductBean> product;

    public static class ProductBean {
        public boolean available;
        public int brandid;
        public String buylimit;
        public int categoryid;
        public String color;
        public int commentcount;
        public int id;
        public String inventoryarea;
        public boolean isgift;
        public boolean ishotproduct;
        public boolean islimitbuy;
        public boolean isnewproduct;
        public String lefttime;
        public int limitprice;
        public String listfilter;
        public int marketprice;
        public String name;
        public int number;
        public String pic;
        public int price;
        public String productdesc;
        public String productprom;
        public String productproperty;
        public int sales;
        public int score;
        public String size;
        public int topicid;
        public List<String> bigpic;
        public List<String> pics;
        public List<?> productProm;
        /**
         * id : 1
         * prodKey : 大小
         * prodValue : M
         */

        public List<ProductPropertyBean> product_property;

        public static class ProductPropertyBean {
            @Override
            public String toString() {
                return "ProductPropertyBean{" +
                        "id=" + id +
                        ", prodKey='" + prodKey + '\'' +
                        ", prodValue='" + prodValue + '\'' +
                        '}';
            }

            public int id;
            public String prodKey;
            public String prodValue;
        }

        @Override
        public String toString() {
            return "ProductBean{" +
                    "available=" + available +
                    ", brandid=" + brandid +
                    ", buylimit='" + buylimit + '\'' +
                    ", categoryid=" + categoryid +
                    ", color='" + color + '\'' +
                    ", commentcount=" + commentcount +
                    ", id=" + id +
                    ", inventoryarea='" + inventoryarea + '\'' +
                    ", isgift=" + isgift +
                    ", ishotproduct=" + ishotproduct +
                    ", islimitbuy=" + islimitbuy +
                    ", isnewproduct=" + isnewproduct +
                    ", lefttime='" + lefttime + '\'' +
                    ", limitprice=" + limitprice +
                    ", listfilter='" + listfilter + '\'' +
                    ", marketprice=" + marketprice +
                    ", name='" + name + '\'' +
                    ", number=" + number +
                    ", pic='" + pic + '\'' +
                    ", price=" + price +
                    ", productdesc='" + productdesc + '\'' +
                    ", productprom='" + productprom + '\'' +
                    ", productproperty='" + productproperty + '\'' +
                    ", sales=" + sales +
                    ", score=" + score +
                    ", size='" + size + '\'' +
                    ", topicid=" + topicid +
                    ", bigpic=" + bigpic +
                    ", pics=" + pics +
                    ", productProm=" + productProm +
                    ", product_property=" + product_property +
                    '}';
        }
    }

}
