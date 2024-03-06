package com.example.vm;

import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.*;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

@Getter
@Setter
@VariableResolver(DelegatingVariableResolver.class)
public class PageVm {

    private AImage uploadedImage = null;
    private String selectedLabelText = "Select";
    private String filenameLabelText = "-";
    private String sizeLabelText =  "-";

    @Init
    public void init() {
        // code to run on page init
    }

    @Command
    @NotifyChange({"selectedLabelText", "filenameLabelText", "uploadedImage"})
    public void uploadImage(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) {

        if (event != null) {
            selectedLabelText = "Selected";
            Media media = event.getMedia();
            if (media instanceof AImage) {
                filenameLabelText = "Image uploaded";
                uploadedImage = (AImage) media;
            } else {
                filenameLabelText = "File is not an image";
                uploadedImage = null;
            }
        } else {
            selectedLabelText = "Select image";
            filenameLabelText = "-";
            uploadedImage = null;
        }
    }

    @Command
    public void cancel() {
        // logic for cancel button or navigation to another page
    }

    @Command
    public void upload() {
        // logic for cancel button or navigation to another page
    }

    @Command
    public void navigate(String target) {
        String zulPath = target + ".zul";
        Executions.createComponents(zulPath, null, null);
    }

}
