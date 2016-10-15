package hai.xiong.redchilden.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell on 2016/8/6.
 */
public class Category implements  Serializable{

    /**
     * category : [{"id":1,"isleafnode":false,"leafNode":false,"level":1,"userName":"孕妈专区","parentID":0,"parentid":0,"pic":"/images/category/category1.png","t":"妈妈专区,祛纹纤体"},{"id":2,"isleafnode":false,"leafNode":false,"level":1,"userName":"婴幼食品","parentID":0,"parentid":0,"pic":"/images/category/category2.png","t":"奶粉辅食,婴幼儿营养"},{"id":3,"isleafnode":false,"leafNode":false,"level":1,"userName":"宝宝用品","parentID":0,"parentid":0,"pic":"/images/category/category3.png","t":"尿裤喂养,用品纸巾"},{"id":4,"isleafnode":false,"leafNode":false,"level":1,"userName":"玩具童车","parentID":0,"parentid":0,"pic":"/images/category/category4.png","t":"婴儿床椅,婴儿车"},{"id":5,"isleafnode":false,"leafNode":false,"level":1,"userName":"寝具服饰","parentID":0,"parentid":0,"pic":"/images/category/category5.png","t":"童鞋婴,幼儿服饰"},{"id":11,"isleafnode":false,"leafNode":false,"level":2,"userName":"孕养营养品/奶粉","parentID":1,"parentid":1,"pic":"","t":""},{"id":12,"isleafnode":false,"leafNode":false,"level":2,"userName":"孕妇内衣","parentID":1,"parentid":1,"pic":"","t":""},{"id":13,"isleafnode":false,"leafNode":false,"level":2,"userName":"孕妇服饰","parentID":1,"parentid":1,"pic":"","t":""},{"id":14,"isleafnode":false,"leafNode":false,"level":2,"userName":"防辐射","parentID":1,"parentid":1,"pic":"","t":""},{"id":15,"isleafnode":false,"leafNode":false,"level":2,"userName":"胎心仪/孕妇枕","parentID":1,"parentid":1,"pic":"","t":""},{"id":16,"isleafnode":false,"leafNode":false,"level":2,"userName":"妈妈个人护理","parentID":1,"parentid":1,"pic":"","t":""},{"id":17,"isleafnode":false,"leafNode":false,"level":2,"userName":"母乳喂养用品","parentID":1,"parentid":1,"pic":"","t":""},{"id":18,"isleafnode":false,"leafNode":false,"level":3,"userName":"祛纹/纤体塑身","parentID":1,"parentid":1,"pic":"","t":""},{"id":101,"isleafnode":true,"leafNode":true,"level":3,"userName":"面部护理","parentID":16,"parentid":16,"pic":"","t":""},{"id":102,"isleafnode":true,"leafNode":true,"level":3,"userName":"洁牙护齿","parentID":16,"parentid":16,"pic":"","t":""},{"id":103,"isleafnode":true,"leafNode":true,"level":3,"userName":"身体护理","parentID":16,"parentid":16,"pic":"","t":""},{"id":104,"isleafnode":true,"leafNode":true,"level":3,"userName":"卫生巾","parentID":16,"parentid":16,"pic":"","t":""},{"id":105,"isleafnode":true,"leafNode":true,"level":3,"userName":"产褥/护理垫","parentID":16,"parentid":16,"pic":"","t":""},{"id":106,"isleafnode":true,"leafNode":true,"level":3,"userName":"眼部护理","parentID":16,"parentid":16,"pic":"","t":""},{"id":107,"isleafnode":true,"leafNode":true,"level":3,"userName":"洗手液","parentID":16,"parentid":16,"pic":"","t":""},{"id":108,"isleafnode":true,"leafNode":true,"level":3,"userName":"洗护套装","parentID":16,"parentid":16,"pic":"","t":""}]
     * response : category
     * version : 1.1
     */

    public String response;
    public String version;
    /**
     * id : 1
     * isleafnode : false
     * leafNode : false
     * level : 1
     * userName : 孕妈专区
     * parentID : 0
     * parentid : 0
     * pic : /images/category/category1.png
     * t : 妈妈专区,祛纹纤体
     */

    public List<CategoryBean> category;

    public static class CategoryBean implements Serializable {
        public int id;
        public boolean isleafnode;
        public boolean leafNode;
        public int level;
        public String name;
        public int parentID;
        public int parentid;
        public String pic;
        public String t;
    }
}
