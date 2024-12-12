package com.example.demo.storage;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class PasswordGenerator {


    public String generate() {
        Random random = new Random();


        StringBuilder password = new StringBuilder();

        int i = random.nextInt(2);

        List<Character> letters = new ArrayList<>(
                List.of('a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h'  , 'i' ,
                        'j' , 'k' , 'l' , 'm' ,'n' , 'q' , 'r' , 's' , 't' , 'u' ,'v' ,  'w' ,'x' , 'y' , 'z'));

        for(int j = 0 ; j < 10 ; j++) {
            if (i == 0) password.append(random.nextInt(10));
            else password.append(letters.get(random.nextInt(letters.size())));
        }

        return password.toString();
    }
}
