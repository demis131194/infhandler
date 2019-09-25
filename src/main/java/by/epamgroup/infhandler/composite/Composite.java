package by.epamgroup.infhandler.composite;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {
    private TextPart textPart;
    private List<Component> components = new ArrayList<>();

    public Composite(TextPart textPart) {
        this.textPart = textPart;
    }

    @Override
    public TextPart getTextPart() {
        return textPart;
    }

    @Override
    public void addComponent(Component component) {
        components.add(component);
    }

    @Override
    public List<Component> getComponents() {
        return components;
    }

    @Override
    public String collect() {
        StringBuilder builder = new StringBuilder();

        switch (textPart) {
            case TEXT:
                components.forEach(component -> builder.append("\t").append(component.collect()).append("\n"));
                builder.deleteCharAt(builder.lastIndexOf("\n"));
                break;
            case PARAGRAPH:
            case SENTENCE:
                components.forEach(component -> builder.append(component.collect()).append(" "));
                builder.deleteCharAt(builder.lastIndexOf(" "));
                break;
            case TOKEN:
                components.forEach(component -> builder.append(component.collect()));
                break;
        }

        return builder.toString();
    }
}
