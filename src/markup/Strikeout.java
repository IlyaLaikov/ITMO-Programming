package markup;

import java.util.List;

public class Strikeout extends AbstractMarkup {
    private static final String MARKDOWN = "~";

    private static final String OPEN_BBCODE = "[s]";
    private static final String CLOSE_BBCODE = "[/s]";

    public Strikeout(List<Markupable> list) {
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
