window.addEventListener('load', () => {
    const member = document.querySelector(".member");

    $.ajax({
        async: false,
        type: "get",
        url: "/api/account/getLogin",
        success: (response) => {
            console.log(response);
            if (response.data == null || response.data == "anonymousUser") {
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
            } else if (response.data.account.role == 1) {
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
                        <div class="button-div">
                            <button class="login-btn-modify" type="button" onclick="location.href='/member/modify'">비밀번호 변경</button>
                        </div>
                        <div class="button-div">
                            <button class="login-btn-AccountLeave" type="button" onclick="leave()">회원탈퇴</button>
                        </div>
                    </div>                    
                `;
            } else if (response.data.account.role == 3) {
                member.innerHTML = `
                    <div class="aside-left-logo_div">
                        <a href="/"><img src="/static/images/main_logo.jpg"></a>
                    </div>
                    <div class="aside-left-span_div">
                        <span>MEMBER</span>
                    </div>
                    <div class="login-success">
                        <span class="login-username">${response.data.username}</span><span class="login-username2"> (관리자 계정)</span>
                        <div class="button-div">
                            <button class="login-btn-logout" type="button" onclick="location.href='/logout'">로그아웃</button>
                        </div>
                        <div class="button-div">
                            <button class="login-btn-modify" type="button" onclick="location.href='/member/modify'">비밀번호 변경</button>
                        </div>
                        <div class="button-div">
                            <button class="login-btn-AccountLeave" type="button" onclick="leave()">회원정보</button>
                        </div>
                    </div>                    
                `;
            }
        }, error: (error) => {
            console.log(error);
        }
    });
});

function leave() {
    if (window.confirm('정말로 탈퇴 하시겠습니까?')) {
        $.ajax({
            async: false,
            type: "post",
            url: "/api/account/leave",
            success: (response) => {
                alert('탈퇴가 완료되었습니다.');
                location.href = '/logout';
            }, error: (error) => {
                console.log(error);
            }
        })
    }
}