package by.epamgroup.infhandler.comporator;

import by.epamgroup.infhandler.composite.Component;

import java.util.Comparator;

public class ComponentCountComparator implements Comparator<Component> {

    @Override
    public int compare(Component o1, Component o2) {
        int i = Integer.compare(o1.getComponents().size(), o2.getComponents().size());
        return i;
    }

}
