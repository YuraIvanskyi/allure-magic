package i1skyi;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "value",
        "display_value",
        "decoration"
})
public class GlobalInfoItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("value")
    protected String value;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("display_value")
    protected String display_value;
    @JsonProperty("decoration")
    protected String decoration;

    public GlobalInfoItem() {
        super();
    }

    public GlobalInfoItem(String name, String value, String display_value) {
        this.value = value;
        this.name = name;
        this.display_value = display_value;
        this.decoration = "text";
    }

    public GlobalInfoItem(String name, String value) {
        this.value = value;
        this.name = name;
        this.decoration = "text";
        this.display_value = value;
    }

    public GlobalInfoItem(String name, String value, String display_value, String decoration) {
        this.value = value;
        this.name = name;
        this.display_value = display_value;
        this.decoration = decoration;
    }

    @Override
    public String toString() {
        return "GlobalInfoItem (value=" + value + ", name=" + name + ", display_value=" + display_value
                + ", decoration=" + decoration + ")";
    }
}
