package com.dw;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;

public class ParseEmojiXml {

    static LinkedList normal = new LinkedList();
    static LinkedList simple = new LinkedList();
    static LinkedList complex = new LinkedList();


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParser sp = SAXParserFactory.newInstance().newSAXParser();
        InputStream in = ClassLoader.getSystemResourceAsStream("emoji.xml");

        sp.parse(in, new DefaultHandler() {
            @Override
            public void endDocument() throws SAXException {
                super.endDocument();
                printResult();
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);
                if ("exp".equals(qName)) {
                    if (attributes != null)
                    {
                        String value = attributes.getValue("unified");
                        processValue(value);
                    }
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                super.endElement(uri, localName, qName);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                super.characters(ch, start, length);
            }
        });
    }

    private static void processValue(String input) {
        input = input.replaceAll("_20E3|_FE0F","");
        if (input.contains("_")) {
            if (input.startsWith("1F")) {
                complex.add(change(input));
            }else {
                simple.add(change(input));
            }
        } else {
            normal.add(change(input));
        }
    }

    private  static void printResult(){
        Collections.sort(normal);
        Collections.sort(simple);
        Collections.sort(complex);
        System.out.println("共"+(normal.size() + simple.size() + complex.size())+" 条记录");
        System.out.println(normal);
        System.out.println(simple);
        System.out.println(complex);
    }

    private  static String change(String input){
//        return input;
        String s[] = input.split("_");
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String ss : s) {
            if (ss.startsWith("1F")) {
                char c[] = Character.toChars(Integer.parseInt(ss, 16));
                ss = Integer.toHexString(c[0]) + Integer.toHexString(c[1]);
            }
            if (!first) sb.append("_");
            sb.append(ss);
            first = false;
        }
        return sb.toString();
    }
}
