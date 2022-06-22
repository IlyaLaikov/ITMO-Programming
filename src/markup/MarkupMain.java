package markup;

import java.util.List;

public class MarkupMain {
    public static void main(String[] args) {
        Paragraph paragraph = new Paragraph(List.of(
                new Strong(List.of(
                        new Text("1"),
                        new Strikeout(List.of(
                                new Text("2"),
                                new Emphasis(List.of(
                                        new Text("3"),
                                        new Text("4"))),
                                new Text("5"))),
                        new Text("6")))));
        OrderedList orderList = new OrderedList(List.of(
                new ListItem(List.of(
                        paragraph))));
        UnorderedList unorderList = new UnorderedList(List.of(
                new ListItem(List.of(
                        orderList)),
                new ListItem(List.of(
                        paragraph))));

        StringBuilder sb_paragraph = new StringBuilder();
        paragraph.toBBCode(sb_paragraph);
        StringBuilder sb_orderList = new StringBuilder();
        orderList.toBBCode(sb_orderList);
        StringBuilder sb_unorderList = new StringBuilder();
        unorderList.toBBCode(sb_unorderList);

        System.out.println(sb_paragraph);
        System.out.println(sb_orderList);
        System.out.println(sb_unorderList);
    }
}
