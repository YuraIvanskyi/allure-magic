package i1skyi;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class GlobalInfoItem implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String value;
    protected String name;
    protected String display_value;
    protected String decoration;

    public GlobalInfoItem(String value, String name, String display_value) {
        this.value = value;
        this.name = name;
        this.display_value = display_value;
        this.decoration = "text";
    }

    public GlobalInfoItem(String value, String name) {
        this.value = value;
        this.name = name;
        this.decoration = "text";
        this.display_value = value;
    }

    public GlobalInfoItem(String value, String name, String display_value, String decoration) {
        this.value = value;
        this.name = name;
        this.display_value = display_value;
        this.decoration = decoration;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplay_value(String display_value) {
        this.display_value = display_value;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getDisplay_value() {
        return display_value;
    }

    public String getDecoration() {
        return decoration;
    }

}
