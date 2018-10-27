package org.thunlp.thulac.data;

/**
 * A class which represent a tagged word, that is, a word with a tag.
 */
public class TaggedWord {
	public String word;
	public String tag;
	public int startOffset;
	public int endOffset;

	public TaggedWord() {
		this.word = "";
	}

	public TaggedWord(String word, String tag, int startOffset, int endOffset) {
		this.word = word;
		this.tag = tag;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
	}
}
