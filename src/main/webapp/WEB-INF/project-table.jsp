<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="bpath" value="/"></c:url>
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
<p class="text-danger"><b>课设提交截至时间，${applicationScope.deadline}。过期提交无效。</b></p>
<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col"></th>
        <th scope="col"></th>
        <th scope="col"></th>
        <th scope="col"></th>
        <th scope="col"><a href="https://github.com/bwhyman/web-project-manager" target="_blank">
            <img style="width: 30px" src="http://pngimg.com/uploads/github/github_PNG72.png" alt="github">
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
                <div style="display: inline-block; text-align: center">
                        ${p.user.name}<br>
                    <span style="font-size: 0.5rem">${p.user.clazz}</span>
                </div>
            </td>
            <td><c:if test="${p.user.showPhoto == 0}">
                <div class="info">
                    <i class="material-icons info info-link" data-uid="${p.user.id}">info</i>
                    <div class="menu"></div>
                </div>
            </c:if>
            </td>
            <td>
                <c:if test="${p.user.repositoryUrl != null && p.user.repositoryUrl.length() > 0}">
                    <a href="${p.user.repositoryUrl}" target="_blank"><i
                            class="material-icons info-link" title="${p.user.repositoryUrl}">local_activity</i></a>
                </c:if>
            </td>
            <td>
                <c:set var="url" value=""/>
                <c:if test="${p.index == null}">
                    <c:set var="url" value="${p.selfAddress}"/>
                </c:if>
                <c:if test="${p.index != null}">
                    <c:set var="url" value="/${p.user.number}/${p.index}"/>
                </c:if>
                <a href="${url}" target="_blank"><i class="material-icons info-link"
                                                    title="${url}">link</i></a>
            </td>
            <td>
                <div style="font-size: 0.5rem; display: inline-block; text-align: center">${date}<br>${time}</div>
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
        160px;border: 1px solid aquamarine; border-radius: 10px">`);
        } else {
            $.ajax({
                url: "photo",
                data: {"uid": uid},
                beforeSend: () => {
                    $(this).next().html(`<img src="resources/circle-loading.gif" alt="photo" style="width: 160px;height:
        160px;border: 1px solid aquamarine; border-radius: 10px">`);
                },
                success: resp => {
                    photo = resp;
                    $(this).next().html(`<img src="\${photo}" alt="photo" style="width: 160px;height:
        160px;border: 1px solid aquamarine; border-radius: 10px">`);
                    photos.push({"uid": uid, "photo": photo});
                }
            });
        }
    }, () => {})
</script>
