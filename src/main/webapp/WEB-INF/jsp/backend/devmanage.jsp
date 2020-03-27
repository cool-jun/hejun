<%--
  Created by IntelliJ IDEA.
  User: 13698
  Date: 2020/1/3
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="common/header.jsp"%>
<div class="clearfix"></div>
<div class="row">

    <div class="col-md-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>
                    APP 审核列表 <i class="fa fa-user"></i><small>${userSession.userName}
                    - 您可以通过搜索或者其他的筛选项对APP的信息进行审核操作。^_^</small>
                </h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <form method="post" action="listalldev">
                    <input type="hidden" name="pageIndex" value="1" />
                    <ul>
                        <li>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12">开发者账号</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input name="queryCode" type="text" class="form-control col-md-7 col-xs-12" value="${queryCode}"/>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12">开发者名称</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input name="queryName" type="text" class="form-control col-md-7 col-xs-12" value="${queryName}">
                                </div>
                            </div>
                        </li>
                        <li><button type="submit" class="btn btn-primary"> 查 &nbsp;&nbsp;&nbsp;&nbsp;询 </button></li>
                    </ul>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <p class="text-muted font-13 m-b-30"></p>
                <div id="datatable-responsive_wrapper"
                     class="dataTables_wrapper form-inline dt-bootstrap no-footer">
                    <div class="row">
                        <div class="col-sm-12">
                            <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap dataTable no-footer dtr-inline collapsed"
                                   cellspacing="0" width="100%" role="grid" aria-describedby="datatable-responsive_info" style="width: 100%;">
                                <thead>
                                <tr role="row">
                                    <th class="sorting_asc" tabindex="0"
                                        aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                        style="width: 70px;" aria-label="First name: activate to sort column descending"
                                        aria-sort="ascending">开发者ID</th>
                                    <th class="sorting" tabindex="0"
                                        aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                        style="width: 10px;"
                                        aria-label="Last name: activate to sort column ascending">
                                        开发者账号</th>
                                    <th class="sorting" tabindex="0"
                                        aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                        style="width: 90px;"
                                        aria-label="Last name: activate to sort column ascending">
                                        开发者名称</th>
                                    <th class="sorting" tabindex="0"
                                        aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                        style="width: 50px;"
                                        aria-label="Last name: activate to sort column ascending">
                                        开发者邮箱</th>
                                    <th class="sorting" tabindex="0"
                                        aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                        style="width: 170px;"
                                        aria-label="Last name: activate to sort column ascending">
                                        开发者信息</th>
                                    <th class="sorting" tabindex="0"
                                        aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                        style="width: 30px;"
                                        aria-label="Last name: activate to sort column ascending">
                                        操作</th>
                                    <th class="sorting" tabindex="0"
                                        aria-controls="datatable-responsive" rowspan="1" colspan="1"
                                        style="width: 30px;"
                                        aria-label="Last name: activate to sort column ascending">
                                        操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="devInfo" items="${devInfoList}" varStatus="status">
                                    <tr role="row" class="odd">
                                        <%--<td tabindex="0" class="sorting_1">${devInfo.softwareName}</td>--%>
                                        <td>${devInfo.id }</td>
                                        <td>${devInfo.devCode }</td>
                                        <td>${devInfo.devName }</td>
                                        <td>${devInfo.devEmail }</td>
                                        <td>${devInfo.devInfo }</td>
                                        <td>
                                            <button type="button" class="btn btn-default" onclick="window.location.href = 'deleteDev?id=' + ${devInfo.id};"
                                                    data-toggle="tooltip" data-placement="top" title="" data-original-title="删除开发者">删除</button>
                                        </td>
                                            <td>
                                                <button type="button" class="btn btn-default" onclick="window.location.href = 'modifyDev?id=' + ${devInfo.id};"
                                                        data-toggle="tooltip" data-placement="top" title="" data-original-title="修改开发者">修改</button>
                                            </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-5">
                            <div class="dataTables_info" id="datatable-responsive_info"
                                 role="status" aria-live="polite">共${pages.totalCount }条记录
                                ${pages.currentPageNo }/${pages.totalPageCount }页</div>
                        </div>
                        <div class="col-sm-7">
                            <div class="dataTables_paginate paging_simple_numbers"
                                 id="datatable-responsive_paginate">
                                <ul class="pagination">
                                    <c:if test="${pages.currentPageNo > 1}">
                                        <li class="paginate_button previous"><a
                                                href="javascript:page_nav(document.forms[0],1);"
                                                aria-controls="datatable-responsive" data-dt-idx="0"
                                                tabindex="0">首页</a>
                                        </li>
                                        <li class="paginate_button "><a
                                                href="javascript:page_nav(document.forms[0],${pages.currentPageNo-1});"
                                                aria-controls="datatable-responsive" data-dt-idx="1"
                                                tabindex="0">上一页</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${pages.currentPageNo < pages.totalPageCount }">
                                        <li class="paginate_button "><a
                                                href="javascript:page_nav(document.forms[0],${pages.currentPageNo+1 });"
                                                aria-controls="datatable-responsive" data-dt-idx="1"
                                                tabindex="0">下一页</a>
                                        </li>
                                        <li class="paginate_button next"><a
                                                href="javascript:page_nav(document.forms[0],${pages.totalPageCount });"
                                                aria-controls="datatable-responsive" data-dt-idx="7"
                                                tabindex="0">最后一页</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<%@ include file="common/footer.jsp"%>
<script src="${pageContext.request.contextPath }/statics/localjs/rollpage.js"></script>