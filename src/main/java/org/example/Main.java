package org.example;

import org.example.api.Dto.BusDto;
import org.example.api.Factory.BusFactory;

import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var storage = new BusFactory();
        Scanner scanner = new Scanner(System.in);



        storage.readFromFile("bus.txt");
        System.out.println("Список автобусов загружен");
        for (BusDto dto: storage.getList()) {
            System.out.println(dto.toString());
        }
        System.out.println();

        System.out.println("Введите данные об автобусе в форме id,name,power,setaings :");
        String input = scanner.nextLine();
        String[] parts = input.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int power = Integer.parseInt(parts[2]);
        int seating = Integer.parseInt(parts[3]);

        if(storage.getList().stream().anyMatch(BusDTO-> BusDTO.getId() == id) ||
           storage.getList().stream().anyMatch(BusDto-> BusDto.getPower()== power )||
           storage.getList().stream().anyMatch(CategoryDto-> CategoryDto.getName().equals(name)) ||
           storage.getList().stream().anyMatch(Dto->Dto.getSeatings() == seating)
        ) {
            System.out.println("Такой автобус уже существует!");
            return;
        }
        //System.out.println("Вывод из xml файла");
        //storage.readFromXml();
        System.out.println(storage.getList());




        var NewBus = new BusDto(id, name,power,seating);
        storage.addToListStorage(NewBus);
        storage.addToMapStorage(id, NewBus);

        storage.writeToFile("bus.txt");

        System.out.println("Обновленный список автобусов" + storage.getList());
        boolean ans=false;

    do {
    System.out.println("Выберете поле для сортировки(id,name,power,seating):");
    String typeSort = scanner.nextLine();
    typeSort = typeSort.toLowerCase();


        switch (typeSort) {

            case "id":
                storage.getList().sort(Comparator.comparing(BusDto::getId));
                System.out.println("Автобусы сортированные по id: ");
                for (BusDto dto: storage.getList()) {
                    System.out.println(dto.toString());
                }
                ans=true;
            break;

            case "name":
                storage.getList().sort(Comparator.comparing(BusDto::getName));
                System.out.println("Автобусы сортированные по названию: " + storage.getList());
                ans=true;
            break;

            case "power":
                storage.getList().sort(Comparator.comparing(BusDto::getPower));
                System.out.println("Автобусы сортированные по мощности: " + storage.getList());
                ans=true;
            break;
            case "seating":
                storage.getList().sort(Comparator.comparing(BusDto::getSeatings));
                System.out.println("Автобусы сортированные по количеству сидений: " + storage.getList());

            default:
                System.out.println("Введено неверное поле");
            break;
     }
    }while(!ans);
        //storage.writeToXml();

    }
}