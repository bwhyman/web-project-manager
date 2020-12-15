<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    div.login {
        width: 350px;
    }

    i.login {
        font-size: 1.5rem;
    }

    #login-alert {
        display: none;
    }
</style>
<div class="login">
    <form>
        <div class="form-group">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="material-icons login">account_box</i></span>
                </div>
                <input type="text" name="number" class="form-control" placeholder="学号">
            </div>
        </div>
        <div class="form-group">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="material-icons login">lock</i></span>
                </div>
                <input type="password" name="pwd" class="form-control" placeholder="密码">
            </div>
        </div>
        <div class="form-group">
            <button type="button" class="btn btn-primary" id="button-login" disabled>Login</button>
        </div>
    </form>
</div>
<script>
    /*-------------------- Login ----------------------*/
    $("input[name=number]").blur(function () {
        let num = $(this).val();
        if (num.length != 10 || isNaN(num)) {
            $("#message").text("学号为10位数字");
            $('#exampleModal').modal('show')
            $("#button-login").prop("disabled", true);
        } else {
            $("#button-login").prop("disabled", false);
        }
    })

    $("#button-login").click(function () {
        $.ajax({
            url: "login",
            method: "post",
            data: {number: $("input[name=number]").val(), pwd: $("input[name=pwd]").val()},
            success: resp => {
                $("#v-pills-profile").html(resp);
            },
            error: error => {
                $("#message").text("学号/密码错误！");
                $('#exampleModal').modal('show')
            }
        })
    })
</script>