package hai.xiong.redchilden.bean;

import java.util.List;

/**
 * Created by spring on 2016/8/6.
 */
public class AddressSaveBean {

    /**
     * addressList : [{"areaDetail":"闵庄路3号","areaId":"1020304","cityId":"10203","fixedtel":"010-88496666","id":"1001","userName":"肖文","phoneNumber":"15801477821","provinceId":"102","zipCode":"100195"},{"areaDetail":"闵庄路3号","areaId":"1020304","cityId":"10203","fixedtel":"010-88496666","id":"1002","userName":"肖文","phoneNumber":"15801477821","provinceId":"102","zipCode":"100195"}]
     * response : addresssave
     */

    public String response;
    /**
     * areaDetail : 闵庄路3号
     * areaId : 1020304
     * cityId : 10203
     * fixedtel : 010-88496666
     * id : 1001
     * userName : 肖文
     * phoneNumber : 15801477821
     * provinceId : 102
     * zipCode : 100195
     */

    public List<AddressListBean> addressList;

    public static class AddressListBean {
        public String areaDetail;
        public String areaId;
        public String cityId;
        public String fixedtel;
        public String id;
        public String name;
        public String phoneNumber;
        public String provinceId;
        public String zipCode;
    }
}
