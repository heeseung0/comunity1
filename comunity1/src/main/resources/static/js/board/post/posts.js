window.addEventListener('load', () => {
    const tbody = document.querySelector(".jsTarget_tbody");


    if (tbody != null) {
        switch (location.href.split('/')[3]) {
            case 'notice':
            case 'Notice':
                noticeGet();
                break;
        }
    }
})

function noticeGet() {
    const tbody = document.querySelector(".jsTarget_tbody");
    const postHeader = document.querySelector(".header");
    const postFooter = document.querySelector(".footer");
    const postContentHeader = document.querySelector(".header_title");
    const postContent = document.querySelector(".read_body");
    const postContentFooter = document.querySelector(".read_footer");

    $.ajax({
        async: false,
        type: "get",
        url: "/api/board/notice/getPosts",
        success: (response) => {
            console.log(response);

            tbody.innerHTML = "";
            response.data.forEach(post => {
                let type_class = "";
                let type_content = "";
                let date_process = post.date_post;

                //----------게시글 불러오는곳------------
                //----------전처리----------
                date_process = date_process.replace(/-/g, '.');
                date_process = date_process.substring(0, date_process.indexOf("T"));

                switch (post.type) {
                    case 1:
                        type_class = "category_notice";
                        type_content = "알림";
                        break;
                    case 2:
                        type_class = "category_update";
                        type_content = "업뎃";
                        break;
                    case 3:
                        type_class = "category_imple";
                        type_content = "중요";
                        break;
                }

                //----------후처리----------
                tbody.innerHTML += `
                        <tr>
                            <td class="no"><span>${post.id}</span></td>
                            <td class="${type_class}"><span>${type_content}</span></td>
                            <td class="title"><a href="/Notice/${post.id}">${post.title}</a></td>
                            <td class="author"><span>${post.writer}</span></td>
                            <td class="time"><span>${date_process}</span></td>
                            <td class="readNum"><span>${post.view}</span></td>
                        </tr>
                    `;

                //----------게시글 내부일 경우에 내용 불러오는곳----------
                if (postFooter != null && postContent != null) {
                    if (post.id == location.pathname.substring(8)) {
                        let boardURL = "/" + location.href.split('/')[3];
                        let postTime = post.date_post.substring(post.date_post.indexOf("T") + 1);

                        postContentHeader.innerHTML = `<a href=${boardURL}>공지사항</a>`;

                        postHeader.innerHTML = `
                                <h1 class="title"><span>${post.title}</span></h1>
                                <div class="nick_area">${post.writer}</div>
                            `;

                        postFooter.innerHTML = `
                                <a class="link" href="/Notice/${post.id}">127.0.0.1:8000/Notice/${post.id}</a>
                                <p class="sum">
                                    <span class="read" style="margin-right:40px"><b>조회 수</b> <span class="num">${post.view}</span></span>
                                    <span class="time"><b>등록일</b> <span class="num">${date_process}<span style="margin-right:20px"></span> ${postTime}</span></span>
                                </p>
                            `;

                        postContent.innerHTML = post.contents;

                        if (sessionStorage.getItem('getLogin') == post.writer || sessionStorage.getItem('getLoginRole') == 'admin') {
                            postContentFooter.setAttribute('class', 'read_footer');

                            btnEvent_MD(boardURL, post.id);
                        }
                    }
                }
            })
        }, error: (error) => {
            console.log("에러!!!");
            console.log(error);
        }
    });
}

function btnEvent_MD(boardURL, postID) {
    $(document).ready(function () {
        $('.postModify').click(function () {
            location.href += "/PostModify";
        });

        $('.postDelete').click(function () {
            if (confirm("글을 삭제하시겠습니까?")) {
                $.ajax({
                    async: false,
                    type: 'delete',
                    url: boardURL + "/" + postID + "/PostDelete",
                    success: (response) => {
                        alert("성공적으로 삭제하였습니다.");
                        location.href = boardURL;
                    }, error: (error) => {
                        alert("삭제중 오류가 발생하였습니다.");
                    }
                })
            }
        });
    });
}