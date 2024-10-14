package org.example.api.Factory;

import org.example.api.Dto.BusDto;
import org.example.persistence.Repositories.AbstractStorage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BusFactory extends AbstractStorage<BusDto> {
    private Document doc;
    DocumentBuilder db;
    public BusFactory() {
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = db.parse(new File("cake.xml"));
        }catch(ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int power=Integer.parseInt(parts[2]);
                int seatings=Integer.parseInt(parts[3]);

                BusDto bus= new BusDto(id,name,power,seatings);
                addToListStorage(bus);
                addToMapStorage(id, bus);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (BusDto bus : listStorage) {
                bw.write(bus.getId() + "," +
                        bus.getName() + "," +
                        bus.getPower() +","+
                        bus.getSeatings()+"\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void readFromXml() {
//        int id=0;
//        if(listStorage.size()!=0) {
//             id = listStorage.get(listStorage.size()-1).getId();
//        }
//        try {
//
//            Node root=doc.getDocumentElement();
//            NodeList nodes = root.getChildNodes();
//            for (int i=0;i<nodes.getLength();i++) {
//                Node node = nodes.item(i);
//                if(node.getNodeType()!=Node.TEXT_NODE) {
//                    NodeList childNodes = node.getChildNodes();
//                    for (int j=0;j<childNodes.getLength();j++) {
//                        Node childNode = childNodes.item(j);
//                        if(childNode.getNodeType()!=Node.TEXT_NODE) {
//                            addToListStorage(new BusDto(++id,childNode.getNodeName(),childNode.getChildNodes().item(0).getTextContent()));
//                        }
//                    }
//
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Override
//    public void writeToXml() {
//        List<BusDto> list=new ArrayList<BusDto>();
//        list=listStorage;
//        readFromXml();
//        for(int i=0;i<list.size();i++) {
//            if(listStorage.contains(list.get(i))) {
//                list.remove(i);
//            }
//        }
//        listStorage=list;
//
//        int i=0;
//        try {
//            while (i < listStorage.size()) {
//                    Node root = doc.getDocumentElement();
//
//                    Element rootElement = doc.createElement("CafeEntity");
//                    Element id = doc.createElement("id");
//                    id.setTextContent(String.valueOf(listStorage.get(i).getId()));
//                    Element name = doc.createElement("name");
//                    name.setTextContent(String.valueOf(listStorage.get(i).getName()));
//                    Element description = doc.createElement("description");
//                    description.setTextContent(String.valueOf(listStorage.get(i).getDescription()));
//
//                    rootElement.appendChild(id);
//                    rootElement.appendChild(name);
//                    rootElement.appendChild(description);
//                    root.appendChild(rootElement);
//
//                    Transformer tr = TransformerFactory.newInstance().newTransformer();
//                    DOMSource source = new DOMSource(doc);
//                    FileOutputStream fos = new FileOutputStream("Cake.xml");
//                    StreamResult result = new StreamResult(fos);
//                    tr.transform(source, result);
//                    i++;
//                }
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//
//    }

    @Override
    public BusDto findByName(String name) {
        return listStorage.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(new BusDto(-1,"",-1,-1));
    }


}
