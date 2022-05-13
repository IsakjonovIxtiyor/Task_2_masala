import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class LazyKing {
    public static void main(String[] args) {

        UnluckyVassal unluckyVassal = new UnluckyVassal();
        unluckyVassal.printReportForKing(pollResults);
    }

    private static List<String> pollResults = List.of(
            "служанка Аня", // xizmatkor
            "управляющий Семен Семеныч: крестьянин Федя, доярка Нюра", // boshqaruvchi
            "дворянин Кузькин: управляющий Семен Семеныч, жена Кузькина, экономка Лидия Федоровна", // zodagon
            "экономка Лидия Федоровна: дворник Гена, служанка Аня", //uy bekasi
            "доярка Нюра", // soguvchi
            "кот Василий: человеческая особь Катя", // mushuk
            "дворник Гена: посыльный Тошка", //ko'cha tozalovchi
            "киллер Гена", // qotil
            "зажиточный холоп: крестьянка Таня", //
            "секретарь короля: зажиточный холоп, шпион Т", // qirol kotibi
            "шпион Т: кучер Д", // josus
            "посыльный Тошка: кот Василий", //xabarchi
            "аристократ Клаус", // boyvacha
            "просветленный Антон"// xazrat qori

    );

}
class UnluckyVassal {
    private static final String KING = "король";
    private Map<String, List<String>> tree;

    public void printReportForKing(List<String> pollResults) {
        tree = createTree(pollResults);

        System.out.println(KING);
        printLevel(KING, "  ");
    }

    private void printLevel(String name, String ident) {
        if(tree.containsKey(name))
            for (String s : tree.get(name)) {
                System.out.println(ident+s);
                printLevel(s, ident + "  ");
            }
    }

    private Map<String, List<String>> createTree(List<String> pollResults) {
        Map<String, List<String>> tree = new HashMap<>();
        for (String pollItem : pollResults) {
            String[] r = pollItem.split(": ");
            tree.put(
                    r[0],
                    r.length > 1
                            ? Stream.of(r[1].split(", ")).sorted(Comparator.naturalOrder()).collect(toList())
                            : List.of()
            );
        }

        Set<String> noKingVassals = tree.values().stream().flatMap(Collection::stream).collect(toSet());
        tree.put(
                KING,
                tree.keySet().stream()
                        .filter(s->!noKingVassals.contains(s))
                        .sorted(Comparator.naturalOrder())
                        .collect(toList())
        );
        return tree;
    }
}
