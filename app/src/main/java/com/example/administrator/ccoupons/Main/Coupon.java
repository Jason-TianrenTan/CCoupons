package com.example.administrator.ccoupons.Main;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class Coupon implements Serializable {

    private static final int STAT_ONSALE = 1;
    private static final int STAT_EXPIRED = 2;
    private static final int STAT_USED = 3;
    private static final int STAT_STORE = 4;

<<<<<<< HEAD

    private String address;//地址
    private String name;//=>product 优惠券名字
    private String couponId;
    private String brandName;//品牌id ->可口可乐、吮指原味鸡->肯德基
    private String catId;//类别
    private double listPrice;//用户列出来的价格
    private double evaluatePrice;//估值价格 =>value
    private double discount;//打折多少 20表示20元
    private int stat;//状态 详见下
    private String imgURL;//url
    private String expireDate;//过期时间
    private String[] constraints;//限制

    private String sellerNickname;//卖家名字
    private String sellerAvatarURL;//头像URL

    /*
    状态:
    onSale => 正在卖
    expired =>过期的
    used  =>用过的
    store =>储存的
     */
    public Coupon() {
=======
    private boolean liked = false;
    //地址
    private String address;
>>>>>>> ttr

    //类别
    @SerializedName("category")
    private String catId;

<<<<<<< HEAD
<<<<<<< HEAD
    public Coupon(String name, String couponId, String brand, String catId, double listPrice, double evaluatePrice, double discount, int stat, String imgURL, String expireDate) {
        this.name = name;
        this.couponId = couponId;
        this.brandName = brand;
        this.catId = catId;
        this.listPrice = listPrice;
        this.evaluatePrice = evaluatePrice;
        this.discount = discount;
        this.stat = stat;
        this.imgURL = imgURL;
        this.expireDate = expireDate;
    }

    public void setName(String str) {
        this.name = str;
    }


=======
=======

>>>>>>> Czj
    //品牌
    @SerializedName("brand")
    private String brandName;

    //状态
    private int stat;

    //卖家id
    private String sellerId;

    // 限制
    private String[] constraints;

    //卖家名字
    private String sellerNickname;

    // 头像URL
    private String sellerAvatarURL;

    private String couponid;

    private String product;

    private String listprice;

    private String value;

    private String expiredtime;

    private String discount;

    private String pic;

    public Coupon() {

    }


    //地址
>>>>>>> ttr
    public void setAddress(String str) {
        this.address = str;
    }

    public void setDetail(String str) {
        this.expireDate = str;
    }

    //商家信息
    public void setBrandName(String str) {
        this.brandName = str;
    }
    public String getBrandName() {
        return this.brandName;
    }

<<<<<<< HEAD
=======
    public String getSellerId() {
        return this.sellerId;
    }

>>>>>>> ttr
    //卖家昵称
    public void setSellerName(String str) {
        this.sellerNickname = str;
    }
    public String getSellerNickname() {
        return this.sellerNickname;
    }
    public void setSellerAvatarURL(String url) {
        this.sellerAvatarURL = url;
    }
    public String getSellerAvatarURL() {
        return this.sellerAvatarURL;
    }


    //使用限制
    public void setConstraints(String[] arr) {
        this.constraints = arr;
    }

    public String[] getConstraints() {
        return this.constraints;
    }


<<<<<<< HEAD
    public void setImgURL(String url) {
        this.imgURL = url;
    }

    public void setListPrice(double price) {
        this.listPrice = price;
    }

    public void setEvaluatePrice(double price) {
        this.evaluatePrice = price;
    }

    public String getAddress() {
        return this.address;
    }


    public String getName() {
        return this.name;
    }

    public double getListPrice() {
        return this.listPrice;
    }

    public double getEvaluatePrice() {
        return this.evaluatePrice;
=======
    public void setCategory(String cat) {
        this.catId = cat;
>>>>>>> ttr
    }

    public String getCategory() {
        return this.catId;
    }


<<<<<<< HEAD
    public String getCouponId() {
        return this.couponId;
    }

    public double getDiscount() {
        return this.discount;
    }

=======
>>>>>>> ttr
    public int getStat() {
        return this.stat;
    }

<<<<<<< HEAD
    public String getImgURL() {
        return this.imgURL;
    }

    public String getExpireDate() {
        return this.expireDate;
    }

=======
>>>>>>> ttr

    /**
     * set current state of coupon
     * @param statStr
     */
    private void setCouponStat(String statStr) {
        int coupon_stat = -1;
        if (statStr.equals("onSale"))
            coupon_stat = STAT_ONSALE;
        if (statStr.equals("expired"))
            coupon_stat = STAT_EXPIRED;
        if (statStr.equals("used"))
            coupon_stat = STAT_USED;
        if (statStr.equals("store"))
            coupon_stat = STAT_STORE;
    }

<<<<<<< HEAD
    public static Coupon decodeFromJSON(JSONObject jsonObject) {
        Coupon coupon = new Coupon();
        try {
            coupon.couponId = jsonObject.getString("couponid");
            coupon.listPrice = Double.parseDouble(jsonObject.getString("listprice"));
            coupon.evaluatePrice = Double.parseDouble(jsonObject.getString("value"));
            coupon.name = jsonObject.getString("product");
            coupon.discount = Double.parseDouble(jsonObject.getString("discount"));
            coupon.expireDate = jsonObject.getString("expiredtime");
            coupon.imgURL = jsonObject.getString("pic");


=======

    /**
     * decode JSON Object
     * @param jsonObject
     * @return
     */
    public static Coupon decodeFromJSON(JSONObject jsonObject) {
        Coupon coupon = new Coupon();
        try {
            coupon.couponid = jsonObject.getString("couponid");
            coupon.listprice = jsonObject.getString("listprice");
            coupon.value = jsonObject.getString("value");
            coupon.product = jsonObject.getString("product");
            coupon.discount = jsonObject.getString("discount");
            coupon.expiredtime = jsonObject.getString("expiredtime");
            coupon.pic = jsonObject.getString("pic");
>>>>>>> ttr
        } catch (Exception e) {
            System.out.println("Error when decoding coupon json");
            e.printStackTrace();
        }
        //name price detail expire_date

        return coupon;
    }


    //Second, more detailed decode
    public void getDetails(String str) {
        System.out.println("response = " + str);
        try {
            JSONObject mainObj = new JSONObject(str);

            //brand
            JSONObject brandObj = mainObj.getJSONArray("brand").getJSONObject(0);
            this.brandName = brandObj.getString("name");
         //   this.address = brandObj.getString("area");

            //limit
            JSONArray limitArray = mainObj.getJSONArray("limit");
            String[] constraintList = new String[limitArray.length()];
            for (int i = 0; i < limitArray.length(); i++) {
                JSONObject contentObj = limitArray.getJSONObject(i);
                String content = contentObj.getString("content");
                constraintList[i] = content;
            }
            this.constraints = constraintList;

<<<<<<< HEAD
=======
            //关注
            String likeStr = mainObj.getString("isLike");
            this.liked = false;
            if (likeStr.equals("1"))
                this.liked = true;

>>>>>>> ttr
            //seller 卖家
            JSONObject sellerObj = mainObj.getJSONArray("seller").getJSONObject(0);
            this.sellerNickname = sellerObj.getString("nickname");
            this.sellerAvatarURL = sellerObj.getString("avatar");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
<<<<<<< HEAD
    /*{"brand": [{"name": "\u80af\u5fb7\u57fa",
    "address": "\u7fa4\u5149"}],
    "limit": [{"content": "\u53ea\u9650\u7fa4\u5149\u4f7f\u7528"},
     {"content": "\u6bcf\u4e2a\u5ba2\u6237\u4f7f\u7528\u4e00\u4e00\u5f20"},
     {"content": "\u6ee140\u5143\u53ef\u4f7f\u7528"}], "seller": [{"nickname": "\u5988\u5356\u6279\u54e6", "avatar": null}]}
      */
=======


    /**
     * Auto generate JSON with user ID
     * @param userID
     * @return
     */
    public JSONObject generateJSON(String userID) {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            json.put("brand", brandName);
            json.put("category", catId);
            json.put("expiredTime", expiredtime);
            json.put("listPrice", listprice);
            json.put("product", product);
            json.put("discount", discount);
            //json.put("stat", stat);
            JSONArray jsonArray = new JSONArray();
            for (String str : constraints) {
                jsonArray.put(new JSONObject().put("content", str));
            }
            json.put("limit", jsonArray);
            //Todo:图片
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * decode JSON Object from QRScanning Acitivity
     * @param jsonString
     * @return
     */
    public static Coupon decodeFromQRJSON(String jsonString) {
        Coupon coupon = new Coupon();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            coupon.product = jsonObject.getString("product");
            coupon.brandName = jsonObject.getString("brand");
            coupon.catId = jsonObject.getString("category");
            JSONArray limitArray = jsonObject.getJSONArray("limit");
            String[] constraintList = new String[limitArray.length()];
            for (int i = 0; i < limitArray.length(); i++) {
                JSONObject contentObj = limitArray.getJSONObject(i);
                String content = contentObj.getString("content");
                constraintList[i] = content;
            }
            coupon.constraints = constraintList;
            coupon.discount = jsonObject.getString("discount");
            coupon.expiredtime = jsonObject.getString("expiredTime");
        } catch (Exception e) {
            System.out.println("Error when decoding coupon json");
            e.printStackTrace();
        }
        return coupon;
    }


    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getListprice() {
        return listprice;
    }

    public void setListprice(String listprice) {
        this.listprice = listprice;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpiredtime() {
        return expiredtime;
    }

    public void setExpiredtime(String expiredtime) {
        this.expiredtime = expiredtime;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

>>>>>>> ttr
}
