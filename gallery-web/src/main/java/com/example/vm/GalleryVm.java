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

    private int currentPageIndex = 0;
    private final int PAGE_SIZE = 8;

    private boolean nextButtonDisabled = false;
    private boolean previousButtonDisabled = false;

    @Init
    public void init() {

        criteria = new SearchCriteria("","", new ArrayList<>());
        loadImagesForCurrentPage();
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
    @NotifyChange({"log", "allImages", "currentPageIndex", "nextButtonDisabled", "previousButtonDisabled"})
    public void searchByTag(@BindingParam("hashtag") String hashtag) {
        List<String> tags = new ArrayList<>();
        String hashtagNameToSearchBy = hashtag.substring(1);
        tags.add(hashtagNameToSearchBy);
        getPageContentByCriteria(tags, "", "");
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
    @NotifyChange({"allImages", "currentPageIndex", "nextButtonDisabled", "previousButtonDisabled"})
    public void nextPage() {
        if (currentPageIndex < imagePage.getTotalPages() - 1) {
            currentPageIndex++;
            loadImagesForCurrentPage();
        }
    }

    @Command
    @NotifyChange({"allImages", "currentPageIndex", "nextButtonDisabled", "previousButtonDisabled"})
    public void previousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            loadImagesForCurrentPage();
        }

    }

    public boolean isNextButtonDisabled() {
        return (currentPageIndex >= imagePage.getTotalPages() - 1);
    }

    public boolean isPreviousButtonDisabled() {
        return (currentPageIndex <= 0);
    }



    @Command
    @NotifyChange({"searchTitle", "searchDescription", "searchTags", "allImages", "currentPageIndex", "nextButtonDisabled", "previousButtonDisabled"})
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
        getPageContentByCriteria(tags, searchTitle, searchDescription);
    }

    private void getPageContentByCriteria(List<String> tags, String searchTitle, String searchDescription) {
        criteria.setTagNames(tags);
        criteria.setDescription(searchDescription);
        criteria.setImageTitle(searchTitle);
        currentPageIndex = 0;
        loadImagesForCurrentPage();
    }

    private void loadImagesForCurrentPage() {
        Pageable pageable = PageRequest.of(currentPageIndex, PAGE_SIZE);
        imagePage = searchService.searchImages(criteria, pageable);
        allImages = imagePage.getContent();
    }
}
