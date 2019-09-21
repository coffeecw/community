package com.cw.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
        //问题对象
        private List<QuestionDTO> questions;
        //上一页
        private boolean showPrevious;
        //第一页
        private boolean showFirstPage;
        //下一页
        private boolean showNext;
        //最后一页
        private boolean showEndPage;
        //当前页
        private Integer page;
        //页码
        private List<Integer> pages = new ArrayList<>();
        //总页数
        private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        //计算总页数
        totalPage=totalCount%size==0?totalCount/size:totalCount/size+1;
        //防止页码数为负
        if(page<1){
            page=1;
        }
        //防止页码数超过最大页数
        if(page>totalPage){
            page = totalPage;
        }
        this.page = page;
        pages.add(page);
        for(int i = 1; i <= 3;i++){
            if(page-i > 0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }
        //判断上一页是否展示
        showPrevious = page==1?false:true;
        //判断下一页是否展示
        showNext = page==totalPage?false:true;
        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
    }
}
