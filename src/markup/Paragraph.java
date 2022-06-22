package markup;

import java.util.ArrayList;
import java.util.List;

public class Paragraph implements Itemiable, Markdownable {
    private final List<Markupable> list;

    public Paragraph(List<Markupable> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        toMarkdown(sb, list);
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        toBBCode(sb, list);
    }
}
