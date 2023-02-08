package i1skyi;

import io.qameta.allure.CommonJsonAggregator;
import io.qameta.allure.Constants;
import io.qameta.allure.core.LaunchResults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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

        GlobalInfoItem someObj = new GlobalInfoItem("test", "test", "test", "test");
        List<GlobalInfoItem> objList = new ArrayList<GlobalInfoItem>();
        objList.add(someObj);
        return objList;
        // warnings.stream()
        // .collect(groupingBy(Map.Entry::getKey, LinkedHashMap::new,
        // mapping(Map.Entry::getValue, toSet())))
        // .entrySet().stream()
        // .map(GlobalInfoWidget::aggregateItem)
        // .collect(toList());
    }

    public static String getName() {
        return GLOBAL_INFO;
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