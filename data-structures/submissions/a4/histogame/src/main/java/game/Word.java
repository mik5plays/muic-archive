package game;

import histogram.Histogram;
import histogram.SimpleHistogram;

import java.util.Iterator;

public class Word implements Formable<Word>, Comparable<Word> {
    // The only constructor of the class, which takes a String representation
    // of the word. The histogram is generated here from the given word.
    private String word;
    private Histogram<Character> wordHistogram;
    public Word(String word) {
        this.word = word;
        wordHistogram = new SimpleHistogram<>();
        for (int i = 0; i < word.length(); i++) {
            wordHistogram.setCount(word.charAt(i), wordHistogram.getCount(word.charAt(i)) + 1);
        }
    }

    // Returns the String representation of the word.
    public String getWord() { return word; }

    // Returns a Histogram describing the character distribution of the word.
    public Histogram<Character> getHistogram() { return wordHistogram; }

    // Returns true if the Word represented by other can be formed using some
    // or all of the characters of this word.
    @Override
    public boolean canForm(Word other) {
        // Same histogram, so can form.
        if (this.wordHistogram.equals(other.wordHistogram)) { return true; }
        // Other is longer than the current Word, so cannot form.
        if (this.wordHistogram.getTotalCount() < other.wordHistogram.getTotalCount()) { return false; }

        Iterator<Character> wordIter = other.wordHistogram.iterator();
        while (wordIter.hasNext()) {
            Character curr = wordIter.next();
            if (this.wordHistogram.getCount(curr) == 0) { return false; }
            if (other.wordHistogram.getCount(curr) > this.wordHistogram.getCount(curr)) { return false; }
        }

        return true;
    }

    // Return -1 if this word is shorter than the word represented by o OR
    // when this word and the word represented by o have the same length but
    // this word comes before the word represented by o alphabetically.

    // Return 0 if this word and o word are identical.

    // Return +1 otherwise.
    @Override
    public int compareTo(Word o) {
        // The same word
        if (o.wordHistogram.equals(this.wordHistogram) || o.word.equals(this.word)) { return 0; }
        // This word is shorter than o
        if (o.wordHistogram.getTotalCount() > this.wordHistogram.getTotalCount()) { return -1; }
        /*
         * Same length but "this" word comes before "o"
         * Noticed that linuxwords has both capital and lowercase value starting words
         * So will implement additional checks to take care of that
         */
        if (o.wordHistogram.getTotalCount() == this.wordHistogram.getTotalCount()) {
            char startThis = this.word.charAt(0);
            char startOther = o.word.charAt(0);
            // Case 1: "this" is a lowercase but "other" is an uppercase
            if ((int) startThis >= (int) 'a' && (int) startOther < (int) 'a' ) {
                if ((int) startThis - 32 < (int) startOther) {
                    return -1;
                }
            // Case 2: "this" is an uppercase but "other" is a lowercase
            } else if ((int) startOther >= (int) 'a' && (int) startThis < (int) 'a' ) {
                if ((int) startThis < (int) startOther - 32) {
                    return -1;
                }
            // Case 3: They are the same case
            } else {
                if ((int) startThis < (int) startOther) {
                    return -1;
                }
            }
        }
        // Otherwise
        return 1;
    }

    public static void main(String[] args) {

    }
}
