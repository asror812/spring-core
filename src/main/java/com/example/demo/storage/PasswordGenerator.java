package com.example.demo.storage;


import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class PasswordGenerator {


    public String generate() {
        Random random = new Random();


        StringBuilder password = new StringBuilder();

        char[] letters = new char[] {'a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' ,
                            'h'  , 'i' , 'j' , 'k' , 'l' , 'm' ,'n' , 'o' , 'p', 
                            'q' , 'r' , 's' , 't' , 'u' ,'v' , 'w' ,'x' , 'y' , 
                            'z' , 
                            'A' , 'B' , 'C' , 'D' , 'E' , 'F' , 'G' , 'H', 
                            'I' , 'J' , 'K' , 'L' , 'M' , 'N'  , 'O' , 'P' , 
                            'Q' ,'R' , 'S' , 'T' , 'U','V' , 'W' , 'X' ,
                            'Y' , 'Z'};

        for(int j = 0 ; j < 10 ; j++) {
            password.append(letters[random.nextInt(letters.length)]);
        }

        return password.toString();
    }
}
