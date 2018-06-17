package com.gildedrose.www;

import java.util.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

class Program
{
    static private String[] options = new String[20];
    static private int numberOfOptions ;

    public static void main(String[] args) {
        // Set the TcpListener on port 13000.

        //read from configuration file

        String responseTitle = new String();

        numberOfOptions = 0;


        responseTitle = "\n-----Gilded Rose Management System-----\n\n" +
                "OUR STOCK:";

        loadConfiguration();


        // load inventory from disk
        String responseBody = null;
        ArrayList<Item> Items = new ArrayList<Item>();

        String[] currentInventory = new String[40];
        currentInventory[0] = null;
        int j = -1;
        try {
            FileReader fileReader = new FileReader("inventory.txt");
            int intRead;
            while ((intRead = fileReader.read()) != -1) {
                char charRead = (char) intRead;
                if (charRead == '@') {
                    j++;
                    currentInventory[j] = new String();


                } else
                    currentInventory[j] += charRead;
            }

            fileReader.close();
            for (int i = 0;
                 i < j + 1;
                 i++)
                Items.add(new Item(GetStringBetweenStrings(currentInventory[i],
                        "addItem:",
                        "/"),
                        Integer.parseInt(GetStringBetweenStrings(currentInventory[i],
                                "expiresInDays:",
                                "/")),
                        Integer.parseInt(GetStringBetweenStrings(currentInventory[i],
                                "quality:",
                                "/"))
                ));


        } catch (IOException ex) {
            ex.toString();


        }


        // Enter the listening loop.
        while (true) {

            responseBody = "\n";
            int i;
            for (i = 0;
                 i < Items.size();
                 i++)
                responseBody += Items.get(i)
                        .Name +
                        "-> Quality:" +
                        Items.get(i)
                                .Quality +
                        ", SellIn: " +
                        Items.get(i)
                                .SellIn +
                        " days \n";
            // String response = responseTitle + responseBody + responseOptions;

            System.out.println(responseTitle);
            System.out.println(responseBody);
            System.out.println("MENU OPTIONS:\n");
            for (i = 0; i < numberOfOptions; i++)
                System.out.println(Integer.toString(i) + ": " + options[i]);

            Scanner input = new Scanner(System.in);

            int selectedOption = input.nextInt();
            String myRequest = options[selectedOption];


            if (myRequest.indexOf("/addItem",
                    0) >=
                    0)
                Items.add(new Item(
                        //Defect 157 Yaki
                        //Fixed!
                        GetStringBetweenStrings(myRequest,
                                "/addItem:",
                                "/"),
                        Integer.parseInt(GetStringBetweenStrings(myRequest,
                                "expiresInDays:",
                                "/")),
                        Integer.parseInt(GetStringBetweenStrings(myRequest,
                                "quality:",
                                "/"))));
            if (myRequest.indexOf("Quit!",
                    0) >
                    0)
                break;

            if (myRequest.indexOf("Tick!",
                    0) >
                    0)

                for (i = 0;
                     i < Items.size();
                     i++) {
                    //Big IF, very proud of it, wanted to document it down but I just don't have the time
                    if (!Items.get(i)
                            .Name.equals(
                            "Aged_Brie" )&&
                            !Items.get(i)
                                    .Name.equals(
                                    "Backstage_passes_to_a_TAFKAL80ETC_concert")) {
                        if (Items.get(i)
                                .Quality >
                                0) {
                            if (!Items.get(i)
                                    .Name.equals(
                                    "Sulfuras,_Hand_of_Ragnaros")) {
                                Items.get(i)
                                        .Quality = Items.get(i)
                                        .Quality -
                                        1;
                            }
                        }
                    } else {
                        if (Items.get(i)
                                .Quality <
                                50) {
                            Items.get(i)
                                    .Quality = Items.get(i)
                                    .Quality +
                                    1;
                            //To Do in future, need to make code clearer. Very Important!

                            if (Items.get(i)
                                    .Name.equals(
                                    "Backstage_passes_to_a_TAFKAL80ETC_concert")) {
                                if (Items.get(i)
                                        .SellIn <
                                        11) {
                                    if (Items.get(i)
                                            .Quality <
                                            50) {
                                        Items.get(i)
                                                .Quality = Items.get(i)
                                                .Quality +
                                                1;
                                    }
                                }
                                /***********************************
                                 * To handle if sellIn <6
                                 * Special consideration
                                 * Need to put good attention
                                 *
                                 *
                                 * **********************************/

                                if (Items.get(i)
                                        .SellIn <
                                        6) {
                                    if (Items.get(i)
                                            .Quality <
                                            50) {
                                        Items.get(i)
                                                .Quality = Items.get(i)
                                                .Quality +
                                                1;
                                    }
                                }
                            }
                        }
                    }

                    if (!Items.get(i)
                            .Name.equals(
                            "Sulfuras,_Hand_of_Ragnaros")) {
                        Items.get(i)
                                .SellIn = Items.get(i)
                                .SellIn -
                                1;
                    }

                    if (Items.get(i)
                            .SellIn <
                            0) {
                        if (!Items.get(i)
                                .Name.equals(
                                "Aged_Brie")) {
                            if (!Items.get(i)
                                    .Name.equals(
                                    "Backstage_passes_to_a_TAFKAL80ETC_concert")) {
                                if (Items.get(i)
                                        .Quality >
                                        0) {
                                    if (!Items.get(i)
                                            .Name.equals(
                                            "Sulfuras,_Hand_of_Ragnaros")) {
                                        Items.get(i)
                                                .Quality = Items.get(i)
                                                .Quality -
                                                1;
                                    }
                                }
                            } else {
                                Items.get(i)
                                        .Quality = Items.get(i)
                                        .Quality -
                                        Items.get(i)
                                                .Quality;
                            }
                        } else {
                            if (Items.get(i)
                                    .Quality <
                                    50) {
                                Items.get(i)
                                        .Quality = Items.get(i)
                                        .Quality +
                                        1;
                            }
                        }
                    }


                }


            try {
                FileWriter c_fileWriter = new FileWriter("inventory.txt",
                        false);
                for (i = 0;
                     i < Items.size();
                     i++)

                    c_fileWriter.write("@addItem:" +
                            Items.get(i)
                                    .Name +
                            "/quality:" +
                            Items.get(i)
                                    .Quality +
                            "/expiresInDays:" +
                            Items.get(i)
                                    .SellIn +
                            "/");
                c_fileWriter.close();
            } catch (IOException ex2) {
                ex2.toString();
            }


        }
    }




    //System.out.printlnLine("Sent: {0}", data);









    private static void loadConfiguration()
    {
        //read from configuration file
        reloadConfig:



        options[0] = null;
        int j;
        try
        {
            FileReader fileReader = new FileReader("gildedRoseConfig.txt");
            j = -1;
            int intRead;
            while ((intRead = fileReader.read()) != -1) {

                char charRead = (char) intRead;

                if (charRead == '@')
                {
                    j++;
                    numberOfOptions++;
                    options[j] = new String();


                }
                else
                    options[j] += charRead;
            }
            fileReader.close();

        }
        catch (IOException ex)
        {
            ex.toString();
            try
            {
                FileWriter c_fileWriter = new FileWriter("gildedRoseConfig.txt");

                c_fileWriter.write("@/Tick!");

                options[numberOfOptions++] = "@/Tick!";
                c_fileWriter.write("@/Quit!");
                options[numberOfOptions++] = "@/Quit!";
                c_fileWriter.write("@/addItem:Aged_Brie/quality:12/expiresInDays:10/");
                options[numberOfOptions++] = "@/addItem:Aged_Brie/quality:12/expiresInDays:10/";
                c_fileWriter.write("@/addItem:+5_Dexterity_Vest/quality:20/expiresInDays:10/");
                options[numberOfOptions++] = "@/addItem:+5_Dexterity_Vest/quality:20/expiresInDays:10/";
                c_fileWriter.write("@/addItem:Elixir_of_the_Mongoose/quality:7/expiresInDays:5/");
                options[numberOfOptions++] = "@/addItem:Elixir_of_the_Mongoose/quality:7/expiresInDays:5/";
                c_fileWriter.write("@/addItem:Sulfuras,_Hand_of_Ragnaros/quality:80/expiresInDays:5/");
                options[numberOfOptions++] = "@/addItem:Sulfuras,_Hand_of_Ragnaros/quality:80/expiresInDays:5/";
                c_fileWriter.write("@/addItem:Backstage_passes_to_a_TAFKAL80ETC_concert/quality:20/expiresInDays:15/");
                options[numberOfOptions++] = "@/addItem:Backstage_passes_to_a_TAFKAL80ETC_concert/quality:20/expiresInDays:15/";


                c_fileWriter.close();
                //  goto reloadConfig;
            }
            catch (IOException ex2) { ex2.toString(); }

        }
    }

    public static String GetStringBetweenStrings(String source,
                                                 String from,
                                                 String to)
    {
        int i,
                j;
        i = source.indexOf(from,0) +
                from.length();
        j = source.indexOf(to,i);
        String toReturn =  source.substring(i,j);
               return toReturn;
    }

    public static String GetHTTPResponse(String content)
    {
        String myResponse;
        myResponse =
                "HTTP/1.1 200 OK\r\n" +
                        "Date: Mon, 27 Jul 2009 12:28:53 GMT\r\n" +
                        "Server: YAKIKOREN\r\n" +
                        "Last-Modified: Wed, 1 Jan 2018 19:15:56 GMT\r\n" +
                        "Content-Length:" +
                        Integer.toString(content.length()) +
                        "\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Connection: Closed\r\n" +
                        "\r\n" +
                        content;
        return myResponse;

    }
}