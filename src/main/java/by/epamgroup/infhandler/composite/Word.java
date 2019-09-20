package by.epamgroup.infhandler.composite;

public class Word implements Component {
    private String word;

    public Word(String word) {
        this.word = word;
    }

    @Override
    public String collect() {
        return word;
    }

    @Override
    public void addComponent(Component component) {
        throw new UnsupportedOperationException();
    }
}
