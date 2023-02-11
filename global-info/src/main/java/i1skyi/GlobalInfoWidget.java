package i1skyi;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import io.qameta.allure.CommonJsonAggregator;
import io.qameta.allure.Constants;
import io.qameta.allure.core.LaunchResults;

public class GlobalInfoWidget extends CommonJsonAggregator {
    public static final String GLOBAL_INFO = "global-info";

    public GlobalInfoWidget(String location, String fileName) {
        super(location, fileName);
    }

    public GlobalInfoWidget(String fileName) {
        super(Constants.WIDGETS_DIR, fileName);
    }

    public GlobalInfoWidget() {
        super(Constants.WIDGETS_DIR, "global-info.json");
    }

    @Override
    public List<GlobalInfoItem> getData(final List<LaunchResults> launches) {
        final List<Map.Entry<String, String>> warnings = launches.stream()
                .flatMap(launch -> launch.getExtra(GLOBAL_INFO,
                        (Supplier<Map<String, String>>) LinkedHashMap::new).entrySet().stream())
                .collect(toList());
        System.out.println(Arrays.toString(warnings.toArray()));
        GlobalInfoItem someObj = new GlobalInfoItem("text_value", "test_name", "some text", "plain");
        GlobalInfoItem someObj2 = new GlobalInfoItem("https://github.com/YuraIvanskyi", "git", "My GitHub", "link");
        GlobalInfoItem someObj3 = new GlobalInfoItem("<code>let code = 'code'</code>", "raw_entry", "", "raw");
        List<GlobalInfoItem> objList = new ArrayList<GlobalInfoItem>();
        objList.add(someObj);
        objList.add(someObj2);
        objList.add(someObj3);
        return objList;
        // warnings.stream()
        // .collect(groupingBy(Map.Entry::getKey, LinkedHashMap::new,
        // mapping(Map.Entry::getValue, toSet())))
        // .entrySet().stream()
        // .map(GlobalInfoWidget::aggregateItem)
        // .collect(toList());
    }

    private static GlobalInfoItem aggregateItem(final HashMap<String, String> entry) {
        return new GlobalInfoItem(entry.get("values"), entry.get("name"), entry.get("display_name"),
                entry.get("decoration"));
    }

    // public static void main(String[] args) {
    // GlobalInfoItem globalInfoItem = new GlobalInfoItem("test", "test", "test",
    // "test");
    // }
}