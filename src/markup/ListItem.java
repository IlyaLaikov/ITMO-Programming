package markup;

import java.util.ArrayList;
import java.util.List;

public class ListItem implements BBCodeable, ListItemInt {
    private static final String OPEN_BBCODE = "[*]";
    private static final String CLOSE_BBCODE = "";

    private final List<Itemiable> list;

    public ListItem(List<Itemiable> list) {
        this.list = new ArrayList<>(list);
    }

    public String getOpenBBCode() {
        return OPEN_BBCODE;
    }

    public String getCloseBBCode() {
        return CLOSE_BBCODE;
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        toBBCode(sb, getOpenBBCode(), list, getCloseBBCode());
    }
}
