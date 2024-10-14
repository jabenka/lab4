package org.example;

import org.example.api.Dto.CafeDTO;
import org.example.api.Factory.CafeFactory;
import org.example.api.Misc.Archiver;
import org.example.persistence.Repositories.DBConnection;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var storage = new CafeFactory();
        Scanner scanner = new Scanner(System.in);
        var connect= DBConnection.getInstance();

        storage.readFromFile("cafe.txt");
        storage.setListStorage(storage.readFromXml("cafe.xml"));
        storage.setListStorage(storage.readDataFromJsonFile("cafe.json"));
        System.out.println("Список тортов загружен");
        for (CafeDTO dto : storage.getList()) {
            System.out.println(dto.toString());
        }
        System.out.println();

        System.out.println("Введите данные о торте в форме id,name,description:");
        String input = scanner.nextLine();
        String[] parts = input.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String description = parts[2];
        if (storage.getList().stream().anyMatch(cafeDTO -> cafeDTO.getId() == id) ||
                storage.getList().stream().anyMatch(CafeDTO -> CafeDTO.getDescription().equals(description)) ||
                storage.getList().stream().anyMatch(CategoryDto -> CategoryDto.getName().equals(name))
        ) {
            System.out.println("Такой торт уже существует!");
            return;
        }

        //System.out.println("Вывод из xml файла");
        //storage.readFromXml();
        System.out.println(storage.getList());
        CafeDTO newCake = new CafeDTO(id, name, description);
        connect.AddToTable(newCake);
        storage.addToListStorage(newCake);
        storage.addToMapStorage(id, newCake);

        storage.writeToFile("cafe.txt");
        storage.writeToXml("cafe.xml", storage.getList());
        storage.writeDataToJsonFile("cafe.json", storage.getList());
        System.out.println("Обновленный список тортов" + storage.getList());
        boolean ans = false;

        do {
            System.out.println("Выберете поле для сортировки(id,name,description):");
            String typeSort = scanner.nextLine();
            typeSort = typeSort.toLowerCase();


            switch (typeSort) {

                case "id":
                    storage.getList().sort(Comparator.comparing(CafeDTO::getId));
                    System.out.println("Торты сортированные по id: ");
                    for (CafeDTO dto : storage.getList()) {
                        System.out.println(dto.toString());
                    }
                    ans = true;
                    break;

                case "name":
                    storage.getList().sort(Comparator.comparing(CafeDTO::getName));
                    System.out.println("Торты сортированные по названию: " + storage.getList());
                    ans = true;
                    break;

                case "description":
                    storage.getList().sort(Comparator.comparing(CafeDTO::getDescription));
                    System.out.println("Торты сортированные по описанию: " + storage.getList());
                    ans = true;
                    break;
                default:
                    System.out.println("Введено неверное поле");
                    break;
            }
        } while (!ans);
        //storage.writeToXml();


        String[] files = new String[]{
                "cafe.txt",
                "cafe.json",
                "cafe.xml"
        };

        Archiver archiver = new Archiver();
        try {
            archiver.createZipArchive("zipResult.zip", files);
            archiver.createJarArchive("jarResult.jar", files);
        }catch (IOException e)  {
            e.printStackTrace();
        }

        System.out.println("DATABASE");
        List<CafeDTO> dtos=connect.getFromDataBase();
        for (CafeDTO dto:dtos){
            System.out.println(dto);
        }



    }
}

