import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {

    private String id;
    private String site_id;
    private String title;
    private String subtitle;
    private Integer seller_id;
    private String category_id;
    private Integer price;
    private String currency_id;
    private Integer available_quantity;
    private String condition;
    private List<String> pictures = null;
    private Boolean accepts_mercadopago;
    private String status;
    private String date_created;
    private String last_updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(Integer seller_id) {
        this.seller_id = seller_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public Integer getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(Integer available_quantity) {
        this.available_quantity = available_quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public Boolean getAccepts_mercadopago() {
        return accepts_mercadopago;
    }

    public void setAccepts_mercadopago(Boolean accepts_mercadopago) {
        this.accepts_mercadopago = accepts_mercadopago;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public Map<String, Object> toDataMap(){
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("id", this.id);
        dataMap.put("site_id", this.site_id);
        dataMap.put("title", this.title);
        dataMap.put("subtitle", this.subtitle);
        dataMap.put("seller_id", this.seller_id);
        dataMap.put("category_id", this.category_id);
        dataMap.put("price", this.price);
        dataMap.put("currency_id", this.currency_id);
        dataMap.put("available_quantity", this.available_quantity);
        dataMap.put("condition", this.condition);
        dataMap.put("pictures", this.pictures);
        dataMap.put("accepts_mercadopago", this.accepts_mercadopago);
        dataMap.put("status", this.status);
        dataMap.put("date_created", this.date_created);
        dataMap.put("last_updated", this.last_updated);
        return dataMap;
    }

    @Override
    public String toString() {
        String res = "\tID:" + getId() +
                "\n\tSite_id: " + getSite_id() +
                "\n\tTitle: " + getTitle() +
                "\n\tSubtitle: " + getSubtitle() +
                "\n\tSeller_id: " + getSeller_id() +
                "\n\tCategory_id: " + getCategory_id() +
                "\n\tPrice: " + getPrice();
                //"\n\tCurrency_id: " + getCurrency_id() +
                //"\n\tAvailable_Quantity:" + getAvailable_quantity() +
                //"\n\tCondition: " + getCondition() +
                //"\n\tPictures: " + getPictures() +
                //"\n\tAccepts_Mercadopago: " + getAccepts_mercadopago() +
                //"\n\tStatus: " + getStatus() +
                //"\n\tDate_Created: " + getDate_created() +
                //"\n\tLast_updated:" + getLast_updated();
        return res;
    }
}