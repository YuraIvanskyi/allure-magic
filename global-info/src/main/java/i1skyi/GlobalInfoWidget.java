package i1skyi;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
                visitor.visitExtra(GLOBAL_INFO, info);
            } catch (IOException e) {
                visitor.error("Could not read global-info file " + globalInfoFile, e);
            }
        }
    }

    @Override
    public List<GlobalInfoItem> getData(final List<LaunchResults> launches) {
        // this is some Java bullshit but it works
        return launches.stream()
                .map(launchResults -> launchResults.getExtra(GLOBAL_INFO))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList())
                .stream()
                .flatMap(c -> ((Collection<GlobalInfoItem>) c).stream())
                .filter(GlobalInfoItem.class::isInstance)
                .map(GlobalInfoItem.class::cast)
                .collect(Collectors.toList());
    }
}