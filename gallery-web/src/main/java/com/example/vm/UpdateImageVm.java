package com.example.vm;

import com.example.GalleryService;
import com.example.ImageService;
import com.example.TagService;
import com.example.dto.HashtagNameDto;
import com.example.dto.ImageDto;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.util.*;

@Getter
@Setter
@VariableResolver(DelegatingVariableResolver.class)
public class UpdateImageVm {
    @WireVariable
    private GalleryService galleryService;
    @WireVariable
    private ImageService imageService;
    @WireVariable
    private TagService tagService;
    private ImageDto image;
    private String  log = "";
    private String  tagUserInput = "";
    private List<String> inputUnchanged = new ArrayList<>();
    private String  formattedTagNames = "";

    private boolean tagsNotChanged = true;
    private List<String> correctTags;
    @Init
    public void init() {

        Long imageId = Long.parseLong(Executions.getCurrent().getParameter("imageId"));
        image = galleryService.getImageById(imageId);
        log = "" + imageId;
        inputUnchanged.add(image.getName());            // Name index 2
        inputUnchanged.add(image.getDescription());     // Description index 1
        inputUnchanged.add(getTagString());             // Tags index 2
        String[] wordsArray = tagUserInput.split("\\s+");
        List<String> wordsList = Arrays.asList(wordsArray);
        formattedTagNames = tagService.getTagsInPrintableFormat(wordsList);
    }
    @Command
    @NotifyChange("log")
    public void saveChanges() {
        if(!tagsNotChanged){
            Set<HashtagNameDto> hashtagset = new HashSet<>();
            for(String s : correctTags){
                hashtagset.add(new HashtagNameDto(null, s));
            }
            List<Long> tagIds = tagService.extractIds(image.getHashtags());
            image.setHashtags(hashtagset);
        }
        galleryService.saveOrUpdateImage(image);
        Executions.sendRedirect("imageDetail.zul?imageId=" + image.getId());
        log = "save";
    }
    @Command
    @NotifyChange("log")
    public void cancel() {
        log = "cancel";
        Executions.sendRedirect("imageDetail.zul?imageId=" + image.getId()+"&cancel=1");
    }

    @Command
    @NotifyChange("log")
    public void deleteImage() {
        log = "delete";
        galleryService.deleteImageById(image.getId());
        Executions.sendRedirect("gallery.zul");
    }

    public AImage convertToAImage(byte[] imageData) {
        try {
            return new AImage("Image", imageData);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception according to your needs
            return null;
        }
    }

    public String getTagString() {
        tagUserInput = "";
        for(HashtagNameDto tag: image.getHashtags()){
            tagUserInput += tag.getName() + " ";
        }
        log = "save";
        return  tagUserInput;
    }

    @Command
    @NotifyChange({"tagUserInput", "log", "formattedTagNames"})
    public void commitTags() {
        log = "commit tags";
        tagsNotChanged = (tagUserInput.equals(inputUnchanged.get(2)));
        if(tagsNotChanged)
            return;
        log += " tag input changed";
        if(!tagUserInput.isEmpty()){
            correctTags = tagService.getCorrectTagsFromUserInput(tagUserInput);
            formattedTagNames = tagService.getTagsInPrintableFormat(correctTags);
        } else {
            correctTags = null;
        }
    }

}
