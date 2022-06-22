package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractList implements Itemiable {
    protected final List<ListItem> list;

    protected AbstractList(List<ListItem> list) {
        this.list = new ArrayList<>(list);
    }

    protected abstract String getOpenBBCode();

    protected abstract String getCloseBBCode();

    @Override
    public void toBBCode(StringBuilder sb) {
        toBBCode(sb, getOpenBBCode(), list, getCloseBBCode());
    }
}
