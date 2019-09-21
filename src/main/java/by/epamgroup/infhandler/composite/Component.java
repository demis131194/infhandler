package by.epamgroup.infhandler.composite;

import java.util.List;

public interface Component {
    String collect();
    void addComponent(Component component);
    List<Component> getComponents();
}
