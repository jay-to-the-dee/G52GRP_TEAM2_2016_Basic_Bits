package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.SortedSet;

import static android.content.ContentValues.TAG;

public class Parser {

    private Data rdfData;

    private DocumentBuilderFactory factory;
    //private DocumentBuilderFactory staffFactory;

    public Parser(Data data) {
        rdfData = data;
        factory = DocumentBuilderFactory.newInstance();
        //staffFactory = DocumentBuilderFactory.newInstance();
    }

    public void doParse (InputStream isStudent, InputStream isStaff, InputStream isModule) {

        parseStaff(isStaff);
        parseStudents(isStudent);
        parseModule(isModule);

        testOutput();
    }

    // contains testing output only, can be deleted
    private void testOutput() {
        /*// check first student has correct values
        Student firstStudent = rdfData.getStudents().first();
        Log.d(TAG, "WHALE first student      : " + firstStudent.toString());
        Log.d(TAG, "WHALE first student tutor: " + firstStudent.getTutor().toString());

        // check first staff has correct values
        Staff firstStaff = rdfData.getStaff().first();
        Log.d(TAG, "WHALE first staff:   " + firstStaff.toString());

        // check first module has correct values
        Module firstModule = rdfData.getModules().first();
        Log.d(TAG, "WHALE first module: " + firstModule.toString());

        // check students are added to staff tutee lists correctly
        Staff lupin = rdfData.findStaff("jkrrl");
        for (Student s : lupin.getTutees()) {
            Log.d(TAG, "WHALE   LUPIN TUTEES:   " + s.toString());
        }

        Staff hagrid = rdfData.findStaff("jkrrh");
        for (Student s : hagrid.getTutees()) {
            Log.d(TAG, "WHALE   HAGRID TUTEES:   " + s.toString());
        }

        Staff mcgonagall = rdfData.findStaff("jkrmm");
        for (Student s : mcgonagall.getTutees()) {
            Log.d(TAG, "WHALE   MCGONAGALL TUTEES:   " + s.toString());
        }

        Staff snape = rdfData.findStaff("jkrss");
        for (Student s : snape.getTutees()) {
            Log.d(TAG, "WHALE   SNAPE TUTEES:   " + s.toString());
        }*/

        for (Staff s : rdfData.getStaff()) {
            Log.d(TAG, "WHALE   " + s.getName() + " MODULES:");

            for (Module m : s.getModulesTaught()) {
                Log.d(TAG, "WHALE    " + m.toString());
            }

            Log.d(TAG, "WHALE");
        }

    }

    private void parseStudents(InputStream is) {

        BufferedReader r = new BufferedReader(new InputStreamReader(is));

        //Log.d(TAG, "WHALE student:");

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            Element element = doc.getDocumentElement();
            NodeList nList = doc.getElementsByTagName("rdf:Description");

            // loop through 'description' elements (nList)
            // print details of each student for debugging
            // create and add new Students to students SortedSet
            for (int i=0 ; i<nList.getLength() ; i++) {
                Node node = nList.item(i);
                Element eItem = (Element)node;

                String rdfFullName = getValue("v:FN", eItem);
                String rdfEmail = getValue("v:email", eItem);
                String rdfUsername = getValue("information:username", eItem);
                String rdfTutor = getValue("information:tutor", eItem);

                //Log.d(TAG, "WHALE " + i + "   FULL NAME: " + rdfFullName);
                //Log.d(TAG, "WHALE " + i + "       EMAIL: " + rdfEmail);
                //Log.d(TAG, "WHALE " + i + "    USERNAME: " + rdfUsername);
                //Log.d(TAG, "WHALE " + i + "       TUTOR: " + rdfTutor);

                Staff tutor = rdfData.findStaff(rdfTutor);

                Student student = new Student(rdfFullName, rdfEmail, rdfUsername, tutor);
                //Log.d(TAG, "WHALE " + i + "   PRINT OBJ: " + student.toString());
                //Log.d(TAG, "WHALE " + i + "   TUTOR STR: " + student.getTutor().toString());

                int setSize = rdfData.addStudent(student);
                //Log.d(TAG, "WHALE " + i + "    SET SIZE: " + setSize);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseStaff(InputStream is) {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));

        //Log.d(TAG, "WHALE staff:");

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            Element element = doc.getDocumentElement();
            NodeList nList = doc.getElementsByTagName("rdf:Description");

            // loop through 'description' elements (nList)
            // print details of each staff for debugging
            // create and add new Staff to staff SortedSet
            for (int i=0 ; i<nList.getLength() ; i++) {
                Node node = nList.item(i);
                Element eItem = (Element)node;

                String rdfFullName = getValue("v:FN", eItem);
                String rdfEmail = getValue("v:email", eItem);
                String rdfUsername = getValue("information:username", eItem);
                String rdfPhone = getValue("v:tel", eItem);
                String rdfOffice = getValue("information:office", eItem);
                String rdfURL = getValue("information:url", eItem);

                //Log.d(TAG, "WHALE " + i + "   FULL NAME: " + rdfFullName);
                //Log.d(TAG, "WHALE " + i + "       EMAIL: " + rdfEmail);
                //Log.d(TAG, "WHALE " + i + "    USERNAME: " + rdfUsername);
                //Log.d(TAG, "WHALE " + i + "       PHONE: " + rdfPhone);
                //Log.d(TAG, "WHALE " + i + "      OFFICE: " + rdfOffice);
                //Log.d(TAG, "WHALE " + i + "         URL: " + rdfURL);

                Staff staff = new Staff(rdfFullName, rdfEmail, rdfUsername, rdfPhone, rdfOffice, rdfURL);
                //Log.d(TAG, "WHALE " + i + "   PRINT OBJ: " + staff.toString());

                int setSize = rdfData.addStaff(staff);
                //Log.d(TAG, "WHALE " + i + "    SET SIZE: " + setSize);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseModule(InputStream is) {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));

        //Log.d (TAG, "WHALE modules: ");

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            Element element = doc.getDocumentElement();
            NodeList nList = doc.getElementsByTagName("rdf:Description");

            // loop through 'description' elements (nList)
            // print details of each module for debugging
            // create and add new Module to module SortedSet

            //Log.d (TAG, "WHALE nlist length: " + nList.getLength());

            for (int i=0 ; i<nList.getLength() ; i++) {
                Node node = nList.item(i);
                Element eItem = (Element)node;

                String rdfID = getValue("information:id", eItem);
                String rdfName = getValue("information:name", eItem);
                String rdfSemester = getValue("information:semester", eItem);

                Module module = new Module(rdfID, rdfName, rdfSemester);
                //Log.d(TAG, "WHALE " + i + " PRINT OBJ: " + module.toString());

                // creates nodelist of all elements with particular tag name
                // each lecturer will be a different element
                NodeList lecturerNodeList = eItem.getElementsByTagName("information:lecturer");
                //Log.d(TAG, "WHALE   nodelist length: " + lecturerNodeList.getLength());

                for (int j=0 ; j<lecturerNodeList.getLength() ; j++) {
                    // for each lecturer in nodeList, get the value of the first child node (the lectrer's username)
                    String lecturerUsername = lecturerNodeList.item(j).getChildNodes().item(0).getNodeValue();

                    // find lecturer in staff set and add to lecturers set in module
                    module.addLecturers(rdfData.findStaff(lecturerUsername));
                }

                // output for debugging
                /*for (Staff s : module.getLecturers()) {
                    Log.d(TAG, "WHALE   LECTURERS:   " + s.toString());
                }*/

                // output set size for debugging
                int setSize = rdfData.addModule(module);
                //Log.d(TAG, "WHALE " + i + "    SET SIZE: " + setSize);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // takes tag name and an Element (node in the graph)
    // builds a NodeList: getElementsByTagName retrieves elements with the required tag
    //                    build list of child nodes of the first item in getElementsByTagName
    //                    (there should only ever be one element with each tag if this function is called)
    // returns the value of the first element of the above NodeList
    private String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }


}
