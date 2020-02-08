<%--
  Created by IntelliJ IDEA.
  User: 13698
  Date: 2020/1/4
  Time: 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="common/header.jsp"%>
<div class="clearfix"></div>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>修改APP基础信息 <i class="fa fa-user"></i><small>${userSession.userName}</small></h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <form class="form-horizontal form-label-left" action="devinfomodifysave" method="post">
                    <input type="hidden" name="id" id="id" value="${dev.id}">
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">开发者名称<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="devName" class="form-control col-md-7 col-xs-12"
                                   data-validate-length-range="20" data-validate-words="1"
                                   name="devName" value="${dev.devName}" required="required"
                                   placeholder="请输入开发者名称" type="text">
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">开发者账号 <span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="devCode" type="text" class="form-control col-md-7 col-xs-12"
                                   name="devCode" value="${dev.devCode}" readonly="readonly">
                        </div>
                    </div>

                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">开发者邮箱<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="devEmail" class="form-control col-md-7 col-xs-12"
                                   name="devEmail" value="${dev.devEmail}" required="required"
                                   data-validate-length-range="20" data-validate-words="1"
                                   placeholder="请输入开发者邮箱" type="text">
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="textarea">开发者信息 <span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
              <textarea id="devInfo" name="devInfo" required="required"
                        placeholder="请输入开发者的相关信息，本信息作为开发者的详细信息" class="form-control col-md-7 col-xs-12">
                  ${dev.devInfo}
              </textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-6 col-md-offset-3">
                            <button id="send" type="submit" class="btn btn-success">保存</button>
                            <button type="button" class="btn btn-primary" id="back" onclick="window.location.href='listalldev'">返回</button>
                            <br/><br/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="common/footer.jsp"%>
<%--<script src="${pageContext.request.contextPath }/statics/localjs/appinfomodify.js"></script>--%>
