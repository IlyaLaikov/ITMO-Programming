package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMarkup implements Markupable, MarkupInt {
    protected final List<Markupable> list;

    protected AbstractMarkup(List<Markupable> list) {
        this.list = new ArrayList<>(list);
    }

    abstract protected String getOpenMarkdown();

    abstract protected String getCloseMarkdown();

    abstract protected String getOpenBBCode();

    abstract protected String getCloseBBCode();

    @Override
    public void toMarkdown(StringBuilder sb) {
        toMarkdown(sb, getOpenMarkdown(), list, getCloseMarkdown());
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        toBBCode(sb, getOpenBBCode(), list, getCloseBBCode());
    }
}
