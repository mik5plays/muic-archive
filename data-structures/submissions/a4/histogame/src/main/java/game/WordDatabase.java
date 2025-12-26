package game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// HINT(s):
//   To read from src/resources/<filename>
//   InputStream is = getClass().getClassLoader().getResourceAsStream(filename);

public class WordDatabase implements IDatabase {
    public List<Word> words;

    public WordDatabase(String filename) throws IOException { // IntelliJ suggested I replace FileNotFoundException with a more general error message
        words = new ArrayList<Word>();
        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        if (is == null) { throw new FileNotFoundException(); }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (reader.readLine() != null) {
            words.add(new Word(reader.readLine()));
        }

    }
    @Override
    public void add(Word w) {
        // TODO:
        words.add(w);
    }

    @Override
    public void remove(Word w) {
        // TODO:
        words.remove(w);
    }

    @Override
    public List<Word> getWordWithLength(int l) {
        // TODO:
        List<Word> validWords = new ArrayList<Word>();
        for (Word word: words) {
            if (word.getHistogram().getTotalCount() == l) { validWords.add(word); }
        }
        return validWords;
    }

    @Override
    public List<Word> getAllSubWords(Word w, int minLen) {
        // TODO:
        List<Word> validWords = new ArrayList<Word>();
        for (Word word: words) {
            if (word.getHistogram().getTotalCount() >= minLen && w.canForm(word)) { validWords.add(word); }
        }
        return validWords;
    }

    @Override
    public boolean contains(Word o) {
        // TODO:
        return words.contains(o);
    }
}
