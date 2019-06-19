package cn.handy.utils;

import lombok.Getter;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hanshuai
 * @Description: {List分页查询}
 * @date 2019/6/19 13:19
 */
@Getter
public class ListPageUtil {

    private List<String> data;

    /**
     * 上一页
     */
    private int lastPage;

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 下一页
     */
    private int nextPage;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总数据条数
     */
    private int totalCount;

    public ListPageUtil(List<String> data, int pageNum, int pageSize) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("数据不能为空!");
        }
        this.data = data;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalCount = data.size();
        this.totalPage = (totalCount + pageSize - 1) / pageSize;
        this.lastPage = pageNum - 1 > 1 ? pageNum - 1 : 1;
        this.nextPage = pageNum >= totalPage ? totalPage : pageNum + 1;

    }

    public List<String> getData() {
        int fromIndex = (pageNum - 1) * pageSize;
        if (fromIndex >= data.size()) {
            return Collections.emptyList();//空数组
        }
        if (fromIndex < 0) {
            return Collections.emptyList();//空数组
        }
        int toIndex = pageNum * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        return data.subList(fromIndex, toIndex);
    }
}