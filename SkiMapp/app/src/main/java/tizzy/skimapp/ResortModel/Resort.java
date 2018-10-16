package tizzy.skimapp.ResortModel;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by tizzy on 10/15/18.
 */

public class Resort {

    private static GeoNode[] mNodes;
    private static List<Lift> mLifts;
    private static List<Run> mRuns;

    public Resort(InputStream nodeIS, InputStream liftIS) {

        mLifts = new ArrayList<>();
        mRuns = new ArrayList<>();

        readResortFromXML(nodeIS, liftIS);

    }

    private static void readResortFromXML(InputStream nodeIS, InputStream liftIS) {

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
                            mNodes[Integer.parseInt(liftElement.getAttribute("start"))],
                            mNodes[Integer.parseInt(liftElement.getAttribute("end"))]
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
                            mNodes[Integer.parseInt(runElement.getAttribute("start"))],
                            mNodes[Integer.parseInt(runElement.getAttribute("end"))]
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
