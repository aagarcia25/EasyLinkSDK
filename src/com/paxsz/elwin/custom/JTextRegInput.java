package com.paxsz.elwin.custom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextRegInput extends PlainDocument {
    private Pattern pattern;
    private Matcher m;

    public JTextRegInput(String reg) {
        super();
        this.pattern = Pattern.compile(reg);
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) {
            return;
        }
        String tmp = getText(0, offset).concat(str);
        m = pattern.matcher(tmp);
        if (m.matches())
            super.insertString(offset, str, attr);
    }
}
