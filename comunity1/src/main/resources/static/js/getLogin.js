window.addEventListener('load', () => {
    const loginForm = document.querySelector(".login-form");
    const loginSuccess = document.querySelector(".login-success");
    const login_username = document.querySelector(".login-username");

    const member = document.querySelector(".member");

    $.ajax({
        async: false,
        type: "get",
        url: "/api/account/getLogin",
        success: (response) => {
            console.log(response);
            if (response.data == null) {
                let error = '1';
                member.innerHTML += `
                    <form class="login-form" method="post" action="/member/login">
                        <div class="input-div">
                            <input class="login-form-input" type="text" name="username" placeholder="아이디" required>
                            <input class="login-form-input" type="password" name="password" placeholder="비밀번호" required>
                        </div>
                        <div class="button-div">
                            <button class="login-form-btn-login" type="submit">로그인</button>
                        </div>
                        <div class="findAccount-div">
                            <button class="login-form-findAccount" type="button" onclick="location.href='/member/findAccount'">아이디 / 비밀번호찾기</button>
                        </div>
                        <div class="button-div">
                            <button class="login-form-btn-newAccount" type="button" onclick="location.href='/member/newAccount'">회원가입</button>
                        </div>
                    </form>                 
                `;
            } else {
                member.innerHTML = `
                    <div class="aside-left-logo_div">
                        <a href="/"><img src="/static/images/main_logo.jpg"></a>
                    </div>
                    <div class="aside-left-span_div">
                        <span>MEMBER</span>
                    </div>
                    <div class="login-success">
                        <span class="login-username">${response.data.username}</span><span class="login-username2">님 반갑습니다.</span>
                        <div class="button-div">
                            <button class="login-btn-logout" type="button" onclick="location.href='/logout'">로그아웃</button>
                        </div>
                    </div>                    
                `;
            }
        }, error: (error) => {
            console.log(error);
        }
    });
});