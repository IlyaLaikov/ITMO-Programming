package markup;

import java.util.List;

public interface BBCodeable {
    void toBBCode(StringBuilder sb);

    default void toBBCode(StringBuilder sb, List<? extends BBCodeable> list) {
        for (BBCodeable bbcodeable : list) {
            bbcodeable.toBBCode(sb);
        }
    }

    default void toBBCode(StringBuilder sb, String open, List<? extends BBCodeable> list, String close) {
        sb.append(open);
        toBBCode(sb, list);
        sb.append(close);
    }
}
