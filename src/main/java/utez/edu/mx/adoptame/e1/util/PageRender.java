package utez.edu.mx.adoptame.e1.util;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender <T>{
    private String url;
    private Page<T> page;

    private int totalPages;
    private int numberItemsPerPage;
    private int actualPage;

    private List<PageItem> pages;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;

        this.pages = new ArrayList<>();

        numberItemsPerPage = 6;
        totalPages = page.getTotalPages();
        actualPage = page.getNumber() + 1;

        int from, until;

        if (totalPages <= numberItemsPerPage) {
            from = 1;
            until = totalPages;
        } else {
            if (actualPage <= numberItemsPerPage/2) {
                from = 1;
                until = numberItemsPerPage;
            } else if (actualPage >= (totalPages - numberItemsPerPage/2)) {
                from = totalPages - numberItemsPerPage + 1;
                until = numberItemsPerPage;
            } else {
                from = actualPage - numberItemsPerPage / 2;
                until = numberItemsPerPage;
            }
        }

        for (int i = 0; i < until; i++) {
            pages.add(new PageItem(from + i, actualPage == from + i));
        }

    }

    public String getUrl() {
        return url;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getActualPage() {
        return actualPage;
    }

    public List<PageItem> getPages() {
        return pages;
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isHasNext(){
        return page.hasNext();
    }

    public boolean isHasPrevious() {
        return page.hasPrevious();
    }

}
