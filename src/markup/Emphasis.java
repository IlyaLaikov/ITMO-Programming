package markup;

import java.util.List;

public class Emphasis extends AbstractMarkup {
    private static final String MARKDOWN = "*";

    private static final String OPEN_BBCODE = "[i]";
    private static final String CLOSE_BBCODE = "[/i]";

    public Emphasis(List<Markupable> list) {
        super(list);
    }

    @Override
    public String getOpenMarkdown() {
        return MARKDOWN;
    }

    @Override
    public String getCloseMarkdown() {
        return MARKDOWN;
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
