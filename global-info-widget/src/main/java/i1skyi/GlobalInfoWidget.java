package i1skyi;

import io.qameta.allure.CommonJsonAggregator;
import io.qameta.allure.core.LaunchResults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import static io.qameta.allure.allure1.Allure1Plugin.ENVIRONMENT_BLOCK_NAME;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class GlobalInfoWidget extends CommonJsonAggregator {
    public static final String GLOBAL_INFO = "global-info";

    protected GlobalInfoWidget(String fileName) {
        super("widgets", "globalinfo.json");
        // TODO Auto-generated constructor stub
    }

    @Override
    protected List<GlobalInfoItem> getData(final List<LaunchResults> launches) {
        final List<Map.Entry<String, String>> launchEnvironments = launches.stream()
                .flatMap(launch -> launch.getExtra(GLOBAL_INFO,
                        (Supplier<Map<String, String>>) LinkedHashMap::new).entrySet().stream())
                .collect(toList());

        return launchEnvironments.stream()
                .collect(groupingBy(Map.Entry::getKey, LinkedHashMap::new, mapping(Map.Entry::getValue, toSet())))
                .entrySet().stream()
                .map(GlobalInfoWidget::aggregateItem)
                .collect(toList());
    }

    private static GlobalInfoItem aggregateItem(final HashMap<String, String> entry) {
        return new GlobalInfoItem(entry.get("values"), entry.get("name"), entry.get("display_name"),
                entry.get("decoration"));
    }
}
