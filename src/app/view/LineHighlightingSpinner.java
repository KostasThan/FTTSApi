package app.view;

import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

/**
 * This is a custom {@link JSpinner} tha highlights a line in a{@link JTextArea} whenever the JSpinner value is changed.
 */
public class LineHighlightingSpinner extends JSpinner {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8436652441967118346L;

	private JTextArea textArea;
	private int lastValue;


	public LineHighlightingSpinner(JTextArea textArea) {
		
		super();
		super.setValue(1);

		lastValue = 1;
		this.textArea = textArea;

		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				
				//============================
				//This section is responsible for incrementing the spinner value with down arrow and decrement with up arrow. 
				//The reverse of a JSpinner behavior.
				//=====================
				
				//remove the chane listener so we don't end up in infinite loop 
				removeChangeListener(this);

				//if the up arrow is pressed go -1 instead
				//if the down arrow is pressed go +1 instead
				if(lastValue>(Integer) getValue()) {
					lastValue++;
					setValue(lastValue);
				}else if (lastValue<(Integer) getValue()){
					lastValue--;
				}
				setValue(lastValue);

				
				
				//==========================
				//This section decides if the JTextArea has less lines the the spinner and sets its maximum to that amount.
				//Also does not let the spinner to go below 1. 
				//===========================
				
				

				int maxLine = textArea.getLineCount();

				textArea.setText(textArea.getText().strip());
				while (isEmpty(maxLine - 1) && maxLine > 1) {
					maxLine--;
				}
				int lineSelected = (Integer) getValue() - 1;	
				
				if ((Integer) getValue() < 1) {
					setValue(1);
					lastValue = 1;
				} else if ((Integer) getValue() > maxLine) {
					setValue(maxLine);
					lastValue = maxLine;
				}
					
					
					
					//=============================================
					//This section is responsible for selecting a line.
					//=============================================
					
					int start = 0;
					int end = 0;
					
					try {

						start = textArea.getLineStartOffset(lineSelected);
						end = textArea.getLineEndOffset(lineSelected);
						textArea.requestFocusInWindow();
						textArea.select(start, end - 1);
						if ((Integer) getValue() == maxLine) {
							textArea.select(start, end);
						}

					} catch (BadLocationException e1) {
						
					}

				
				
				//add the listener again after all the logic is over
				addChangeListener(this);

			}

		});

	}

	/**
	 * Internally calls for {@link JSpinner#getValue()}.
	 * @return An integer representing the line selected. 
	 */
	public Integer getLine() {
		
		//this is currently offseted by one from the change listener.
		//so we return -1.
		return (Integer) super.getValue() - 1;

	}
	
	//this method returns true if a line in a JTextArea has only white spaces.
	//false otherwise.
	private boolean isEmpty(int lineNum) {
		int start;
		int end;
		String text = "";
		try {

			start = textArea.getLineStartOffset(lineNum);

			end = textArea.getLineEndOffset(lineNum);

			text = textArea.getText(start, end - start);

		} catch (BadLocationException e) {

			return true;

		}
		return text.trim().equals("");
	}

	

}
