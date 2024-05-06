<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:useBean id="projects" scope="request" type="java.util.List<com.example.entity.Project>"/>
<c:url var="bpath" value="/" />
<style>

    i.info-link {
        font-size: 2.0rem;
        color: #007bff;
        cursor: pointer;
    }

    div.info {
        display: inline-block;
        position: relative
    }

    div.info .menu {
        display: none;
        position: absolute;
    }

    div.info i:hover + .menu {
        display: inline;
    }

</style>
<p class="text-danger">
    <b>凡发布包含低俗、谩骂、政治等违反互联网法律法规信息内容，将直接删除部署并留存证据截图至专业，修改数据后可重新提交部署。</b> <br>
    <b>课设提交截至时间，${applicationScope.deadline}。过期提交无效。</b>
</p>
<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col"></th>
        <th scope="col"></th>
        <th scope="col"></th>
        <th scope="col"></th>
        <th scope="col"><a href="https://github.com/bwhyman/web-project-manager" target="_blank">
            <svg width="32" height="32" aria-hidden="true" viewBox="0 0 16 16">
                <path d="M8 0c4.42 0 8 3.58 8 8a8.013 8.013 0 0 1-5.45 7.59c-.4.08-.55-.17-.55-.38 0-.27.01-1.13.01-2.2 0-.75-.25-1.23-.54-1.48 1.78-.2 3.65-.88 3.65-3.95 0-.88-.31-1.59-.82-2.15.08-.2.36-1.02-.08-2.12 0 0-.67-.22-2.2.82-.64-.18-1.32-.27-2-.27-.68 0-1.36.09-2 .27-1.53-1.03-2.2-.82-2.2-.82-.44 1.1-.16 1.92-.08 2.12-.51.56-.82 1.28-.82 2.15 0 3.06 1.86 3.75 3.64 3.95-.23.2-.44.55-.51 1.07-.46.21-1.61.55-2.33-.66-.15-.24-.6-.83-1.23-.82-.67.01-.27.38.01.53.34.19.73.9.82 1.13.16.45.68 1.31 2.69.94 0 .67.01 1.3.01 1.49 0 .21-.15.45-.55.38A7.995 7.995 0 0 1 0 8c0-4.42 3.58-8 8-8Z"></path>
            </svg>
        </a>

        </th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="p" items="${projects}" varStatus="s">
        <fmt:formatDate
                pattern="yyyy-MM-dd"
                value="${p.updateTime }" var="date"/>
        <fmt:formatDate
                pattern="HH:mm"
                value="${p.updateTime }" var="time"/>
        <tr>
            <th scope="row">${s.count}</th>
            <td>
                <div style="display: inline-block;">
                        ${p.user.name}<br>
                    <span style="font-size: 0.8rem">${p.user.clazz}</span>
                </div>
            </td>
            <td><c:if test="${p.user.showPhoto == 1}">
                <div class="info">
                    <i class="material-icons info info-link" data-uid="${p.user.id}">info</i>
                    <div class="menu"></div>
                </div>
            </c:if>
            </td>
            <td>
                <c:if test="${p.user.repositoryUrl != null}">
                    <a href="${p.user.repositoryUrl}" target="_blank">
                        <i class="material-icons info-link" title="${p.user.repositoryUrl}">local_activity</i>
                    </a>
                </c:if>
            </td>
            <td>
                <c:set var="url" value="/${p.user.number}/${p.index}"/>
                <c:set var="icon" value="link"/>
                <c:set var="style" value="" />
                <c:if test="${p.selfAddress != null}">
                    <c:set var="url" value="${p.selfAddress}"/>
                    <c:set var="style" value="color:red" />
                    <c:set var="icon" value="cloud_queue"/>
                </c:if>
                <a href="${url}" target="_blank">
                    <i class="material-icons info-link" title="${url}" style="${style}">${icon}</i>
                </a>
            </td>
            <td>
                <div style="font-size: 0.8rem; display: inline-block; text-align: center">${date}<br>${time}</div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script>
    // photos 声明在index.html
    $("i.info").hover(function () {
        let photo;
        let uid = $(this).data("uid");
        let ph = photos.find(p => p.uid == uid);
        if (ph) {
            photo = ph.photo;
            $(this).next().html(`<img src="\${photo}" alt="photo" style="width: 160px;height:
        160px;border-radius: 10px">`);
        } else {
            $.ajax({
                url: "photo",
                data: {"uid": uid},
                beforeSend: () => {
                    $(this).next().html(`<img src="resources/circle-loading.gif" alt="photo" style="width: 160px;height:
        160px; border-radius: 10px">`);
                },
                success: resp => {
                    photo = resp;
                    $(this).next().html(`<img src="\${photo}" alt="photo" style="width: 160px;height:
        160px;border-radius: 10px">`);
                    photos.push({"uid": uid, "photo": photo});
                }
            });
        }
    }, () => {})
</script>
