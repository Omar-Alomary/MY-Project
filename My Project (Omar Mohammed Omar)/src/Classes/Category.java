package Classes;

public class Category {
    private String type;
    private int number;
    private String describe;

    /**
     *
     * @param type
     * @param number
     * @param describe
     */
    public Category(String type, int number, String describe) {
        this.type = type;
        this.number = number;
        this.describe = describe;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getType() {
        return type;
    }
    public int getNumber() {
        return number;
    }
    public String getDescribe() {
        return describe;
    }
}
