package com.example.vm;


import com.example.GalleryService;
import com.example.dto.ImageDto;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@VariableResolver(DelegatingVariableResolver.class)
public class ImageDetailVm {
    @WireVariable
    private GalleryService galleryService;
    private ImageDto image;
    private String  log = "";
    @Init
    public void init() {
        Long imageId = Long.parseLong(Executions.getCurrent().getParameter("imageId"));
        image = galleryService.getImageById(imageId);
        log = "" + imageId;
    }

    public AImage convertToAImage(byte[] imageData) {
        try {
            return new AImage("Image", imageData);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception according to your needs
            return null;
        }
    }

    public String reformatTime(LocalDateTime uploadDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return uploadDate.format(formatter);
    }

    @Command
    @NotifyChange("log")
    public void editImage(){
        log = "edit img";
        Executions.sendRedirect("updateImage.zul?imageId=" + image.getId());
    }

    @Command
    @NotifyChange("log")
    public void filterByTag(@BindingParam("hashtag") String hashtag) {
        String hashtagNameToSearchBy = hashtag.substring(1);
        // reikia apsisaugoti nuo sql injectionu
        log = "hashtag: " + hashtagNameToSearchBy;
    }
}
