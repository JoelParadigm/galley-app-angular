package com.example.vm;

import com.example.GalleryService;
import com.example.dto.ImageThumbnailDto;
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

import java.util.List;

@Getter
@Setter
@VariableResolver(DelegatingVariableResolver.class)
public class GalleryVm {

    @WireVariable
    private GalleryService galleryService;

    private List<ImageThumbnailDto>  allImages;
    private String  log = "";


    @Init
    public void init() {
        allImages = galleryService.getAllImageThumbnailDtos();
        log = "" + allImages.size();
    }

    public AImage convertToAImage(byte[] imageData) {
        try {
            return new AImage("Image", imageData);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception according to your needs
            return null;
        }
    }

    @Command
    @NotifyChange("log")
    public void updateLog(@BindingParam("hashtag") String hashtag) {
        String hashtagNameToSearchBy = hashtag.substring(1);
        // reikia apsisaugoti nuo sql injectionu
        log = "hashtag: " + hashtagNameToSearchBy;
    }

    @Command
    @NotifyChange("log")
    public void openEditTab(@BindingParam("imageId") Long imageId) {

        if(imageId != null) {
            log = "image: " + imageId;
            Executions.sendRedirect("imageDetail.zul?imageId=" + imageId);
        }
        else
            log = "image: null";
    }
}
