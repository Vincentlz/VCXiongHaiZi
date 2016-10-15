package hai.xiong.redchilden.bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by spring on 2016/8/6.
 */
public class AddressListBean {


    /**
     * addresslist : [{"addressDetail":"长安街中南海1号","area":"北京市","areaId":1,"city":"北京市","cityId":1,"id":6,"isDefault":0,"userName":"习大大","phoneNumber":"18888888888","province":"北京市","provinceId":1,"userId":0,"zipCode":"100195"},{"addressDetail":"中关村99号","area":"北京市","areaId":1,"city":"北京市","cityId":1,"id":7,"isDefault":0,"userName":"小芳","phoneNumber":"13599996666","province":"北京市","provinceId":1,"userId":0,"zipCode":"100000"},{"addressDetail":"腾讯大厦总裁办公室","area":"深圳市","areaId":9,"city":"深圳市","cityId":9,"id":8,"isDefault":0,"userName":"马化腾","phoneNumber":"15888889999","province":"深圳市","provinceId":9,"userId":0,"zipCode":"100000"},{"addressDetail":"神经病","area":"北京市","areaId":1,"city":"北京市","cityId":1,"id":11,"isDefault":0,"userName":"帅哥1","phoneNumber":"13088138000","province":"北京市","provinceId":1,"userId":0},{"addressDetail":"itcast","area":"112","areaId":21,"city":"12","cityId":20,"fixedtel":"123","id":13,"isDefault":0,"userName":"qwe","phoneNumber":"123456","province":"1","provinceId":19,"userId":0,"zipCode":"123"},{"addressDetail":"长安街中南海1号","area":"北京市","areaId":1,"city":"北京市","cityId":1,"id":14,"isDefault":0,"userName":"习大大11","phoneNumber":"18888888888","province":"北京市","provinceId":1,"userId":0}]
     * request : addresslist
     */

    public String request;
    /**
     * addressDetail : 长安街中南海1号
     * area : 北京市
     * areaId : 1
     * city : 北京市
     * cityId : 1
     * id : 6
     * isDefault : 0
     * userName : 习大大
     * phoneNumber : 18888888888
     * province : 北京市
     * provinceId : 1
     * userId : 0
     * zipCode : 100195
     */

    public List<AddresslistBean> addresslist;

    public static class AddresslistBean implements Serializable {
        public String addressDetail;
        public String area;
        public int areaId;
        public String city;
        public int cityId;
        public int id;
        public int isDefault;
        public String name;
        public String phoneNumber;
        public String province;
        public int provinceId;
        public int userId;
        public String zipCode;
    }
}
