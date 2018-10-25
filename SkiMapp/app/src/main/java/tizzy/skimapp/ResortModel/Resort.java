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
                                    Integer.parseInt(element.getAttribute("x")),
                                    Integer.parseInt(element.getAttribute("y")),
                                    Integer.parseInt(element.getAttribute("z")))
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();

        // Lifts
        for (Lift lift : mLifts) {
            edges.add(lift.getEdge());
        }

        // Runs
        for (Run run : mRuns) {
            edges.add(run.getEdge());
        }

        return edges;
    }
}
