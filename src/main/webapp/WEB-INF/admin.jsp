<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h2>更新截止时间</h2>
${applicationScope.deadline}
<div class="form-group">
    <input type="text" name="time"
           class="form-control"
           placeholder="yyyy-MM-dd HH:mm">
</div>
<div class="form-group">
    <button type="button" class="btn btn-primary" id="submit-time">提交</button>
</div>
<h2>删除部署项目</h2>
<div class="form-group">
    <input type="text" name="snumber"
           class="form-control"
           placeholder="删除部署信息学生学号">
</div>
<div class="form-group">
    <button type="button" class="btn btn-primary" id="submit-remove">提交</button>
</div>
<h2>重置密码</h2>
<div class="form-group">
    <input type="text" name="resetpassword-snumber"
           class="form-control"
           placeholder="学生学号">
</div>
<div class="form-group">
    <button type="button" class="btn btn-primary" id="submit-resetpassword">提交</button>
</div>
<h2>导入学生名单</h2>
<div class="input-group mb-3">
    <div class="custom-file">
        <input id="student-excel-file" type="file" class="custom-file-input">
        <label class="custom-file-label" for="student-excel-file">Choose file</label>
    </div>
</div>
<div class="form-group">
    <button type="button" class="btn btn-primary" id="button-upload-excel">Upload</button>
</div>
<table id="student-table" class="table table-hover">
    <thead class="bg-primary">
    <tr>
        <th scope="col">#</th>
        <th scope="col">姓名</th>
        <th scope="col">学号</th>
        <th scope="col">班级</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<jsp:include page="profile.jsp" />
<script src="https://unpkg.com/xlsx@0.18.5/dist/xlsx.full.min.js"></script>

<script>
    let students = [];
    $("#student-excel-file").change(function () {
        let file = $(this)[0].files[0];
        readStudentFile(file).then(s => {
            students = s;
            for (let i = 0; i < s.length; i++) {
                let count = i + 1;
                let name = s[i].name;
                let number = s[i].number;
                let clazz = s[i].clazz;
                $("#student-table tbody").append(`<tr><td>\${count}</td><td>\${name}</td><td>\${number}</td><td>\${clazz}</td></tr>`)
            }


        });
    })

    function readStudentFile(file) {
        return new Promise(
            resolve => {
                let reader = new FileReader();
                let students = [];
                reader.onload = e => {
                    let data = e.target.result;
                    let wb = XLSX.read(data, {type: "binary"});
                    let sheet = wb.Sheets[wb.SheetNames[0]];
                    XLSX.utils.sheet_to_json(sheet).some(r => {
                        console.log(r)
                        let number = r['学号'];
                        let name = r['姓名'];
                        let clazz = r['学生班级'];
                        if (number) {
                            students.push({
                                number: number,
                                name: name,
                                clazz: clazz
                            });
                        }
                    });
                };
                reader.onloadend = () => {
                    console.log(students)
                    resolve(students);
                };
                reader.readAsBinaryString(file);
            },
            () => {
            }
        );
    }
    $("#button-upload-excel").click(() => {
        $.ajax({
            url: "managerx/students",
            method: "post",
            data: {"students": JSON.stringify(students)},
            success: resp => {
                console.log(resp);
            }
        })
    })
    $("#submit-remove").click(() => {
        $.ajax({
            url: "managerx/deploy",
            method: "post",
            data: {"snumber": $("input[name=snumber]").val()},
            success: resp => {
                $("#message").text("删除成功！");
                $('#exampleModal').modal('show')
            }
        })
    })

    $("#submit-time").click(function () {
        $.ajax({
            url: "managex/updatedeadline",
            method: "post",
            data: {"time": $("input[name=time]").val()},
            success: resp => {
                $("#message").text("更新成功！");
                $('#exampleModal').modal('show')
            }
        })
    })

    $("#submit-resetpassword").click(() => {
        $.ajax({
            url: "managex/resetpassword",
            method: "post",
            data: {"number": $("input[name=resetpassword-snumber]").val().trim()},
            success: resp => {
                $("#message").text(resp);
                $('#exampleModal').modal('show')
            }
        })
    })

</script>
