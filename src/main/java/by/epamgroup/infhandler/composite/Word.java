package by.epamgroup.infhandler.composite;

import java.util.List;

public class Word implements Component {
    private TextPart textPart = TextPart.WORD;
    private String word;

    public Word(String word) {
        this.word = word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public TextPart getTextPart() {
        return textPart;
    }

    @Override
    public String collect() {
        return word;
    }

    @Override
    public List<Component> getComponents() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addComponent(Component component) {
        throw new UnsupportedOperationException();
    }
}
