package cn.congee.api.common;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义分页实现
 *
 * @Author: yang
 * @Date: 2020-12-12 9:04
 */
@Setter
@Getter
public class PageResult<T> {

    private long totalElements = 0;
    private long totalPages = 0;
    private Boolean last = false;
    private Boolean first = true;
    private long size = 0;
    private long number = 0;
    private long numberOfElements = 0;
    private List<T> models = new ArrayList<T>();
    /**
     * 列表求和，需要写该值
     */
    private Object total = null;

    public PageResult() {}

    public PageResult(Page<T> page) {
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
        this.first = page.isFirst();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.models = page.getContent();
        this.numberOfElements = page.getNumberOfElements();
    }

    public PageResult(Integer pageno, Integer limit, long totalElements, List<T> models) {
        this.totalElements = totalElements;
        this.size = limit;
        this.number = pageno;
        this.models = models;
        last = false;
        first = false;
        if (pageno == 0) {
            first = true;
        }
        if (limit == null || limit == 0) {
            limit = 1;
        }
        totalPages = totalElements / limit;
        if (totalElements % limit > 0) {
            totalPages += 1;
        }
        if (totalPages == 0) {
            last = true;
        }
        this.numberOfElements = limit;
        if (pageno == (totalPages - 1)) {
            last = true;
            if (pageno == 0) {
                this.numberOfElements = totalElements;
            } else {
                this.numberOfElements = totalElements - (limit * pageno);
            }
        }
    }

    public PageResult(com.github.pagehelper.Page<T> page, int pageNum, int size) {
        this.totalElements = page.getTotal();
        this.totalPages = page.getPages();
        this.last = ((pageNum + 1) == this.totalPages);
        this.first = (pageNum == 0);
        List<T> res = page.getResult();
        this.size = size;
        this.number = pageNum;
        this.models = res;
        this.numberOfElements = CollectionUtils.isEmpty(res) ? 0 : res.size();
    }

    public static PageResult convert(PageInfo pageInfo) {
        int number = pageInfo.getPageNum() - 1 < 0 ? 0 : pageInfo.getPageNum() - 1;
        return new PageResult(number, pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getList());
    }

}
