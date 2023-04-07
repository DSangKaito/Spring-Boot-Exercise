package com.runsystem.springbootdemo.common;

import com.runsystem.springbootdemo.payloads.response.StudentResponse;
import lombok.Data;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PageReponse<T> {
    private List<T> responseList = new ArrayList<T>();

    private int pageNumber;

    public PageReponse(List<T> responseList, int pageNumber) {
        this.responseList = responseList;
        this.pageNumber = pageNumber;
    }

    public Page<T> pagingResponse(){
        // 1. PageListHolder
        PagedListHolder<T> studentPagedListHolder = new PagedListHolder<T>(responseList);
        studentPagedListHolder.setPage(pageNumber - 1);
        studentPagedListHolder.setPageSize(2);

        //2. PropertyComparator
        List<T> pageSlice = studentPagedListHolder.getPageList();
        //PropertyComparator.sort(pageSlice, new MutableSortDefinition("id", true, true));

        //3. PageImpl
        Pageable pageable = PageRequest.of(pageNumber - 1, responseList.size());
        return new PageImpl<>(pageSlice, pageable, responseList.size());
    }
}
