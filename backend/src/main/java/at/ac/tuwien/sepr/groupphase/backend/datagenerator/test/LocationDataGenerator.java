package at.ac.tuwien.sepr.groupphase.backend.datagenerator.test;

import at.ac.tuwien.sepr.groupphase.backend.entity.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Profile("generateTestData")
@Component
public class LocationDataGenerator extends DataGenerator<Location> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    protected List<Location> generate() {
        LOGGER.info("generating locations");

        final var locations = List.of(
            Location.builder()
                .title("Wiener Stadthalle")
                .address("Roland-Rainer-Platz 1")
                .postalCode("1150")
                .city("Wien")
                .country("Österreich")
                .build(),
            Location.builder()
                .title("Gasometer")
                .address("Guglgasse 6")
                .postalCode("1110")
                .city("Wien")
                .country("Österreich")
                .build(),
            Location.builder()
                .title("Stadthalle Graz")
                .address("Messeplatz 1")
                .postalCode("8010")
                .city("Graz")
                .country("Österreich")
                .build(),
            Location.builder()
                .title("Stadthalle Klagenfurt")
                .address("Valentin-Leitgeb-Straße 1")
                .postalCode("9020")
                .city("Klagenfurt")
                .country("Österreich")
                .build(),
            Location.builder()
                .title("Stadthalle Linz")
                .address("Willy-Brandt-Platz 1")
                .postalCode("4020")
                .city("Linz")
                .country("Österreich")
                .build(),
            Location.builder()
                .title("gravida nisi at")
                .address("363 Springview Parkway")
                .postalCode("1423")
                .city("Nantai")
                .country("China")
                .build(),
            Location.builder()
                .title("pede ac diam")
                .address("10 Morning Way")
                .postalCode("90726")
                .city("Sandakan")
                .country("Malaysia")
                .build(),
            Location.builder()
                .title("porttitor pede justo eu massa")
                .address("62820 Dunning Pass")
                .postalCode("4213")
                .city("Todorovo")
                .country("Bosnia and Herzegovina")
                .build(),
            Location.builder()
                .title("cubilia curae")
                .address("47724 Thompson Road")
                .postalCode("95818")
                .city("Sacramento")
                .country("United States")
                .build(),
            Location.builder()
                .title("penatibus et magnis")
                .address("79 Pawling Plaza")
                .postalCode("3142")
                .city("Mesopotamía")
                .country("Greece")
                .build(),
            Location.builder()
                .title("ligula suspendisse ornare")
                .address("33261 Huxley Parkway")
                .postalCode("2413")
                .city("Wulin")
                .country("China")
                .build(),
            Location.builder()
                .title("mi integer")
                .address("4528 Dwight Pass")
                .postalCode("1243")
                .city("Qishui")
                .country("China")
                .build(),
            Location.builder()
                .title("massa donec dapibus duis")
                .address("14 Nobel Park")
                .postalCode("659690")
                .city("Soloneshnoye")
                .country("Russia")
                .build(),
            Location.builder()
                .title("quisque arcu libero")
                .address("04614 Marquette Road")
                .postalCode("58600-000")
                .city("Santa Luzia")
                .country("Brazil")
                .build(),
            Location.builder()
                .title("quis tortor id nulla")
                .address("79 Hermina Alley")
                .postalCode("3124")
                .city("Tual")
                .country("Indonesia")
                .build(),
            Location.builder()
                .title("nisi eu")
                .address("7 Village Drive")
                .postalCode("4213")
                .city("Karangpapak")
                .country("Indonesia")
                .build(),
            Location.builder()
                .title("sit amet erat nulla tempus")
                .address("6088 Tony Lane")
                .postalCode("3124")
                .city("Setono")
                .country("Indonesia")
                .build(),
            Location.builder()
                .title("proin at")
                .address("235 Shopko Court")
                .postalCode("301382")
                .city("Novogurovskiy")
                .country("Russia")
                .build(),
            Location.builder()
                .title("sem fusce")
                .address("92 Hazelcrest Pass")
                .postalCode("7006")
                .city("Siay")
                .country("Philippines")
                .build(),
            Location.builder()
                .title("montes nascetur")
                .address("8687 Tennessee Terrace")
                .postalCode("3282")
                .city("St. Anton an der Jeßnitz")
                .country("Österreich")
                .build(),
            Location.builder()
                .title("non quam nec dui luctus")
                .address("09634 Dexter Junction")
                .postalCode("1232")
                .city("Pueblo Nuevo")
                .country("Peru")
                .build(),
            Location.builder()
                .title("ut erat curabitur gravida")
                .address("15826 Menomonie Way")
                .postalCode("1342")
                .city("Al ‘Āliyah")
                .country("Tunisia")
                .build(),
            Location.builder()
                .title("ut blandit non interdum")
                .address("91 Springview Road")
                .postalCode("5214")
                .city("Mauhao")
                .country("Philippines")
                .build(),
            Location.builder()
                .title("dapibus at diam nam")
                .address("0379 Nevada Point")
                .postalCode("05-870")
                .city("Błonie")
                .country("Poland")
                .build(),
            Location.builder()
                .title("sapien iaculis congue")
                .address("125 Farmco Junction")
                .postalCode("Y25")
                .city("Rathwire")
                .country("Ireland")
                .build(),
            Location.builder()
                .title("metus arcu adipiscing molestie")
                .address("40 Summerview Junction")
                .postalCode("696 74")
                .city("Velká nad Veličkou")
                .country("Czech Republic")
                .build(),
            Location.builder()
                .title("eget tempus vel pede morbi")
                .address("66 Ludington Street")
                .postalCode("3241")
                .city("Binzhou")
                .country("China")
                .build(),
            Location.builder()
                .title("odio odio elementum eu interdum")
                .address("7 Dakota Junction")
                .postalCode("2314")
                .city("Dingchang")
                .country("China")
                .build(),
            Location.builder()
                .title("congue eget semper rutrum")
                .address("5037 Shopko Trail")
                .postalCode("3124")
                .city("Kendayakan")
                .country("Indonesia")
                .build(),
            Location.builder()
                .title("libero rutrum ac lobortis vel")
                .address("8 Sugar Court")
                .postalCode("8701")
                .city("Kalugmanan")
                .country("Philippines")
                .build(),
            Location.builder()
                .title("quis libero nullam sit")
                .address("418 Meadow Vale Hill")
                .postalCode("3214")
                .city("Khorol")
                .country("Ukraine")
                .build(),
            Location.builder()
                .title("amet sapien dignissim")
                .address("2 Basil Circle")
                .postalCode("1423")
                .city("Novoselitsa")
                .country("Ukraine")
                .build(),
            Location.builder()
                .title("turpis donec")
                .address("57227 Glacier Hill Pass")
                .postalCode("615 95")
                .city("Valdemarsvik")
                .country("Sweden")
                .build(),
            Location.builder()
                .title("faucibus orci")
                .address("68 Mcguire Road")
                .postalCode("4213")
                .city("Anxiang")
                .country("China")
                .build(),
            Location.builder()
                .title("lectus pellentesque eget nunc")
                .address("18 Manley Plaza")
                .postalCode("2762")
                .city("Lichtenburg")
                .country("South Africa")
                .build(),
            Location.builder()
                .title("est quam pharetra")
                .address("6 Northfield Crossing")
                .postalCode("3214")
                .city("Bailu")
                .country("China")
                .build(),
            Location.builder()
                .title("est risus auctor sed tristique")
                .address("3982 Rutledge Plaza")
                .postalCode("4321")
                .city("Muke")
                .country("Indonesia")
                .build(),
            Location.builder()
                .title("pellentesque eget nunc donec")
                .address("8 Mayer Road")
                .postalCode("1342")
                .city("Tungguwaneng")
                .country("Indonesia")
                .build(),
            Location.builder()
                .title("nulla tempus")
                .address("049 Valley Edge Court")
                .postalCode("4231")
                .city("Ruchihe")
                .country("China")
                .build(),
            Location.builder()
                .title("nec nisi")
                .address("25 Merrick Junction")
                .postalCode("3214")
                .city("Hongyuan")
                .country("China")
                .build(),
            Location.builder()
                .title("lacus morbi quis tortor")
                .address("02 Dapin Plaza")
                .postalCode("49003")
                .city("Zamora")
                .country("Spain")
                .build(),
            Location.builder()
                .title("orci mauris")
                .address("5 Evergreen Parkway")
                .postalCode("98000")
                .city("Moneghetti")
                .country("Monaco")
                .build(),
            Location.builder()
                .title("in hac habitasse")
                .address("6049 Maple Road")
                .postalCode("3142")
                .city("Alikalia")
                .country("Sierra Leone")
                .build(),
            Location.builder()
                .title("erat id mauris vulputate")
                .address("0030 Westport Crossing")
                .postalCode("4231")
                .city("Yangqiao")
                .country("China")
                .build(),
            Location.builder()
                .title("neque libero convallis eget")
                .address("444 Farragut Court")
                .postalCode("3241")
                .city("Yuguan")
                .country("China")
                .build(),
            Location.builder()
                .title("vitae mattis nibh ligula nec")
                .address("5 Marquette Pass")
                .postalCode("1432")
                .city("Al Fukhkhārī")
                .country("Palestinian Territory")
                .build(),
            Location.builder()
                .title("nulla eget")
                .address("56346 Nelson Plaza")
                .postalCode("3421")
                .city("Pamedaran")
                .country("Indonesia")
                .build(),
            Location.builder()
                .title("tempus vel pede")
                .address("1 Transport Terrace")
                .postalCode("2341")
                .city("Iballë")
                .country("Albania")
                .build(),
            Location.builder()
                .title("erat eros viverra eget congue")
                .address("6491 Dapin Center")
                .postalCode("3412")
                .city("Leles")
                .country("Indonesia")
                .build(),
            Location.builder()
                .title("magnis dis")
                .address("1 Hoard Junction")
                .postalCode("2314")
                .city("Zhuxi")
                .country("China")
                .build(),
            Location.builder()
                .title("suspendisse potenti")
                .address("521 Graedel Court")
                .postalCode("05460")
                .city("Alor Setar")
                .country("Malaysia")
                .build(),
            Location.builder()
                .title("semper interdum mauris")
                .address("9641 Nova Circle")
                .postalCode("4132")
                .city("Al Minyā")
                .country("Egypt")
                .build(),
            Location.builder()
                .title("ante ipsum primis")
                .address("5966 Warrior Avenue")
                .postalCode("4132")
                .city("Babah Rot")
                .country("Indonesia")
                .build(),
            Location.builder()
                .title("pellentesque viverra")
                .address("188 Westend Lane")
                .postalCode("2307")
                .city("Kochani")
                .country("Macedonia")
                .build(),
            Location.builder()
                .title("justo aliquam")
                .address("62 Lakeland Circle")
                .postalCode("862 95")
                .city("Njurunda")
                .country("Sweden")
                .build(),
            Location.builder()
                .title("elit proin risus praesent")
                .address("8136 Sachtjen Trail")
                .postalCode("3241")
                .city("Tsákoi")
                .country("Greece")
                .build(),
            Location.builder()
                .title("lorem id ligula suspendisse")
                .address("2 Hollow Ridge Alley")
                .postalCode("4312")
                .city("Bulembu")
                .country("Swaziland")
                .build(),
            Location.builder()
                .title("eu interdum")
                .address("6 Commercial Plaza")
                .postalCode("49250")
                .city("Zlatar")
                .country("Croatia")
                .build(),
            Location.builder()
                .title("erat tortor sollicitudin mi sit")
                .address("23 Hoard Circle")
                .postalCode("1432")
                .city("Shuidun")
                .country("China")
                .build(),
            Location.builder()
                .title("pede justo lacinia eget tincidunt")
                .address("4 Oneill Junction")
                .postalCode("4231")
                .city("Gao")
                .country("Mali")
                .build(),
            Location.builder()
                .title("pulvinar sed nisl nunc")
                .address("069 Cardinal Parkway")
                .postalCode("2413")
                .city("Jiquinlaca")
                .country("Honduras")
                .build(),
            Location.builder()
                .title("odio donec vitae nisi nam")
                .address("6 Rowland Terrace")
                .postalCode("75929 CEDEX 19")
                .city("Paris 19")
                .country("France")
                .build(),
            Location.builder()
                .title("viverra eget congue eget")
                .address("7 Grayhawk Place")
                .postalCode("4616")
                .city("Molinos")
                .country("Argentina")
                .build(),
            Location.builder()
                .title("aliquet at feugiat non pretium")
                .address("8220 Loftsgordon Alley")
                .postalCode("27-423")
                .city("Bałtów")
                .country("Poland")
                .build(),
            Location.builder()
                .title("convallis morbi")
                .address("6904 Johnson Street")
                .postalCode("95603")
                .city("La Victoria")
                .country("Mexico")
                .build()

        );

        return locations;
    }
}
