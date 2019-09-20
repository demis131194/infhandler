package by.epamgroup.infhandler.composite;

public class Word implements Component {
    private String word;

    @Override
    public String collect() {
        return word;
    }
}
