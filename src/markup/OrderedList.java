package markup;

import java.util.List;

public class OrderedList extends AbstractList {
    private static final String OPEN_BBCODE = "[list=1]";
    private static final String CLOSE_BBCODE = "[/list]";

    public OrderedList(List<ListItem> list) {
        super(list);
    }

    @Override
    public String getOpenBBCode() {
        return OPEN_BBCODE;
    }

    @Override
    public String getCloseBBCode() {
        return CLOSE_BBCODE;
    }
}
