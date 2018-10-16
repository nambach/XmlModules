package utils;

import com.sun.xml.internal.stream.events.XMLEventAllocatorImpl;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import javax.xml.stream.util.XMLEventAllocator;
import java.io.*;
import java.util.*;

public class StAXUtils {

    public static void main(String[] args) {
//        String content = FileUtils.readTextContent(FileUtils.getFilePath("raw/stax.xml"));
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("id", "5");
//
//        String result = extract(content, "book", map);
//
//        FileUtils.exportFile(result, FileUtils.getFilePath("refined/stax.xml"));

        demoIterator();
    }

    public static void demoCursor() {
        FileInputStream inputStream;
        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            xmlInputFactory.setProperty(XMLInputFactory.IS_VALIDATING, false);
            xmlInputFactory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);

            File file = new File(FileUtils.getFilePath("raw/stax.xml"));
            inputStream = new FileInputStream(file);

            XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(inputStream);

            loop:
            while (true) {
                int eventType = reader.getEventType();

                switch (eventType) {
                    case XMLStreamConstants.START_ELEMENT:
                        System.out.println("<" + reader.getLocalName() + ">");
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        System.out.println("</" + reader.getLocalName() + ">");
                        break;
                    case XMLStreamConstants.PROCESSING_INSTRUCTION:
                        System.out.println("<?" + reader.getPIData() + "?>");
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        System.out.println("Chars:" + reader.getText() + ":Chars");
                        break;
                    case XMLStreamConstants.COMMENT:
                        System.out.println("<!--" + reader.getText() + "-->");
                        break;
                    case XMLStreamConstants.SPACE:
                        System.out.println("sp:" + reader.getText() + ":sp");
                        break;
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println("=>" + reader.getCharacterEncodingScheme());
                        break;
                    case XMLStreamConstants.END_DOCUMENT:
                        System.out.println("=>Fin.");
                        reader.close();
                        break loop;
                }
                reader.next();

            }
            inputStream.close();

        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void demoIterator() {
        Reader fileReader = null;
        XMLEventReader reader = null;
        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            xmlInputFactory.setProperty(XMLInputFactory.IS_VALIDATING, false);
            xmlInputFactory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);

            fileReader = new FileReader(FileUtils.getFilePath("raw/stax.xml"));

            reader = xmlInputFactory.createXMLEventReader(fileReader);

            while (reader.hasNext()) {

                XMLEvent event;

                try {
                    event = reader.nextEvent();
                } catch (XMLStreamException e) {
                    System.out.println("skip..." + e);
                    continue;
                }

                if (event.isStartElement()) {
                    StartElement startElement = (StartElement) event;
                    System.out.println("<" + startElement.getName() + ">");

                    startElement.getAttributes().forEachRemaining(o -> {
                        Attribute attribute = (Attribute) o;
                        QName attributeName = attribute.getName();
                        String value = attribute.getValue();

                        System.out.println(attributeName + "=\"" + value + "\"");
                    });
                }

                if (event.isEndElement()) {
                    EndElement endElement = (EndElement) event;
                    System.out.println("</" + endElement.getName() + ">");
                    if (endElement.getName().getLocalPart().equals("library")) {
                        break;
                    }
                }

                if (event.isCharacters()) {
                    Characters characters = (Characters) event;
                    if (!characters.isWhiteSpace()) {
                        System.out.println("txt:" + characters.getData().trim() + ":txt");
                    }

                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (XMLStreamException e) {
            System.out.println("Stream Error");
        } catch (Exception e) {
            System.out.println("Some Error: " + e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
//                    System.out.println("close reader");
                }
            } catch (XMLStreamException ignored) {
            }

            try {
                if (fileReader != null) {
                    fileReader.close();
//                    System.out.println("close fileReader");
                }
            } catch (IOException ignored) {
            }
        }

    }

    public static void demoCombined() {
        try {
            XMLInputFactory factory = XMLInputFactory.newFactory();
            factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
            factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
            factory.setEventAllocator(new XMLEventAllocatorImpl());
            XMLEventAllocator allocator = factory.getEventAllocator();

            XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(FileUtils.getFilePath("raw/stax.xml")));
            int eventType;
            String tagName;

            while (reader.hasNext()) {

                eventType = reader.getEventType();

                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    StartElement element = getEvent(reader, allocator).asStartElement();
                    tagName = element.getName().toString();
                    System.out.println("<" + tagName + ">");

                    element.getAttributes().forEachRemaining(o -> {
                        Attribute attribute = (Attribute) o;
                        System.out.println(attribute.getName() + "=" + attribute.getValue());
                    });
                }
                if (eventType == XMLStreamConstants.END_ELEMENT) {
                    EndElement element = getEvent(reader, allocator).asEndElement();
                    System.out.println("</" + element.getName() + ">");
                    if (element.getName().getLocalPart().equals("library")) {
                        break;
                    }
                }

                try {
                    reader.next();
                } catch (Exception e) {
                    System.out.println("Err found - skip...");
                    e.printStackTrace();
                    System.out.println();

                    if (!e.toString().contains("XMLStreamException")) {
                        e.printStackTrace();
                        break;
                    }
                }
            }

        } catch (XMLStreamException s) {
            System.out.println("ERR:" + s.getMessage());
        } catch (FileNotFoundException ignored) {
        }
    }

    public static XMLEvent getEvent(XMLStreamReader streamReader, XMLEventAllocator allocator) throws XMLStreamException {
        return allocator.allocate(streamReader);
    }

    public static String wellForming(String src) {
        String result = "";

        Stack<String> stack = new Stack<>();

        StringReader stringReader = null;
        XMLEventReader reader = null;

        StringWriter stringWriter = null;
        XMLEventWriter writer = null;

        XMLEventFactory factory = XMLEventFactory.newFactory();
        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            xmlInputFactory.setProperty(XMLInputFactory.IS_VALIDATING, false);
            xmlInputFactory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);

            stringReader = new StringReader(src);
            reader = xmlInputFactory.createXMLEventReader(stringReader);

            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
            stringWriter = new StringWriter();
            writer = xmlOutputFactory.createXMLEventWriter(stringWriter);

            while (reader.hasNext()) {

                XMLEvent event;

                try {
                    event = reader.nextEvent();
                } catch (XMLStreamException e) {
                    System.out.println("skip...");

                    e.printStackTrace();
//                    writer.add(factory.createEndElement("", null, stack.pop()) );
                    continue;
                }

                if (event.isStartElement()) {
                    StartElement startElement = (StartElement) event;

                    startElement.getAttributes().forEachRemaining(o -> {
                        Attribute attribute = (Attribute) o;
                        QName attributeName = attribute.getName();
                        String value = attribute.getValue();
                    });

                    stack.push(startElement.getName().toString());


                }

                if (event.isEndElement()) {
                    EndElement endElement = (EndElement) event;

                    stack.pop();
                }

                writer.add(event);
            }

        } catch (XMLStreamException e) {
            System.out.println("Stream Error");
        } catch (Exception e) {
            System.out.println("Some Error: " + e);
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (XMLStreamException ignored) {
            }

            if (stringReader != null) {
                stringReader.close();
            }
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (XMLStreamException ignored) {
            }

            try {
                if (stringWriter != null) {
                    result = stringWriter.toString();
                    stringWriter.close();
                }
            } catch (IOException ignored) {
            }
        }

        return result;
    }

    public static String extract(String src, String tagName, Map<String, String> attributes) {
        String result = "";
        boolean inTarget = false;

        Stack<String> stack = new Stack<>();

        StringReader stringReader = null;
        XMLEventReader reader = null;

        StringWriter stringWriter = null;
        XMLEventWriter writer = null;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            xmlInputFactory.setProperty(XMLInputFactory.IS_VALIDATING, false);

            stringReader = new StringReader(src);
            reader = xmlInputFactory.createXMLEventReader(stringReader);

            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
            stringWriter = new StringWriter();
            writer = xmlOutputFactory.createXMLEventWriter(stringWriter);

            while (reader.hasNext()) {

                XMLEvent event;

                try {
                    event = reader.nextEvent();
                } catch (Exception e) {
                    System.out.println("Skip error: " + e.getMessage());
                    continue;
                }

                if (event.isStartElement()) {
                    StartElement startElement = (StartElement) event;

                    if (startElement.getName().getLocalPart().equals(tagName)) {

                        List<String> keys = new ArrayList<>(attributes.keySet());
                        for (int i = 0; i < keys.size(); i++) {
                            String key = keys.get(i);
                            String value = attributes.get(key);

                            Attribute attribute = startElement.getAttributeByName(new QName(key));

                            if (attribute != null) {
                                if (!attribute.getValue().contains(value)) {
                                    break;
                                } else if (i == keys.size() - 1) {
                                    inTarget = true;
                                }
                            }
                        }
                    }

                    if (inTarget) {
                        stack.push(startElement.getName().toString());
                    }
                }

                if (event.isEndElement()) {
                    EndElement endElement = (EndElement) event;

                    if (inTarget) {
                        if (!stack.empty()) {
                            stack.pop();

                            if (stack.empty()) {
                                System.out.println(event.toString());
                                writer.add(event);
                                break;
                            }
                        }
                    }
                }

                if (inTarget) {
                    System.out.println(event.toString());
                    writer.add(event);
                }
            }

        } catch (XMLStreamException e) {
            System.out.println("Stream Error");
        } catch (Exception e) {
            System.out.println("Some Error: " + e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (XMLStreamException ignored) {
            }

            if (stringReader != null) {
                stringReader.close();
            }
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (XMLStreamException ignored) {
            }

            try {
                if (stringWriter != null) {
                    result = stringWriter.toString();
                    stringWriter.close();
                }
            } catch (IOException ignored) {
            }
        }

        return result;
    }
}
