
/**
 * Represents a question to appear on a pop-up.
 * @author quincy
 */
public class Question {

	/**
	 * The question
	 */
	private String question;
	/**
	 * The four possible answers of which one is correct
	 */
	private String[] choices;
	/**
	 * The index in the array choices to the correct answer
	 */
	private int correctIndex;

	/**
	 * Contsructs the Question object
	 * @param question The question
	 * @param choices Array of four choices
	 * @param correctIndex The zero-indexed index to the right choice
	 */
	public Question(String question, String[] choices, int correctIndex) {
		this.question = question;
		this.choices = new String[4];
		for (byte i = 0; i < 4; ++i) {
			this.choices[i] = choices[i];
		}
		this.correctIndex = correctIndex;
	}
	
	/**
	 * Gets the question to ask
	 * @return The question
	 */
	public String getQuestion() {
		return question;
	}
	
	/**
	 * Gets the nth choice 
	 * @param n The number of the choice to get
	 * @return The nth choice
	 */
	public String getChoice(int n) {
		return choices[n-1];
	}
	
	/**
	 * Checks if the given string is the correct choice
	 * @param s A choice that might be the correct chocie
	 * @return True if s is the correct choice
	 */
	public boolean answerIs(String s) {
		return choices[correctIndex].equals(s);
	}
    
}
