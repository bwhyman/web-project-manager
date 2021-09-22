<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- project --%>
<p style="text-align: right">
    <a class="btn btn-primary" data-toggle="collapse" href="#project" aria-expanded="false"
       aria-controls="project">
        <i class="material-icons">extension</i>
    </a>
</p>
<c:set var="path" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/" />
<div class="collapse show" id="project">
    <div class="alert alert-primary" role="alert">
        <span style="vertical-align: middle"><i class="material-icons info-link" id="logout" style="color: orangered">cloud_off</i></span>
        ${sessionScope.user.name} / ${sessionScope.user.clazz}
    </div>
    <div class="card card-body">
        <form>
            <div class="input-group mb-3">
                <div class="input-group mb-3">
                    <input value="${sessionScope.user.repositoryUrl}" type="text" name="repositoryurl"
                           class="form-control"
                           placeholder="项目源码仓库地址，可选">
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-primary" id="submit-repo">提交</button>
                </div>
            </div>
            <p>提交项目war包后，5-10s服务器自动完成部署。<br>
                <span class="text-danger">
                页面中的"/"是相对于服务器为根的请求，课设项目自动以学号为项目名部署在服务器。
                因此，请求必须是相对于自己项目为根的请求。参考以下首页地址的前缀</span></p>
            <div class="input-group mb-3">
                <div class="custom-file">
                    <input id="war-file" type="file" class="custom-file-input">
                    <label class="custom-file-label" for="war-file">选择项目war包</label>
                </div>
            </div>
            <div class="form-group">
                <label for="basic-url">网站首页地址。一个Servlet请求地址或index.html/home.jsp等资源地址</label>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon3">${path}${sessionScope.user.number}/</span>
                    </div>
                    <input value="${project.index}" name="index" type="text" class="form-control"
                           id="basic-url" aria-describedby="basic-addon3">
                </div>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-primary" id="upload-war" disabled>提交</button>
            </div>
            <div class="input-group mb-3">
                <div class="custom-control custom-switch">
                    <input type="checkbox" class="custom-control-input" id="customSwitch1">
                    <label class="custom-control-label" for="customSwitch1">部署在个人服务器</label>
                </div>
            </div>
            <div class="input-group mb-3">
                <input value="${project.selfAddress}" disabled name="self-address" type="text" class="form-control"
                       placeholder="部署在个人服务器的浏览地址">
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-primary" id="submit-self" disabled>提交</button>
            </div>
        </form>
    </div>
</div>
<script src="resources/uploadtable.js"></script>
<script>
    $("#logout").click(() => {
        $("#message").text("退出？");
        $("#message").next(".modal-footer").html(`<button id="submit-logout" type="button" class="btn btn-danger">Logout</button>`)
        $('#exampleModal').modal('show')
    })

    $(".modal-footer").on("click", "#submit-logout", () => {
        $.ajax({
            url: "logout",
            method: "get",
            success: resp => {
                $('#exampleModal').modal('hide')
                $("#v-pills-profile").html(resp);
                $("#message").next(".modal-footer").html("")
            }
        })
    })

    $("#customSwitch1").change(function () {
        let self = $(this)[0].checked;
        $('input[name=self-address]').prop('disabled', !self);
        $("input[name=index]").prop('disabled', self);
        $("#war-file").prop('disabled', self);
        if (self) {
            $("#upload-war").prop("disabled", true);
        } else {
            $("#submit-self").prop("disabled", true);

        }
    })
    $("#war-file").change(function () {
        let file = $(this).prop('files')[0];
        $(this).next().text(file.name);
        let d = file.size > 0;
        $("#upload-war").prop("disabled", !d);
    })

    $("#submit-repo").click(() => {
        $.ajax({
            url: "managerx/submitrepo",
            method: "post",
            data: {"repositoryurl": $("input[name=repositoryurl]").val()},
            success: resp => {
                $("#message").text("修改成功");
                $('#exampleModal').modal('show');
                uploadTable();
            }
        })
    })
    $("#upload-war").click(() => {
        let data = new FormData();
        let file = $('#war-file').prop('files')[0];
        data.append('file', file);
        data.append("index", $("input[name=index]").val());
        $.ajax({
            url: "managerx/uploadwar",
            method: "post",
            data: data,
            processData: false,
            contentType: false,
            xhr: function () {
                let xhr = new window.XMLHttpRequest();
                xhr.upload.addEventListener("progress", evt => {
                    let l = parseInt(evt.loaded / evt.total * 100);
                    $("#message").text(l + "%");
                }, false);
                return xhr;
            },
            beforeSend: () => {
                $('#exampleModal').modal('show')
            },
            success: resp => {
                $('#war-file').next().text("");
                uploadTable();
            }
        });
    })

    $("input[name=self-address]").change(function () {
        let d = $(this).val().length > 0;
        $("#submit-self").prop("disabled", !d);
    })

    $("#submit-self").click(() => {
        $.ajax({
            url: "managerx/submitself",
            method: "post",
            data: {"selfaddress": $("input[name=self-address]").val()},
            success: resp => {
                $("#message").text("修改成功");
                $('#exampleModal').modal('show')
                uploadTable();
            }
        })
    })
</script>
<br>
<%--  settings --%>
<style>
    div.password {
        width: 350px;
    }
</style>
<p style="text-align: right">
    <a class="btn btn-primary" data-toggle="collapse" href="#settings" aria-expanded="false"
       aria-controls="settings">
        <i class="material-icons">edit</i>
    </a>
</p>
<div class="collapse" id="settings">
    <div class="card card-body">
        <div id="show-photo">
            <c:if test="${sessionScope.user.photo != null && sessionScope.user.photo.length() > 10}">
                <img src="data:image/png;base64,${sessionScope.user.photo}" alt="photo"
                     style="width: 160px;height: 160px;border: 1px solid aquamarine; border-radius: 10px">
            </c:if>
            <c:if test="${sessionScope.user.photo == null || sessionScope.user.photo.length() < 10}">
                <i class="material-icons" style="font-size: 10rem">photo_camera</i>
            </c:if>
        </div>
        <p>老师认识来上课的同学，但是和名字对不上号啊。上传张照片吧，300KB</p>
        <div class="form-group">
            <div class="custom-file">
                <input id="student-photo" type="file" class="custom-file-input" accept=".jpg, .png">
                <label class="custom-file-label" for="student-photo">照片</label>
            </div>
        </div>
        <div class="form-group">
            <button type="button" class="btn btn-primary" id="submit-settings" disabled>提交</button>
        </div>

        <div class="form-group password">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="material-icons login">lock</i></span>
                </div>
                <input type="password" name="pwd" class="form-control" placeholder="新密码">
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="material-icons login">lock</i></span>
                </div>
                <input type="password" name="pwd-c" class="form-control" placeholder="验证">
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-primary" id="change-pwd" disabled>提交</button>
            </div>

        </div>
    </div>
</div>
<script>
    $("#student-photo").change(function () {
        let file = $(this).prop('files')[0];
        $(this).next().text(file.name);
        if (file.size > 300000) {
            $("#message").text("照片限制在300KB，剪裁一下");
            $('#exampleModal').modal('show')
            $("#submit-settings").prop("disabled", true);
            return;
        }

        $("#submit-settings").prop("disabled", false);
    })
    $("#submit-settings").click(() => {
        let file = $('#student-photo').prop('files')[0];
        let data = new FormData();
        data.append("file", file);
        data.append("filename", file.name);
        $.ajax({
            url: "managerx/photosettings",
            method: "post",
            data: data,
            processData: false,
            contentType: false,
            success: resp => {
                $("#show-photo").html(resp);
                uploadTable();
            }
        })
    })
    $("#change-pwd").click(() => {

        let f = $("input[name=pwd]").val();
        let s = $("input[name=pwd-c]").val();
        if (f != s) {
            $("#message").text("2次输入密码不符");
            $('#exampleModal').modal('show')
            return;
        }
        $.ajax({
            url: "managerx/changepwd",
            method: "post",
            data: {"pwd": f},
            success: resp => {
                $("#message").text("密码修改完成");
                $('#exampleModal').modal('show');
                $("input[name=pwd]").val("");
                $("input[name=pwd-c]").val("");
            }
        })
    })
    $("input[name=pwd]").change(function () {
        let l = $(this).val().trim().length;
        $("#change-pwd").prop("disabled", l == 0);
    })
</script>