package by.epamgroup.infhandler.handler;

import by.epamgroup.infhandler.comporator.ComponentCountComparator;
import by.epamgroup.infhandler.composite.*;
import by.epamgroup.infhandler.exception.ComponentHandlerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentHandler {
    private static Logger logger = LogManager.getLogger();

    private ComponentHandler() {
    }

    public static Component sortParagraphsBySentencesCount(ComponentImpl text) throws ComponentHandlerException {
        logger.trace("In sortParagraphsBySentencesCount method.");
        if (text.getTextPart() != TextPart.TEXT) {
            throw new ComponentHandlerException("Only for TEXT!");
        }

        Comparator<Component> comparator = new ComponentCountComparator();
        List<Component> components = text.getComponents();

        components.sort(comparator);
        logger.trace("Out sortParagraphsBySentencesCount method.");
        return text;
    }

    public static Component sortSentenceByWordCount(ComponentImpl text) throws ComponentHandlerException {
        List<Component> paragraphs;

        switch (text.getTextPart()) {
            case TEXT:
                paragraphs = text.getComponents();
                break;
            case PARAGRAPH:
                paragraphs = new ArrayList<>(Collections.singletonList(text));
                break;
            default:
                throw new ComponentHandlerException("Only for TEXT and PARAGRAPH!");

        }

        for (Component paragraph : paragraphs) {
            List<Component> sentences = paragraph.getComponents();

            sentences.sort(Comparator.comparingInt(o -> getTokensWhichContainWordFromSentence(o).size()));
        }

        return text;
    }

    public static Component sortWordsByLength(Component text) {     // FIXME: 24.09.2019
        List<Component> paragraphs = text.getComponents();

        for (Component paragraph : paragraphs) {
            List<Component> sentences = paragraph.getComponents();

            for (Component sentence : sentences) {
                List<Component> tokens = getTokensWhichContainWordFromSentence(sentence);
                List<String> words = getWordsFromTokenList(tokens);

                words.sort(Comparator.comparingInt(String::length));

                for(int i=0; i < tokens.size(); i++) {
                    Word word = (Word) tokens.get(i)
                            .getComponents()
                            .stream()
                            .filter(str -> str.getClass() == Word.class)
                            .findFirst()
                            .get();
                    word.setWord(words.get(i));
                }

            }
        }

        return text;
    }

    private static List<Component> getTokensWhichContainWordFromSentence(Component sentence) {
        return sentence.getComponents()
                .stream()
                .filter(token -> {
                    boolean hasWord = token.getComponents()
                            .stream()
                            .anyMatch(tokenComponents -> tokenComponents.getClass() == Word.class);
                    return hasWord;
                })
                .collect(Collectors.toList());
    }

    private static List<String> getWordsFromTokenList(List<Component> tokens) {
        List<String> words = new ArrayList<>();

        for (Component token : tokens) {
            List<Component> wordsSymbolsExpressions = token.getComponents();
            String word = wordsSymbolsExpressions.stream()
                    .filter(str -> str.getClass() == Word.class)
                    .findFirst()
                    .get()
                    .collect();
            words.add(word);
        }
        return words;
    }
}
