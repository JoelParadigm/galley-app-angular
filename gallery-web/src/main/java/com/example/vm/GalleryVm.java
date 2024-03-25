package com.example.vm;

import com.example.GalleryService;
import com.example.TagService;
import com.example.dto.ImageThumbnailDto;
import com.example.dto.SearchCriteria;
import com.example.search.SearchService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@VariableResolver(DelegatingVariableResolver.class)
public class GalleryVm {

    @WireVariable
    private GalleryService galleryService;
    @WireVariable
    private SearchService searchService;
    @WireVariable
    private TagService tagService;

    private Page<ImageThumbnailDto> imagePage;
    private List<ImageThumbnailDto>  allImages;
    private String  log = "";
    private SearchCriteria criteria;

    private String searchTitle = "";
    private String searchDescription = "";
    private String searchTags = "";

    private int currentPageIndex = 0; // Track the current page index
    private final int PAGE_SIZE = 8; // Page size
    boolean boolTest = false;

    @Init
    public void init() {

        criteria = new SearchCriteria("","", new ArrayList<>());
        loadImagesForCurrentPage();
//        log = "" + allImages.size();
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
    public void searchByTag(@BindingParam("hashtag") String hashtag) {
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

    @Command
    @NotifyChange({"allImages", "currentPageIndex"})
    public void nextPage() {
        if (currentPageIndex < imagePage.getTotalPages() - 1) {
            currentPageIndex++;
            loadImagesForCurrentPage();
        }
    }

    @Command
    @NotifyChange({"allImages", "currentPageIndex"})
    public void previousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            loadImagesForCurrentPage();
        }

    }

    private void loadImagesForCurrentPage() {
        Pageable pageable = PageRequest.of(currentPageIndex, PAGE_SIZE);
        imagePage = searchService.searchImages(criteria, pageable);
        log = "total pages" + imagePage.getTotalPages();
        allImages = imagePage.getContent();
    }

    @Command
    @NotifyChange({"searchTitle", "searchDescription", "searchTags", "allImages", "currentPageIndex"})
    public void searchByCriteria(){
        System.out.printf("test0");
        List<String> tags = tagService.getCorrectTagsFromUserInput(searchTags);
        System.out.printf("test1");
        System.out.println(
                "description: " + (searchDescription != null ? searchDescription : "null") +
                        " title: " + (searchTitle != null ? searchTitle : "null") +
                        " tagnames: " + (searchTags != null ? searchTags : "null") +
                        " num of tags: " + (tags != null ? tags.size() : "null")
        );
        criteria.setTagNames(tags);
        criteria.setDescription(searchDescription);
        criteria.setImageTitle(searchTitle);
        currentPageIndex = 0;
        loadImagesForCurrentPage();
    }

}
