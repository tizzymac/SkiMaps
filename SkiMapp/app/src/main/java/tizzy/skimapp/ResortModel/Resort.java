package tizzy.skimapp.ResortModel;


import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by tizzy on 10/15/18.
 */

public class Resort implements Serializable {
    private static Resort sResort;
    private tizzy.skimapp.ResortModel.Node[] mNodes;
    private List<Lift> mLifts;
    private List<Run> mRuns;
    private List<Facility> mFacilities;
    // mountains

    public static Resort get(Context context) {
        if (sResort == null) {
            sResort = new Resort(context);
        }
        return sResort;
    }

    public Resort(Context context) {
        mLifts = new ArrayList<>();
        mRuns = new ArrayList<>();
        mFacilities = new ArrayList<>();

        // Get Input Streams
        InputStream resortIS = null;
        try {
            resortIS = context.getAssets().open("resort.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        readResortFromXML(resortIS);
    }

    public List<tizzy.skimapp.ResortModel.Node> getNodes() {
        return Arrays.asList(mNodes);
    }

    public List<Run> getRuns() {
        return mRuns;
    }

    public List<Lift> getLifts() {
        return mLifts;
    }

    public List<Facility> getFacilities() { return mFacilities; }

    private void readResortFromXML(InputStream resortIS) {

        // Then read in Lifts & Runs
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(resortIS);
            doc.getDocumentElement().normalize();

            // Nodes
            NodeList nNodeList = doc.getElementsByTagName("Node");
            mNodes = new tizzy.skimapp.ResortModel.Node[nNodeList.getLength()];
            for (int i = 0; i < nNodeList.getLength(); i++) {
                Node nNode = nNodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;

                    // Add to node list
                    mNodes[i] = new tizzy.skimapp.ResortModel.Node(
                            element.getAttribute("id"),
                            new Coords(
                                    Double.parseDouble(element.getAttribute("x")),
                                    Double.parseDouble(element.getAttribute("y")),
                                    Double.parseDouble(element.getAttribute("z")))
                    );
                }
            }


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

            // Facilities
            NodeList nFacList = doc.getElementsByTagName("Facility");
            for (int i = 0; i < nFacList.getLength(); i++) {
                Node nNode = nFacList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element facElement = (Element) nNode;

                    // Add facility to list
                    mFacilities.add(new Facility(
                            facElement.getAttribute("type"),
                            facElement.getAttribute("name"),
                            mNodes[Integer.parseInt(facElement.getAttribute("location")) - 1]
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();

        // Lifts
        for (Lift lift : mLifts) {

            boolean edgeExists = false;
            // Check if there is an edge with the same start and end nodes
            for (Edge e : edges) {
                if ((e.getSource() == lift.getStart())
                        && (e.getDestination() == lift.getEnd())) {
                    e.addLift(lift);
                    edgeExists = true;
                }
            }
            if (!edgeExists) {
                // Create new edge
                Edge newEdge = new Edge(
                        lift.getName(),  // TODO
                        lift.getStart(),
                        lift.getEnd(),
                        1);     // TODO
                newEdge.addLift(lift);
                edges.add(newEdge);
            }

            //edges.add(lift.getEdge()); // no... should be creating an edge with the lifts
        }

        // Runs
        for (Run run : mRuns) {

            // Check if there is an edge with the same start and end nodes
            boolean edgeExists = false;
            for (Edge e : edges) {
                if ((e.getSource() == run.getStart())
                        && (e.getDestination() == run.getEnd())) {
                    e.addRun(run);
                }
            }
            if (!edgeExists) {
                // Create new edge
                Edge newEdge = new Edge(
                        run.getName(),  // TODO
                        run.getStart(),
                        run.getEnd(),
                        1);     // TODO
                newEdge.addRun(run);
                edges.add(newEdge);
            }

//            edges.add(run.getEdge());
        }

        return edges;
    }
}
