package Domain;

import java.sql.Timestamp;

/**
 * @author lty
 */
public class Bill {
    private Integer id;
    private Integer foodsId;
    private Integer foodsNum;
    private Double price;
    private Integer tableId;
    private Timestamp date;

    public Bill() {}

    public Bill(Integer id, Integer foodsId, Integer foodsNum, Double price, Integer tableId, Timestamp date) {
        this.id = id;
        this.foodsId = foodsId;
        this.foodsNum = foodsNum;
        this.price = price;
        this.tableId = tableId;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFoodsId() {
        return foodsId;
    }

    public void setFoodsId(Integer foodsId) {
        this.foodsId = foodsId;
    }

    public Integer getFoodsNum() {
        return foodsNum;
    }

    public void setFoodsNum(Integer foodsNum) {
        this.foodsNum = foodsNum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return id + "\t\t\t" + foodsId + "\t\t\t\t" + foodsNum + "\t\t\t" + price + "\t\t" + tableId + "\t\t" + date;
    }
}
