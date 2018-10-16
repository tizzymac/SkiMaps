package tizzy.skimapp.ResortModel;


import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by tizzy on 10/15/18.
 */

public class Resort {
    private static Resort sResort;
    private GeoNode[] mNodes;
    private List<Lift> mLifts;
    private List<Run> mRuns;

    public static Resort get(Context context) {
        if (sResort == null) {
            sResort = new Resort(context);
        }
        return sResort;
    }

    private Resort(Context context) {
        mLifts = new ArrayList<>();
        mRuns = new ArrayList<>();

        // Get Input Streams
        InputStream resortIS = null;
        InputStream nodeIS = null;
        try {
            resortIS = context.getAssets().open("resort.xml");
            nodeIS = context.getAssets().open("nodes.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        readResortFromXML(nodeIS, resortIS);
    }

//    public Resort(InputStream nodeIS, InputStream liftIS) {
//
//        mLifts = new ArrayList<>();
//        mRuns = new ArrayList<>();
//
//        readResortFromXML(nodeIS, liftIS);
//
//    }

    public List<Run> getRuns() {
        return mRuns;
    }

    public List<Lift> getLifts() {
        return mLifts;
    }

    private void readResortFromXML(InputStream nodeIS, InputStream liftIS) {

        // First read in all nodes
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(nodeIS);
            doc.getDocumentElement().normalize();

            NodeList nNodeList = doc.getElementsByTagName("Node");
            mNodes = new GeoNode[nNodeList.getLength()];
            for (int i = 0; i < nNodeList.getLength(); i++) {
                Node nNode = nNodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;

                    // Add to node list
                    mNodes[i] = new GeoNode(
                            element.getAttribute("id"),
                            Integer.parseInt(element.getAttribute("x")),
                            Integer.parseInt(element.getAttribute("y")),
                            Integer.parseInt(element.getAttribute("z")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Then read in Lifts & Runs
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(liftIS);
            doc.getDocumentElement().normalize();

            // Lifts
            NodeList nLiftList = doc.getElementsByTagName("Lift");
            for (int i = 0; i < nLiftList.getLength(); i++) {
                Node nNode = nLiftList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element liftElement = (Element) nNode;

                    // Add to lift list
                    mLifts.add(new Lift(
                            liftElement.getAttribute("name"),
                            mNodes[Integer.parseInt(liftElement.getAttribute("start")) - 1],
                            mNodes[Integer.parseInt(liftElement.getAttribute("end")) - 1]
                    ));
                }
            }

            // Runs
            NodeList nRunList = doc.getElementsByTagName("Run");
            for (int i = 0; i < nRunList.getLength(); i++) {
                Node nNode = nRunList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element runElement = (Element) nNode;

                    // Add run to list
                    mRuns.add(new Run(
                            runElement.getAttribute("name"),
                            runElement.getAttribute("level"),
                            mNodes[Integer.parseInt(runElement.getAttribute("start")) - 1],
                            mNodes[Integer.parseInt(runElement.getAttribute("end")) - 1]
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
