package by.epamgroup.infhandler.comporator;

import by.epamgroup.infhandler.composite.Word;

import java.util.Comparator;

public class ComponentLengthComparator implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        return Integer.compare(o1.collect().length(), o2.collect().length());
    }
}
