package org.example.api.Factory;

import org.example.api.Dto.CafeDTO;
import org.example.persistence.Repositories.AbstractStorage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class CafeFactory extends AbstractStorage<CafeDTO> {

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String desc=parts[2];

                CafeDTO cake= CafeDTO.builder()
                        .id(id)
                        .name(name)
                        .description(desc)
                        .build();
                addToListStorage(cake);
                addToMapStorage(id, cake);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (CafeDTO bus : listStorage) {
                bw.write(bus.getId() + "," +
                        bus.getName() + "," +
                        bus.getDescription()+"\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CafeDTO> readFromXml(String filename) {
        List<CafeDTO> list=new ArrayList<>();

        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("cafe");


            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    CafeDTO cake = CafeDTO.builder()
                            .id((Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent())))
                            .name((element.getElementsByTagName("name").item(0).getTextContent()))
                            .description((element.getElementsByTagName("description").item(0).getTextContent()))
                            .build();
                    list.add(cake);
                }
            }
            } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void writeToXml(String filename, List<CafeDTO> list) {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("cafe");
            document.appendChild(root);

            for (CafeDTO vehicle : list) {
                Element cake = document.createElement("cake");

                Element id = document.createElement("id");
                id.appendChild(document.createTextNode(String.valueOf(vehicle.getId())));
                cake.appendChild(id);

                Element type = document.createElement("name");
                type.appendChild(document.createTextNode(vehicle.getName()));
                cake.appendChild(type);

                Element model = document.createElement("description");
                model.appendChild(document.createTextNode(vehicle.getDescription()));
                cake.appendChild(model);


                root.appendChild(cake);
            }


                    Transformer tr = TransformerFactory.newInstance().newTransformer();
                    DOMSource source = new DOMSource(document);
                    FileOutputStream fos = new FileOutputStream("Cake.xml");
                    StreamResult result = new StreamResult(new File(filename));

                    tr.setOutputProperty(OutputKeys.INDENT, "yes");


                    tr.transform(source, result);
                } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public CafeDTO findByName(String name) {
        return listStorage.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(CafeDTO.builder().build());
    }

    public List<CafeDTO> readDataFromJsonFile(String fileName) {
        List<CafeDTO> cakes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            JSONArray jsonArray = new JSONArray(jsonContent.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CafeDTO cake = CafeDTO.builder()
                        .id(jsonObject.getInt("id"))
                        .name(jsonObject.getString("name"))
                        .description(jsonObject.getString("description"))
                        .build();
                cakes.add(cake);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cakes;
    }

    public void writeDataToJsonFile(String fileName, List<CafeDTO> cakes) {
        JSONArray jsonArray = new JSONArray();
        for (CafeDTO cake : cakes) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", cake.getId());
            jsonObject.put("name", cake.getName());
            jsonObject.put("description", cake.getDescription());
            jsonArray.put(jsonObject);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
