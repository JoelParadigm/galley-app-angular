package com.example.vm;

import com.example.DatabaseClearService;
import com.example.GalleryService;
import com.example.ImageService;
import com.example.TagService;
import com.example.dto.HashtagNameDto;
import com.example.dto.ImageDto;
import com.example.entities.HashtagEntity;
import com.example.entities.ImageEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.annotation.*;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@VariableResolver(DelegatingVariableResolver.class)
public class PageVm {
    @WireVariable
    private GalleryService galleryService;
    @WireVariable
    private ImageService imageService;
    @WireVariable
    private TagService tagService;
    private AImage uploadedImageThumbnail = null; // it's the thumbnail, rename this variable later!!
    private byte[] uploadedImage = null; // it's the thumbnail, rename this variable later!!
    private String selectedLabelText = "Select";
    private String filenameLabelText = "-";
    private String sizeLabelText =  "-";
    private String pictureTitle = "";

    private String userInput = ""; // bad name - user input for tags
    private String submittedTags = "";
    private String badTagFormatMessage = "";
    private List<String> tags = new ArrayList<>();

    private String imageDescription = "";
    private int imageDescriptionRows = 5;
    private int imageDimensions = 300;


    @Init
    public void init() {

    }

    @Command
    @NotifyChange({"selectedLabelText", "filenameLabelText", "uploadedImageThumbnail"})
    public void uploadImage(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) {

        if (event != null) {
            selectedLabelText = "Selected.\n";
            Media media = event.getMedia();
            if (media instanceof AImage) {
                filenameLabelText = "Image uploaded";
                filenameLabelText = "$default_name:"+"{"+imageService.catNameGenerator()+"}\n";
                uploadedImage = media.getByteData();
                byte[] thumbnailBytes = imageService.generateThumbnail(media.getByteData(), 400, 400);
                try {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(thumbnailBytes);
                    uploadedImageThumbnail = new AImage("Thumbnail", byteArrayInputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                    uploadedImageThumbnail = null;
                    uploadedImage = null;
                }
            } else {
                filenameLabelText = "File is not an image\n";
                uploadedImageThumbnail = null;
                uploadedImage = null;
            }
        } else {
            selectedLabelText = "Select image\n";
            filenameLabelText = "-\n";
            uploadedImageThumbnail = null;
            uploadedImage = null;
        }
    }

    @Command

    public void cancel() {
        // logic for cancel button or navigation to another page
    }

    @Command
    @NotifyChange({"sizeLabelText", "imageDescription"})
    public void upload() {
        if (uploadedImage != null) {
            int imageSize = uploadedImage.length;
            String title = pictureTitle;
            if(Objects.equals(title, "")){
                title = imageService.catNameGenerator();
            }
            imageDescription = imageService.normalizeString(imageDescription);
            sizeLabelText = String.format("Image Information - Size: %d bytes, Title: %s\n", imageSize, title);
            sizeLabelText += String.format("Number of tags: %d\n", tags.size());
            sizeLabelText += String.format("Description length: %d\n", imageDescription.length());
            Set<HashtagNameDto> hashtagset = new HashSet<>();
            for(String s : tags){
                hashtagset.add(new HashtagNameDto(null, s));
            }
            ImageDto imageDto = new ImageDto(null,
                                            title,
                                            imageDescription,
                                            LocalDateTime.now(),
                                            uploadedImage,
                                            imageService.generateThumbnail(uploadedImage, imageDimensions, imageDimensions),
                                            hashtagset);
            galleryService.saveOrUpdateImage(imageDto);

            Executions.sendRedirect("gallery.zul");
        } else {
            sizeLabelText  = "No image uploaded.";
        }
    }

    @Command
    public void navigate(String target) {
        String zulPath = target + ".zul";
        Executions.createComponents(zulPath, null, null);
    }

    @Command
    @NotifyChange({"submittedTags", "userInput", "badTagFormatMessage"})
    public void userInputChanged() {
        submittedTags = "";
        badTagFormatMessage = "";
        if(!userInput.isEmpty()){
            List<String> words = tagService.parseWords(userInput);
            List<String> correctTags = new ArrayList<>();
            for(int i = 0; i < words.size(); i++) {
                String word = words.get(i);
                if(tagService.correctTagFormat(word)){
                    if(tagService.uniqueTag(correctTags, word)) {
                        String correctTag = tagService.stripStringToPlainTagFormat(words.get(i));
                        submittedTags += String.format("#%s ", correctTag);
                        correctTags.add(correctTag);
                    }
                } else {
                    //submittedTags += "{bad$tag$} ";
                    badTagFormatMessage = "Tags can only contain one special character, and if such as a prefix;\n";
                    badTagFormatMessage += String.format("Tags can only be between %d and %d characters long;",
                            tagService.lowerbound_TagLength,
                            tagService.upperbound_TagLength);
                }
            }
            tags = correctTags;
        } else {
            tags = null;
        }
    }
}
