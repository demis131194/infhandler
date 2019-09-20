package by.epamgroup.infhandler.composite;

public interface Component {
    String collect();
    void addComponent(Component component);
}
