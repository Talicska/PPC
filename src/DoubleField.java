/**
 * Created by Talicska on 2014.07.12..
 */

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class DoubleField extends JTextField {

    public DoubleField() {
        super();
    }

    public DoubleField( int cols ) {
        super( cols );
    }

    public DoubleField( String value) {
        super( value);
    }

    @Override
    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }

    static class UpperCaseDocument extends PlainDocument {

        @Override
        public void insertString( int offs, String str, AttributeSet a )
                throws BadLocationException {

            if ( str == null ) {
                return;
            }

            char[] chars = str.toCharArray();
            boolean ok = true;

            for ( int i = 0; i < chars.length; i++ ) {
                try {
                    Double.parseDouble(String.valueOf(chars[i]));
                } catch ( NumberFormatException exc ) {
                    if ( chars[i] != '.' ) {
                        ok = false;
                        break;
                    }
                }
            }

            if ( ok )
                super.insertString( offs, new String( chars ), a );
        }
    }

}