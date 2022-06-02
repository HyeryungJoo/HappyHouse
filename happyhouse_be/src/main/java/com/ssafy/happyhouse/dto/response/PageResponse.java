package com.ssafy.happyhouse.dto.response;

import java.util.ArrayList;
import java.util.List;

public class PageResponse {

    private List<Object> content = new ArrayList<>();

    private int totalCount;

    private int totalPage;

    private int page;

    private int size;


    public PageResponse() {
    }

    public void makePageList(int totalCount, int page, int size) {
        this.totalCount = totalCount;
        this.page = page;
        this.size = content.size();
        this.totalPage = (int) Math.ceil((double) totalCount / (double) size);
    }


    public List<Object> getContent() {
        return content;
    }

    public void setContent(List<Object> content) {
        this.content = content;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

