package by.epamgroup.infhandler.handler;

import by.epamgroup.infhandler.comporator.ComponentCountComparator;
import by.epamgroup.infhandler.comporator.ComponentLengthComparator;
import by.epamgroup.infhandler.composite.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.CertPathTrustManagerParameters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentHandler {
    private static Logger logger = LogManager.getLogger();

    private ComponentHandler() {
    }

    public static Text sortParagraphsBySentencesCount(Text text) {
        Comparator<Component> comparator = new ComponentCountComparator();
        List<Component> components = text.getComponents();

        components.sort(comparator);
        return text;
    }

    public static Text sortSentenceByWordCount(Text text) {

        return text;
    }

    public static Text sortWordsByLength(Text text) {
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
