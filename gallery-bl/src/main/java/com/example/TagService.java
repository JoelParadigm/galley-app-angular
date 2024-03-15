package com.example;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@NoArgsConstructor
public class TagService {
    public int upperbound_TagLength = 30;
    public int lowerbound_TagLength = 2;
    public List<String> parseWords(String text) {
        if(text.isEmpty()) return null;
        List<String> words = Arrays.asList(text.split("\\s+"));
        return words;
    }

    public boolean correctTagFormat(String s) {
        if(isStringLeadingCharSpecial(s)){
            // remove the leading character for parsing
            s = s.substring(1);
        }
        return  isStringAlphanumeric(s)
                    && isStringCorectLength(s, lowerbound_TagLength, upperbound_TagLength)
                ||//OR
                (isStringAlphanumeric(s.substring(0, s.length() - 1))
                    && isLastCharPunctuation(s))
                    && isStringCorectLength(s, lowerbound_TagLength, upperbound_TagLength);
    }

    private boolean isStringLeadingCharSpecial(String  str){
        if(!str.isEmpty()){
            return !Character.isLetterOrDigit(str.charAt(0));
        }
        return false;
    }
    private boolean isStringAlphanumeric(String input) {
        for (char character : input.toCharArray()) {
            // Check if the character is a letter or a digit
            if (!Character.isLetterOrDigit(character)) {
                return false; // Found a non-alphanumeric character
            }
        }
        return true; // All characters are alphanumeric
    }
    private boolean isStringCorectLength(String str, int lowerbound, int upperbound){
        return str.length() >= lowerbound && str.length() <= upperbound;
    }

    private boolean isLastCharPunctuation(String s){
        return s.charAt(s.length()-1) == ','
                || s.charAt(s.length()-1) == '.'
                || s.charAt(s.length()-1) == '?'
                || s.charAt(s.length()-1) == '!';
    }

    public String stripStringToPlainTagFormat(String s){
        if(isStringLeadingCharSpecial(s))
            s = s.substring(1);
        if(isLastCharPunctuation(s))
            s = s.substring(0, s.length() - 1);
        return s;
    }

    public boolean uniqueTag(List<String> tags, String tag){
        return !tags.contains(tag);
    }
}
