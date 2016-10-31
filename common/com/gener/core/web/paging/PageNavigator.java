package com.gener.core.web.paging;

import java.util.ArrayList;
import java.util.List;

public class PageNavigator
{

    private int          currentPage = 1;

    private int          pageSize    = 15;

    private int          totalSize   = 0;

    private int          totalPage   = 1;

    private List<?>      list        = new ArrayList<Object>();

    private StringBuffer navigator   = new StringBuffer("");

    public PageNavigator()
    {

    }

    public StringBuffer getNavigator()
    {
        navigator = new StringBuffer("");
        totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
        totalPage = totalPage == 0 ? 1 : totalPage;
        // 录入(分页信息)
        navigator.append("<input type=\"hidden\" name=\"webPage.currentPage\" id=\"currentPage\"/>");
        
        navigator.append("<span id=\"pageNaviSpan\" class=\"fr pageRecord\">页次：");
        navigator.append(currentPage);
        navigator.append("/");
        navigator.append(totalPage);
        navigator.append("页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每页显示：");
        navigator.append(pageSize);
        navigator.append("条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本页记录数：");
        navigator.append(list.size());
        navigator.append("条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总记录数：");
        navigator.append(totalSize);
        navigator.append("条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        
        // 录入(分页按钮,首页)
        navigator.append("<span class=\"pagenav\">&nbsp;&nbsp;");
        if(currentPage != 1){
            navigator.append("<a href=\"#\" ");
       //     navigator.append(currentPage == 1 ? "disabled=\"disabled\" " : "");
            navigator.append("onclick=\"$('#currentPage').val('1'); document.getElementsByTagName('form')[0].submit();\" ");
            navigator.append(">首页</a>&nbsp;&nbsp;&nbsp;");
            // 录入(分页按钮,上一页)
            navigator.append("<a href=\"#\" ");
      //      navigator.append(currentPage == 1 ? "disabled=\"disabled\" " : "");
            navigator.append("onclick=\"$('#currentPage').val('");
            navigator.append(currentPage - 1 < 1 ? 1 : currentPage - 1);
            navigator.append("'); document.getElementsByTagName('form')[0].submit();\" ");
            navigator.append(">上一页</a>&nbsp;&nbsp;&nbsp;");
        }else{
            navigator.append("首页&nbsp;&nbsp;&nbsp;上一页&nbsp;&nbsp;&nbsp;");
        }
        if(currentPage + 1 <= totalPage){
            // 录入(分页按钮,下一页)
            navigator.append("<a href=\"#\" ");
     //       navigator.append(currentPage == totalPage ? "disabled=\"disabled\" " : "");
            navigator.append("onclick=\"$('#currentPage').val('");
            navigator.append(currentPage + 1 > totalPage ? totalPage : currentPage + 1);
            navigator.append("'); document.getElementsByTagName('form')[0].submit();\" ");
            navigator.append(">下一页</a>&nbsp;&nbsp;");
            // 录入(分页按钮,末页)
            navigator.append("<a href=\"#\" ");
  //          navigator.append(currentPage == totalPage ? "disabled=\"disabled\" " : "");
            navigator.append("onclick=\"$('#currentPage').val('");
            navigator.append(totalPage);
            navigator.append("'); document.getElementsByTagName('form')[0].submit();\" ");
            navigator.append(">末页</a>&nbsp;&nbsp;&nbsp;");
        }else{
            navigator.append("下一页&nbsp;&nbsp;&nbsp;末页&nbsp;&nbsp;&nbsp;");
        }
        // 录入(分页下拉)
        navigator.append("转到第<select ");
        navigator.append("onchange=\"$('#currentPage').val(this.value); ");
        navigator.append("document.getElementsByTagName('form')[0].submit();\">");
        for (int i = 1; i <= totalPage; i++)
        {
            navigator.append("<option value='");
            navigator.append(i);
            navigator.append("' ");
            navigator.append(i == currentPage ? "selected=\"selected\" " : "");
            navigator.append(">");
            navigator.append(i);
            navigator.append("</option>");
        }
        navigator.append("</select>页 </span></span>");

        return navigator;
    }

    public PageNavigator(int pageSize, int totalSize)
    {
        this.pageSize = pageSize;
        this.totalSize = totalSize;
    }

    public PageNavigator(int currentPage, int pageSize, int totalSize)
    {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public int getTotalPage()
    {
        return totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
    }

    public int getTotalSize()
    {
        return totalSize;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

    public List<?> getList()
    {
        return list;
    }

    public void setList(List<?> list)
    {
        this.list = list;
    }

    public void setTotalSize(int totalSize)
    {
        this.totalSize = totalSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

}
