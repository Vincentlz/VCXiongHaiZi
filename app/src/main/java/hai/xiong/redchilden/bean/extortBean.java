package hai.xiong.redchilden.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 徐心意 on 2016/8/7.
 */
public class extortBean {
    /**
     * invoiceList : [{"content":"啤酒饮料,矿泉水","title":"公司单位"},{"content":"前排围观","title":"秦守"},{"content":"gogogogoogogogogoo","title":"参数太多了"},{"content":"0","title":"北京红孩子互联科技有限公司"},{"content":"0","title":"北京红孩子互联科技有限公司"},{"content":"0","title":"北京红孩子互联科技有限公司"},{"content":"0","title":"北京红孩子互联科技有限公司"},{"content":"1","title":"北京红孩子互联科技有限公司"},{"content":"1","title":"北京红孩子互联科技有限公司"},{"content":"1","title":"北京红孩子互联科技有限公司"},{"content":"1","title":"北京红孩子互联科技有限公司"}]
     * response : invoice
     */

    public String response;
    /**
     * content : 啤酒饮料,矿泉水
     * title : 公司单位
     */

    public List<InvoiceListBean> invoiceList;

    public static class InvoiceListBean implements Serializable {
        public String content;
        public String title;
        public int type;
        public String person;
        public String unit;
    }
}
