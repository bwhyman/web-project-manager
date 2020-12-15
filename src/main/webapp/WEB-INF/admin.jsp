<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script src="https://unpkg.com/xlsx/dist/xlsx.full.min.js"></script>

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
                    XLSX.utils.sheet_to_row_object_array(sheet).some(r => {
                        let number = parseInt(r.__EMPTY);
                        if (!isNaN(number)) {
                            students.push({
                                number: number,
                                name: r.__EMPTY_1,
                                clazz: r.__EMPTY_3
                            });
                        }
                    });
                };
                reader.onloadend = () => {
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
            url: "manager/students",
            method: "post",
            data: {"students": JSON.stringify(students)},
            success: resp => {
                console.log(resp);
            }
        })
    })
</script>
