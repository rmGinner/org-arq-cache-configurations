import java.util.Objects;

public class DirectCache {

    private String data;

    private String tag;

    private String line;

    private Integer totalUses;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Integer getTotalUses() {
        return totalUses;
    }

    public void setTotalUses(Integer totalUses) {
        this.totalUses = totalUses;
    }

    public void addTotalUse() {
        this.totalUses = Objects.nonNull(this.totalUses) ? this.totalUses + 1 : 0;
    }

}
