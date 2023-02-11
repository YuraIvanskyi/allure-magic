package i1skyi;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;

import io.qameta.allure.CommonJsonAggregator;
import io.qameta.allure.Constants;
import io.qameta.allure.Reader;
import io.qameta.allure.context.JacksonContext;
import io.qameta.allure.core.Configuration;
import io.qameta.allure.core.LaunchResults;
import io.qameta.allure.core.ResultsVisitor;

public class GlobalInfoWidget extends CommonJsonAggregator implements Reader {
    public static final String GLOBAL_INFO = "global-info";
    protected static final String JSON_FILE_NAME = "global-info.json";

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
    public void readResults(final Configuration configuration,
            final ResultsVisitor visitor,
            final Path directory) {
        final JacksonContext context = configuration.requireContext(JacksonContext.class);
        final Path globalInfoFile = directory.resolve(JSON_FILE_NAME);
        if (Files.exists(globalInfoFile)) {
            try (InputStream is = Files.newInputStream(globalInfoFile)) {
                final List<GlobalInfoItem> info = context.getValue()
                        .readerFor(new TypeReference<List<GlobalInfoItem>>() {
                        }).readValue(is);
                System.out.println(info);
                visitor.visitExtra(GLOBAL_INFO, info);
            } catch (IOException e) {
                visitor.error("Could not read global-info file " + globalInfoFile, e);
            }
        }
    }

    public static <T> List<T> flatten(List<List<T>> listOfLists) {
        return listOfLists.stream().reduce((x, y) -> {
            List<T> list = new ArrayList<>(x);
            list.addAll(y);
            return list;
        }).orElse(new ArrayList<>());
    }

    @Override
    public List<GlobalInfoItem> getData(final List<LaunchResults> launches) {
        // final List<Map.Entry<String, String>> warnings = launches.stream()
        // .flatMap(launch -> launch.getExtra(GLOBAL_INFO,
        // (Supplier<Map<String, String>>) LinkedHashMap::new).entrySet().stream())
        // .collect(toList());
        // System.out.println(Arrays.toString(launches.toArray()));
        // System.out.println(Arrays.toString(warnings.toArray()));
        // GlobalInfoItem someObj = new GlobalInfoItem("text_value", "test_name", "some
        // text", "plain");
        // GlobalInfoItem someObj2 = new
        // GlobalInfoItem("https://github.com/YuraIvanskyi", "git", "My GitHub",
        // "link");
        // GlobalInfoItem someObj3 = new GlobalInfoItem("<code>let code =
        // 'code'</code>", "raw_entry", "", "raw");
        // List<GlobalInfoItem> objList = new ArrayList<GlobalInfoItem>();
        // objList.add(someObj);
        // objList.add(someObj2);
        // objList.add(someObj3);
        // return objList;
        List<Object> all = launches.stream()
                .map(launchResults -> launchResults.getExtra(GLOBAL_INFO))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        System.out.println(all);
        return launches.stream()
                .map(launchResults -> launchResults.getExtra(GLOBAL_INFO))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(GlobalInfoItem.class::isInstance)
                .map(GlobalInfoItem.class::cast)
                .collect(Collectors.toList());
    }
}