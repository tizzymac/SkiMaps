package tizzy.skimapp.ResortModel;


import android.content.Context;

import com.google.firebase.FirebaseApp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by tizzy on 10/15/18.
 */

public class Resort implements Serializable {
    private static Resort sResort;
    private LinkedList<tizzy.skimapp.ResortModel.Node> mNodes;
    private LinkedList<Lift> mLifts;
    private LinkedList<Run> mRuns;
    private static String mRegion;

    private LinkedList<Facility> mFacilities;
    private String mEmergencyNumber;
    // mountains

    public static Resort get(Context context) {
        if (sResort == null) {
            sResort = new Resort(context);
        }
        return sResort;
    }

    public Resort(Context context) {
        mLifts = new LinkedList<>();
        mRuns = new LinkedList<>();
        mFacilities = new LinkedList<>();

        // Get Input Streams
        InputStream resortIS = null;
        try {
            resortIS = context.getAssets().open("buttermilk.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Build Document
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(resortIS);
            doc.getDocumentElement().normalize();

            // Initialize Firebase
            FirebaseApp.initializeApp(context);

            // Read in Resort data from xml
            readRegion(doc);
            readNodes(doc);
            readRuns(doc);
            readLifts(doc);
            readFacilities(doc);
            readEmergency(doc);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private void readNodes(Document doc) {

        NodeList nNodeList = doc.getElementsByTagName("Node");
        mNodes = new LinkedList<>();
        for (int i = 0; i < nNodeList.getLength(); i++) {
            Node nNode = nNodeList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;

                // Add to node list
                mNodes.add( new tizzy.skimapp.ResortModel.Node(
                        element.getAttribute("id"),
                        new Coords(
                                Double.parseDouble(element.getAttribute("x")),
                                Double.parseDouble(element.getAttribute("y")),
                                Double.parseDouble(element.getAttribute("z")))
                ));
            }
        }
    }

    private void readRuns(Document doc) {
        NodeList nRunList = doc.getElementsByTagName("Run");
        for (int i = 0; i < nRunList.getLength(); i++) {
            Node nNode = nRunList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element runElement = (Element) nNode;

                // Add run to list
                Run r = new Run(
                        runElement.getAttribute("name"),
                        runElement.getAttribute("level"),
                        mRegion,
                        mNodes.get(Integer.parseInt(runElement.getAttribute("start")) - 1),
                        mNodes.get(Integer.parseInt(runElement.getAttribute("end")) - 1)
                );
                // Add midpoints
                if (!(runElement.getAttribute("midpoints")).isEmpty()) {
                    List<String> midpointStrs = Arrays.asList((runElement.getAttribute("midpoints")).split(","));
                    // Convert to nodes
                    List<tizzy.skimapp.ResortModel.Node> midpointNodes = new ArrayList<>();
                    for (String s : midpointStrs) {
                        midpointNodes.add(mNodes.get(Integer.parseInt(s) - 1));
                    }
                    r.setMidpoints(midpointNodes);
                }
                mRuns.add(r);
            }
        }

        // alphabetize
        Collections.sort(mRuns);
    }

    private void readLifts(Document doc) throws ParseException {
        NodeList nLiftList = doc.getElementsByTagName("Lift");
        for (int i = 0; i < nLiftList.getLength(); i++) {
            Node nNode = nLiftList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element liftElement = (Element) nNode;

                // Add to lift list
                mLifts.add(new Lift(
                        liftElement.getAttribute("name"),
                        mNodes.get(Integer.parseInt(liftElement.getAttribute("start")) - 1),
                        mNodes.get(Integer.parseInt(liftElement.getAttribute("end")) - 1),
                        Integer.parseInt(liftElement.getAttribute("capacity")),
                        liftElement.getAttribute("open"),
                        liftElement.getAttribute("close")
                ));
            }
        }
        // alphabetize
        Collections.sort(mLifts);
    }

    private void readFacilities(Document doc) {
        NodeList nFacList = doc.getElementsByTagName("Facility");
        for (int i = 0; i < nFacList.getLength(); i++) {
            Node nNode = nFacList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element facElement = (Element) nNode;

                // Add facility to list
                mFacilities.add(new Facility(
                        facElement.getAttribute("type"),
                        facElement.getAttribute("name"),
                        mNodes.get(Integer.parseInt(facElement.getAttribute("location")) - 1)
                ));
            }
        }
    }

    private void readEmergency(Document doc) {
        NodeList nFacList = doc.getElementsByTagName("SkiPatrol");
        Element emergencyElement = (Element) nFacList.item(0);
        String number = emergencyElement.getAttribute("number");
        mEmergencyNumber = number;
    }

    private void readRegion(Document doc) {
        NodeList nFacList = doc.getElementsByTagName("Resort");
        Element regionElement = (Element) nFacList.item(0);
        String region = regionElement.getAttribute("region");
        mRegion = region;
    }

    public LinkedList<tizzy.skimapp.ResortModel.Node> getNodes() {
        return mNodes;
    }

    public LinkedList<Run> getRuns() {
        return mRuns;
    }

    public LinkedList<Lift> getLifts() {
        return mLifts;
    }

    public LinkedList<Facility> getFacilities() { return mFacilities; }


    public LinkedList<Edge> getEdges() {
        LinkedList<Edge> edges = new LinkedList<>();
        edges.addAll(mLifts);
        edges.addAll(mRuns);
        return edges;
    }

    public String getEmergencyNumber() {
        return mEmergencyNumber;
    }

    public static String getRegion() {
        return mRegion;
    }

    public Run getRun(String runName) {
        for (Run r : mRuns) {
            if (runName.equals(r.getName())) {
                return r;
            }
        }
        return null;
    }
}
