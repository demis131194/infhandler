package by.epamgroup.infhandler.composite;

import java.util.ArrayList;
import java.util.List;

public class Text implements Component {
    private List<Component> components = new ArrayList<>();

    @Override
    public void addComponent(Component component) {
        components.add(component);
    }

    @Override
    public String collect() {
        StringBuilder builder = new StringBuilder();
        components.forEach(component -> builder.append("\t").append(component.collect()).append("\n"));
        builder.deleteCharAt(builder.lastIndexOf("\n"));
        return builder.toString();
    }
}
