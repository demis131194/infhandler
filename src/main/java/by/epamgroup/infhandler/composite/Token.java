package by.epamgroup.infhandler.composite;

import java.util.ArrayList;
import java.util.List;

public class Token implements Component {
    private List<Component> components = new ArrayList<>();

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
        components.forEach(component -> builder.append(component.collect()));
        return builder.toString();
    }
}
