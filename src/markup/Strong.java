package markup;

import java.util.List;

public class Strong extends AbstractMarkup {
    private static final String MARKDOWN = "__";

    private static final String OPEN_BBCODE = "[b]";
    private static final String CLOSE_BBCODE = "[/b]";

    public Strong(List<Markupable> list) {
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
