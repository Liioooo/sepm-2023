package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import at.ac.tuwien.sepr.groupphase.backend.repository.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Profile("generateData")
@Component
public class NewsDataGenerator extends DataGenerator<News> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsRepository newsRepository;
    private final ApplicationUserDataGenerator userDataGenerator;
    private final PublicFileDataGenerator publicFileDataGenerator;

    public NewsDataGenerator(NewsRepository newsRepository, ApplicationUserDataGenerator userDataGenerator, PublicFileDataGenerator publicFileDataGenerator) {
        this.newsRepository = newsRepository;
        this.userDataGenerator = userDataGenerator;
        this.publicFileDataGenerator = publicFileDataGenerator;
    }

    @Override
    protected List<News> generate() {
        if (newsRepository.count() > 0) {
            LOGGER.info("news data already generated");
            return null;
        }

        final var news = List.of(
            News.builder()
                .title("News-Title-1")
                .publishDate(OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC))
                .overviewText("This is an abstract for News-Title-1")
                .text("This is text for News-Title-1")
                .author(userDataGenerator.getTestData().get(0))
                .image(publicFileDataGenerator.getTestData().get(0))
                .build(),
            News.builder()
                .title("News-Title-2")
                .publishDate(OffsetDateTime.of(2021, 2, 9, 20, 0, 0, 0, ZoneOffset.UTC))
                .overviewText("This is an abstract for News-Title-2")
                .text("This is text for News-Title-2")
                .author(userDataGenerator.getTestData().get(0))
                .image(publicFileDataGenerator.getTestData().get(0))
                .build(),
            News.builder()
                .title("News-Title-3")
                .publishDate(OffsetDateTime.of(2023, 12, 9, 20, 0, 0, 0, ZoneOffset.UTC))
                .overviewText("This is an abstract for News-Title-3")
                .text("This is text for News-Title-3")
                .author(userDataGenerator.getTestData().get(0))
                .image(publicFileDataGenerator.getTestData().get(0))
                .build(),
            News.builder()
                .title("News-Title-4")
                .publishDate(OffsetDateTime.of(2022, 1, 1, 15, 30, 0, 0, ZoneOffset.UTC))
                .overviewText("This is an abstract for News-Title-4")
                .text("This is text for News-Title-4")
                .author(userDataGenerator.getTestData().get(0))
                .image(publicFileDataGenerator.getTestData().get(1))
                .build(),
            News.builder()
                .title("News-Title-5")
                .publishDate(OffsetDateTime.of(2020, 10, 25, 20, 15, 0, 0, ZoneOffset.UTC))
                .overviewText("This is an abstract for News-Title-5")
                .text("This is text for News-Title-5")
                .author(userDataGenerator.getTestData().get(0))
                .image(publicFileDataGenerator.getTestData().get(1))
                .build(),
            News.builder()
                .title("Long-News-Title-6")
                .publishDate(OffsetDateTime.of(2020, 10, 25, 20, 15, 0, 0, ZoneOffset.UTC))
                .overviewText(
                    "This is an long abstract for Long-News-Title-6. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et"
                        + " dolore magna aliquyam erat, sed diam voluptua. At vero eos et "
                        + "accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lore")
                .text(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. "
                        + "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt u"
                        + "t labore et dolore magna aliquyam"
                        + " erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et e")
                .author(userDataGenerator.getTestData().get(0))
                .image(publicFileDataGenerator.getTestData().get(1))
                .build()
        );

        LOGGER.info("generating news");
        return newsRepository.saveAll(news);
    }
}
