package markup;

import java.util.List;

public interface Markdownable {
    void toMarkdown(StringBuilder sb);

    default void toMarkdown(StringBuilder sb, List<? extends Markdownable> list) {
        for (Markdownable markdownable : list) {
            markdownable.toMarkdown(sb);
        }
    }

    default void toMarkdown(StringBuilder sb, String open, List<? extends Markdownable> list, String close) {
        sb.append(open);
        toMarkdown(sb, list);
        sb.append(close);
    }
}
