package markup;

public class Text implements Markupable, MarkupInt {
    private final String data;

    public Text(String data) {
        this.data = data;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(data);
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        sb.append(data);
    }
}
