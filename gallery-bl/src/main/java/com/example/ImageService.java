package com.example;

import lombok.NoArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@NoArgsConstructor
public class ImageService {

    public byte[] generateThumbnail(byte[] originalImage, int maxWidth, int maxHeight){
        try {
            InputStream inputStream = new ByteArrayInputStream(originalImage);
            BufferedImage originalBufferedImage = ImageIO.read(inputStream);

            BufferedImage thumbnailBufferedImage = Scalr.resize(originalBufferedImage, Scalr.Method.QUALITY,
                    Scalr.Mode.AUTOMATIC, maxWidth, maxHeight, Scalr.OP_ANTIALIAS);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnailBufferedImage, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String catNameGenerator(){
        List<String> adjectiveList = new ArrayList<>();
        adjectiveList.add("Chunky");    adjectiveList.add("Funky");     adjectiveList.add("Crazy");
        adjectiveList.add("Fat");       adjectiveList.add("Chill");     adjectiveList.add("Mischievous");
        adjectiveList.add("Cool");      adjectiveList.add("Generic");   adjectiveList.add("Evil");
        adjectiveList.add("Angry");     adjectiveList.add("Hungry");

        List<String> catList = new ArrayList<>();
        catList.add("Bob");     catList.add("Bobcat");  catList.add("Leo");
        catList.add("Feline");  catList.add("Cat");     catList.add("Kitkat");
        catList.add("Kitty");   catList.add("Romeo");   catList.add("Destroyer 3000");
        catList.add("!Dog");

        String adjective = getRandomItem(adjectiveList, "Cool");
        String cat = getRandomItem(catList, "Cat");
        return adjective + " " + cat;
    }

    private static String getRandomItem(List<String> list, String defItem) {
        if (list != null && !list.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(list.size());
            return list.get(randomIndex);
        } else {
            return defItem;
        }
    }

    public String normalizeString(String input) {
        // Replace consecutive line breaks with a single one
        String normalizedLineBreaks = input.replaceAll("\\n{2,}", "\n");

        // Replace consecutive tabs with a maximum of two tabs
        String normalizedTabs = normalizedLineBreaks.replaceAll("\\t{2,}", "\t\t");

        return normalizedTabs;
    }
}
