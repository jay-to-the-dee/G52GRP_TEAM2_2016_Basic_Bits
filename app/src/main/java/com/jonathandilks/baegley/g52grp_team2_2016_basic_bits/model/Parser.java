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

        Student firstStudent = rdfData.getStudents().first();
        Log.d(TAG, "WHALE first student: " + firstStudent.toString());

        Staff firstStaff = rdfData.getStaff().first();
        Log.d(TAG, "WHALE first staff:   " + firstStaff.toString());

        Module firstModule = rdfData.getModules().first();
        Log.d(TAG, "WHALE first module: " + firstModule.toString());
    }

    private void parseStudents(InputStream is) {
        // BUILD EMPTY STAFF OBJECT BECAUSE TUTOR ISN'T ADDED INTO XML YET
        Staff emptyStaff = new Staff("","","","","","");

        BufferedReader r = new BufferedReader(new InputStreamReader(is));

        Log.d(TAG, "WHALE student:");

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

                Log.d(TAG, "WHALE " + i + "   FULL NAME: " + rdfFullName);
                Log.d(TAG, "WHALE " + i + "       EMAIL: " + rdfEmail);

                Student student = new Student(rdfFullName, rdfEmail, "", emptyStaff);
                Log.d(TAG, "WHALE " + i + "   PRINT OBJ: " + student.toString());

                int setSize = rdfData.addStudent(student);
                Log.d(TAG, "WHALE " + i + "    SET SIZE: " + setSize);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseStaff(InputStream is) {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));

        Log.d(TAG, "WHALE staff:");

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

                Log.d(TAG, "WHALE " + i + "   FULL NAME: " + rdfFullName);
                Log.d(TAG, "WHALE " + i + "       EMAIL: " + rdfEmail);
                Log.d(TAG, "WHALE " + i + "    USERNAME: " + rdfUsername);
                Log.d(TAG, "WHALE " + i + "       PHONE: " + rdfPhone);
                Log.d(TAG, "WHALE " + i + "      OFFICE: " + rdfOffice);
                Log.d(TAG, "WHALE " + i + "         URL: " + rdfURL);

                Staff staff = new Staff(rdfFullName, rdfEmail, rdfUsername, rdfPhone, rdfOffice, rdfURL);
                Log.d(TAG, "WHALE " + i + "   PRINT OBJ: " + staff.toString());

                int setSize = rdfData.addStaff(staff);
                Log.d(TAG, "WHALE " + i + "    SET SIZE: " + setSize);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseModule(InputStream is) {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));

        // BUILD EMPTY STAFF OBJECT BECAUSE NOT DEALING WITH LECTURERS YET
        Staff emptyStaff = new Staff("","","","","","");

        Log.d (TAG, "WHALE modules: ");

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            Element element = doc.getDocumentElement();
            NodeList nList = doc.getElementsByTagName("rdf:Description");

            // loop through 'description' elements (nList)
            // print details of each module for debugging
            // create and add new Module to module SortedSet

            Log.d (TAG, "WHALE nlist length: " + nList.getLength());

            for (int i=0 ; i<nList.getLength() ; i++) {
                Node node = nList.item(i);
                Element eItem = (Element)node;

                String rdfID = getValue("information:id", eItem);
                String rdfName = getValue("information:name", eItem);
                String rdfSemester = getValue("information:semester", eItem);

                Module module = new Module(rdfID, rdfName, rdfSemester, emptyStaff);
                Log.d(TAG, "WHALE " + i + " PRINT OBJ: " + module.toString());

                int setSize = rdfData.addModule(module);
                Log.d(TAG, "WHALE " + i + "    SET SIZE: " + setSize);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // takes tag name and an Element (node in the graph)
    // builds a NodeList: getElementsByTagName retrieves elements with the required tag
    //                    build list of child nodes of the first item in getElementsByTagName
    // returns the value of the first element of the above NodeList
    private String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}
